package tmidev.workersample.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tmidev.workersample.R
import tmidev.workersample.room.LogEntity
import tmidev.workersample.util.toFormattedTimestamp
import tmidev.workersample.work.LOG_WORK_INTERVAL
import tmidev.workersample.work.LogWork

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainContent(
    modifier: Modifier,
    isOnline: Boolean,
    isLogWorkEnqueued: Boolean,
    isLogWorkRunning: Boolean,
    logs: List<LogEntity>
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    AnimatedVisibility(visible = !isOnline) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.offline),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = stringResource(
                    id = R.string.workWillNotRunWithoutConnection,
                    LogWork.workName
                ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .weight(weight = 1F),
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        if (logs.isEmpty()) item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.noLogsToBeDisplayed),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        }

        itemsIndexed(
            items = logs,
            key = { _, log -> log.id }
        ) { index, log ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement(),
                verticalArrangement = Arrangement.spacedBy(space = 4.dp)
            ) {
                Text(
                    text = log.timestamp.toFormattedTimestamp(),
                    fontFamily = FontFamily.Monospace
                )

                Text(
                    text = log.message,
                    fontFamily = FontFamily.Monospace
                )

                if (index < logs.lastIndex) Divider()
            }
        }
    }

    AnimatedVisibility(
        modifier = Modifier.padding(all = 16.dp),
        visible = isLogWorkEnqueued
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.workEnqueued, LogWork.workName),
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = stringResource(id = R.string.repeatTimeInMinutes, LOG_WORK_INTERVAL),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    AnimatedVisibility(
        modifier = Modifier.padding(all = 16.dp),
        visible = isLogWorkRunning
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.workRunning, LogWork.workName),
                style = MaterialTheme.typography.titleLarge
            )

            LinearProgressIndicator()
        }
    }
}