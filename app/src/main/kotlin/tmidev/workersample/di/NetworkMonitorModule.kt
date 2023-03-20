package tmidev.workersample.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tmidev.workersample.util.NetworkMonitor
import tmidev.workersample.util.NetworkMonitorImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkMonitorModule {
    @Binds
    @Singleton
    fun bindsNetworkMonitor(
        impl: NetworkMonitorImpl
    ): NetworkMonitor
}