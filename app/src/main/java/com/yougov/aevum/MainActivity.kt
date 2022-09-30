package com.yougov.aevum

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yougov.aevum.data.Timer
import com.yougov.aevum.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.min

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TimersScreen(viewModel,
                onAddTimer = { hours, minutes, seconds ->
                    val timer = Timer()
                    viewModel.addNewTimer(timer)
                    timer.startTimer(viewModel.calculateTimerInMillis(hours, minutes, seconds))
                } )
        }
    }
}

@Composable
fun TimersScreen(viewModel: MainViewModel,
                    onAddTimer: (String, String, String) -> Unit) {
    Surface {
        Column(modifier = Modifier.padding(16.dp)) {
            NewTimer(onAddTimer)
            Spacer(modifier = Modifier.height(24.dp))
            Text("Running timers:")
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .testTag("timersList")
            ) {
                items(viewModel.timers) { timer ->
                    Divider()
                    Timer(viewModel, timer)
                }
            }

        }
    }
}

@Composable
fun NewTimer(onAddTimer: (String, String, String) -> Unit) {
    var hours by rememberSaveable {
        mutableStateOf("")
    }

    var minutes by rememberSaveable {
        mutableStateOf("")
    }

    var seconds by rememberSaveable {
        mutableStateOf("")
    }

    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = spacedBy(8.dp)) {
        TimeInput(placeholderText = "hours",
            modifier = Modifier
                .weight(1f)
                .testTag("hours"),
            onValueChange = { hours = it })

        TimeInput(placeholderText = "minutes",
            modifier = Modifier
                .weight(1f)
                .testTag("minutes"),
            onValueChange = { minutes = it })
        TimeInput(placeholderText = "seconds",
            modifier = Modifier
                .weight(1f)
                .testTag("seconds"),
            onValueChange = { seconds = it })
        Button(
            onClick = {
                onAddTimer(hours, minutes, seconds)
            },
            modifier = Modifier.weight(1f)
        ) {
            Text("Start!")
        }
    }
}

@Composable
fun TimeInput(placeholderText: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit) {
    var input by remember {
        mutableStateOf("")
    }

    TextField(
        value = input,
        onValueChange = { input = it
            onValueChange(it) },
        placeholder = {
            Text(
                text = placeholderText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontSize = 10.sp)
            )
        },
        modifier = modifier
    )
}

@Composable
fun Timer(viewModel: MainViewModel, timer: Timer) {
    val timerButtonText = remember {
        mutableStateOf("Pause")
    }

    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = spacedBy(8.dp)) {
        Text(text = timer.timerText, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium), modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .weight(0.4f))
        Button(
            onClick = {
                if (timer.isPaused()) {
                    timer.resumeTimer()
                    timerButtonText.value = "Pause"
                } else {
                    timer.pauseTimer()
                    timerButtonText.value = "Resume"
                }
            },
            modifier = Modifier.weight(0.3f)
                .testTag("pauseResumeButton")
        ) {
            Text(timerButtonText.value)
        }
        Button(
            onClick = {
                viewModel.removeTimer(timer)
            },
            modifier = Modifier.weight(0.3f)
                .testTag("removeButton")
        ) {
            Text("Remove")
        }
    }
}

@Preview
@Composable
fun PreviewTimeInput() {
//    TimeInput(placeholderText = "seconds")
}

@Preview
@Composable
fun PreviewTimers() {

}

@Preview
@Composable
fun PreviewTimersEmpty() {
//    TimersScreen(emptyList())
}


@Preview
@Composable
fun PreviewTimer() {
//    Timer("03:23:14")
}

@Preview
@Composable
fun PreviewNewTimer() {
//    NewTimer()
}

data class Time(val value: String)