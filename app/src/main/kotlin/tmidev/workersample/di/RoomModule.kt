package tmidev.workersample.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tmidev.workersample.room.LogsDao
import tmidev.workersample.room.WorkerSampleDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun providesWorkerSampleDatabase(
        @ApplicationContext context: Context
    ): WorkerSampleDatabase = Room.databaseBuilder(
        context, WorkerSampleDatabase::class.java, "worker_sample_database"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesLogsDao(
        database: WorkerSampleDatabase
    ): LogsDao = database.logsDao
}