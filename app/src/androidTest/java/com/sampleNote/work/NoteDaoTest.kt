package com.sampleNote.work

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.sampleNote.work.model.Note
import com.sampleNote.work.model.Priority
import com.sampleNote.work.model.repository.database.NoteDao
import com.sampleNote.work.model.repository.database.NoteDatabase
import com.sampleNote.work.util.LiveDataTestUtil
import junit.framework.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/*
Test class to verify Note dao CRUD operations
*/
@RunWith(AndroidJUnit4::class)
class NoteDaoTest {

    private lateinit var noteDatabase: NoteDatabase
    private lateinit var noteDao: NoteDao

    //step 1 for Testing Database
    // Setting Up context and intialise database, dao instance.
    @Before
    fun setup() {
        val context = InstrumentationRegistry.getContext()
        noteDatabase = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java).build()
        noteDao = noteDatabase.getNoteDao()

    }

    /*testing that note is saved into database and then fetching that data from same db to
    ensure that data is stored correctly*/
    @Test
    fun testSaveNote() {
        val note = Note(text = "Have a goodnight sleep", priority = Priority.HIGH)
        val noteId: Long = noteDao.saveNote(note)

        val savedNote = noteDao.findNoteById(noteId)

        assertNotNull(savedNote)
        assertEquals(note.copy(noteId = noteId), savedNote)
    }

    /*
      finding a note and just checking it should not null
    */
    @Test
    fun testFindNoteById_WithInvalidNoteId_ShouldReturnNull() {
        assertNull(noteDao.findNoteById(5))
    }

    /* Adding 3 notes then iterating notes using 'saveNotes'()
       fetching all notes and storing into list var finally checking stored note list size is 3*/
    @Test
    fun testFindSavedNotes() {
        val notes = listOf(
            Note(text = "Read book"),
            Note(text = "Listen to music", color = "#00FFFF"),
            Note(priority = Priority.MEDIUM, text = "Do Laundry")
        )

        notes.forEach {
            noteDao.saveNote(it)
        }

        val savedNotes = LiveDataTestUtil.getValue(noteDao.findSavedNotes())
        assertEquals(3, savedNotes.size)
    }

    /* Fetching saved note and then updating text on that note text and then updating changed note
       finally comparing changed note in db is same as that we update*/
    @Test
    fun testUpdateNote() {

        val note = Note(text = "Listen to music", color = "#00FFFF")
        val noteId = noteDao.saveNote(note)
        val savedNote = noteDao.findNoteById(noteId)

        val newText = "Play subway surfers"
        savedNote?.text = newText
        noteDao.updateNote(savedNote!!)

        val updatedNote = noteDao.findNoteById(noteId)

        assertEquals(savedNote, updatedNote)
    }

    /*    Creating Note and then adding that note into db then fetching note and deleting fetched note
        finally checking that note should not available into note db*/
    @Test
    fun testDeleteNote() {
        val note = Note(text = "Listen to music", color = "#00FFFF")
        val noteId = noteDao.saveNote(note)
        val savedNote = noteDao.findNoteById(noteId)

        noteDao.deleteNote(savedNote!!)

        assertNull(noteDao.findNoteById(noteId))
    }

    /* after testing all the CRUD operation on database instance, it should be closed so that
     it may not lead to memory issues*/
    @After
    fun tearDown() {
        //closing database
        noteDatabase.close()
    }
}