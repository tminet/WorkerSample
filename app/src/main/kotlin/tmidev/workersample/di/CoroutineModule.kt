package tmidev.workersample.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tmidev.workersample.util.CoroutineDispatchers
import tmidev.workersample.util.CoroutineDispatchersImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CoroutineModule {
    @Binds
    @Singleton
    fun bindsCoroutineDispatchers(
        impl: CoroutineDispatchersImpl
    ): CoroutineDispatchers
}