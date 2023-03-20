package tmidev.workersample.work

import kotlinx.coroutines.flow.Flow

interface WorkMonitor {
    val isEnqueued: Flow<Boolean>
    val isRunning: Flow<Boolean>
}