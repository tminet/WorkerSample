package tmidev.workersample.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tmidev.workersample.work.WorkMonitor
import tmidev.workersample.work.LogWorkMonitor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface WorkMonitorModule {
    @Binds
    @Singleton
    fun bindsLogWorkMonitor(
        impl: LogWorkMonitor
    ): WorkMonitor
}