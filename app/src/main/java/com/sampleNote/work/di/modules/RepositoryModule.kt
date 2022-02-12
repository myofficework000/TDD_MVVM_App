package com.sampleNote.work.di.modules

import com.sampleNote.work.model.repository.NoteRepository
import com.sampleNote.work.model.repository.database.NoteDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
class RepositoryModule {
    @Singleton
    @Provides
    fun provideNoteRepository(notesDao: NoteDao): NoteRepository{
        return NoteRepository(notesDao)
    }
}