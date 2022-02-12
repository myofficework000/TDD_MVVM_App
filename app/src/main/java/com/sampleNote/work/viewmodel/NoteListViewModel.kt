package com.sampleNote.work.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.databinding.Bindable
import com.sampleNote.work.BR
import com.sampleNote.work.ContextDispatchers
import com.sampleNote.work.model.repository.NoteRepository
import com.sampleNote.work.viewmodel.dto.NoteListItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NoteListViewModel
@Inject constructor(
        private val contextDispatchers: ContextDispatchers,
        private val noteRepository: NoteRepository) :
        ObservableViewModel(), CoroutineScope{
    @get:Bindable
    var isNoteListVisible = false

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + contextDispatchers.Main
    private val scope = CoroutineScope(coroutineContext)


    fun getNoteList():LiveData<List<NoteListItem>> = Transformations.map(noteRepository.findSavedNotes()) { savedNotes ->

        isNoteListVisible = savedNotes.isNotEmpty()
        notifyPropertyChanged(BR.noteListVisible)

        savedNotes.map {
            NoteListItem(
                    noteId = it.noteId,
                    text = it.text,
                    color = it.color
            )
        }

    }

    fun deleteNote(noteId: Long) = scope.launch{
        async(contextDispatchers.IO) {noteRepository.deleteNote(noteId)}.await()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}