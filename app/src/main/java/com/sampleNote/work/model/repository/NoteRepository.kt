package com.sampleNote.work.model.repository

import android.arch.lifecycle.LiveData
import android.content.res.Resources.NotFoundException
import com.sampleNote.work.model.Note
import com.sampleNote.work.model.repository.database.NoteDao
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

/*
* Storing Note and deleting note and updating note
*/
class NoteRepository
@Inject constructor(
        private val notesDao: NoteDao
){
    suspend fun saveNote(note: Note): Note {
        checkFieldsNotEmpty(note)
        val noteId = notesDao.saveNote(note)
        return note.copy(noteId = noteId)
    }

    suspend fun findNoteById(noteId: Long): Note {
        return notesDao.findNoteById(noteId)
                ?:throw NotFoundException("Note not found")
    }

    suspend fun updateNote(note: Note): Note {
        checkFieldsNotEmpty(note)
        note.modifiedAt = LocalDateTime.now()
        notesDao.updateNote(note)
        return note
    }

    suspend fun deleteNote(noteId: Long){
        val noteToDelete = findNoteById(noteId)
        notesDao.deleteNote(noteToDelete)
    }

    fun findSavedNotes(): LiveData<List<Note>>{
        return notesDao.findSavedNotes()
    }

    private fun checkFieldsNotEmpty(note: Note){

        if(note.text.isEmpty() || note.color.isEmpty() || note.priority.name.isEmpty()){
            throw IllegalArgumentException("Field cannot be empty")
        }
    }
}