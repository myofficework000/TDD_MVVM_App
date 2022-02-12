package com.sampleNote.work.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import com.sampleNote.work.R
import com.sampleNote.work.model.repository.database.NoteDao
import com.sampleNote.work.model.repository.database.NoteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(application: Application): NoteDatabase{
        return Room.databaseBuilder(application.applicationContext, NoteDatabase::class.java, application.getString(R.string.app_name))
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao = noteDatabase.getNoteDao()
}