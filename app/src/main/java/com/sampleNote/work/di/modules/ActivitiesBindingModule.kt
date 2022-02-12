package com.sampleNote.work.di.modules

import com.sampleNote.work.view.AddNoteActivity
import com.sampleNote.work.view.NoteDetailActivity
import com.sampleNote.work.view.NoteListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModule {
    @ContributesAndroidInjector
    abstract fun noteListActivity(): NoteListActivity

    @ContributesAndroidInjector
    abstract fun addNoteActivity(): AddNoteActivity

    @ContributesAndroidInjector
    abstract fun noteDetailActivity(): NoteDetailActivity
}