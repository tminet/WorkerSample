package tmidev.workersample.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import tmidev.workersample.R
import tmidev.workersample.room.LogEntity
import tmidev.workersample.room.LogsDao
import tmidev.workersample.util.CoroutineDispatchers
import kotlin.random.Random

@HiltWorker
class LogWork @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted params: WorkerParameters,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val logsDao: LogsDao
) : CoroutineWorker(
    appContext = appContext,
    params = params
) {
    override suspend fun doWork(): Result = withContext(context = coroutineDispatchers.io) {
        val randomDelay = Random.nextLong(
            from = 1_000,
            until = 10_000
        )

        delay(timeMillis = randomDelay)

        val newLogId = logsDao.insertLog(
            log = LogEntity(
                message = appContext.getString(R.string.logWorkMessage, randomDelay)
            )
        )

        if (newLogId > 0) Result.success()
        else Result.failure()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo =
        appContext.logWorkForegroundInfo()

    companion object {
        const val workName = "LogWork"
    }
}