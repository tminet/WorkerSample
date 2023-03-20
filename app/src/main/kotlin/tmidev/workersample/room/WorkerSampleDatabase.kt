package tmidev.workersample.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WorkerSampleDatabase : RoomDatabase() {
    abstract val logsDao: LogsDao
}