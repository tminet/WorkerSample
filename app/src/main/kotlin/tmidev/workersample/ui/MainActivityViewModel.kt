package tmidev.workersample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import tmidev.workersample.room.LogEntity
import tmidev.workersample.room.LogsDao
import tmidev.workersample.util.NetworkMonitor
import tmidev.workersample.work.WorkMonitor
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    workMonitor: WorkMonitor,
    logsDao: LogsDao
) : ViewModel() {
    private val stopTimeoutMillis = 5000L

    val isOnline: StateFlow<Boolean> = networkMonitor.isOnline.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = stopTimeoutMillis),
        initialValue = true
    )

    val isLogWorkEnqueued: StateFlow<Boolean> = workMonitor.isEnqueued.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = stopTimeoutMillis),
        initialValue = false
    )

    val isLogWorkRunning: StateFlow<Boolean> = workMonitor.isRunning.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = stopTimeoutMillis),
        initialValue = false
    )

    val logs: StateFlow<List<LogEntity>> = logsDao.getLogsFlowable().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = stopTimeoutMillis),
        initialValue = emptyList()
    )
}