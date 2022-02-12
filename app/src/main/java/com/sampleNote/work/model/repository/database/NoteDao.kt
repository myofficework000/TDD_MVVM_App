package com.sampleNote.work.model.repository.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.sampleNote.work.model.Note

/*
Keeps all the CRUD operation for Note making work
*/
@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun findSavedNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes")
    fun allNotes(): List<Note>


    @Query("SELECT * FROM notes WHERE noteId = :noteId")
    fun findNoteById(noteId: Long): Note?

    @Insert
    fun saveNote(note: Note): Long

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM notes")
    fun deleteSavedNotes()


}