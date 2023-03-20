package tmidev.workersample.work

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject

private val List<WorkInfo>.anyEnqueued
    get() = any { it.state == WorkInfo.State.ENQUEUED }

private val List<WorkInfo>.anyRunning
    get() = any { it.state == WorkInfo.State.RUNNING }

class LogWorkMonitor @Inject constructor(
    @ApplicationContext context: Context
) : WorkMonitor {
    override val isEnqueued: Flow<Boolean> = WorkManager
        .getInstance(context)
        .getWorkInfosForUniqueWorkLiveData(LogWork.workName)
        .map(MutableList<WorkInfo>::anyEnqueued)
        .asFlow()
        .conflate()

    override val isRunning: Flow<Boolean> = WorkManager
        .getInstance(context)
        .getWorkInfosForUniqueWorkLiveData(LogWork.workName)
        .map(MutableList<WorkInfo>::anyRunning)
        .asFlow()
        .conflate()
}