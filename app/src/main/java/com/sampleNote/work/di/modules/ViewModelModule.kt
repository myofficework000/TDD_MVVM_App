package com.sampleNote.work.di.modules

import android.arch.lifecycle.ViewModel
import com.sampleNote.work.viewmodel.AddNoteViewModel
import com.sampleNote.work.viewmodel.NoteDetailViewModel
import com.sampleNote.work.viewmodel.NoteListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(NoteListViewModel::class)
    abstract fun bindNoteListViewModel(noteListViewModel: NoteListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddNoteViewModel::class)
    abstract fun bindAddNoteViewModel(addNoteViewModel: AddNoteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NoteDetailViewModel::class)
    abstract fun bindEditNoteViewModel(noteDetailViewModel: NoteDetailViewModel): ViewModel
}