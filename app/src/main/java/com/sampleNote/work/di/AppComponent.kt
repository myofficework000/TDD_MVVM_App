package com.sampleNote.work.di

import com.sampleNote.work.FinitoApp
import com.sampleNote.work.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivitiesBindingModule::class,
    AppModule::class,
    DatabaseModule::class,
    ContextDispatchersModule::class,
    RepositoryModule::class,
    ViewModelModule::class
])
interface AppComponent : AndroidInjector<FinitoApp>{
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun app(app: FinitoApp): Builder
        fun applicationModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }
}