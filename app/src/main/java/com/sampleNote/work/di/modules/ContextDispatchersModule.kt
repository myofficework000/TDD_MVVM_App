package com.sampleNote.work.di.modules

import com.sampleNote.work.ContextDispatchers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextDispatchersModule {
    @Singleton
    @Provides
    fun provideDispatcherProvider(): ContextDispatchers{
        return ContextDispatchers()
    }
}