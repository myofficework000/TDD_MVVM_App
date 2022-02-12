package com.sampleNote.work.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.sampleNote.work.ContextDispatchers
import com.sampleNote.work.model.Note
import com.sampleNote.work.model.repository.NoteRepository
import com.sampleNote.work.viewmodel.dto.NoteListItem
import com.sampleNote.work.viewmodel.util.TestContextDispatchers
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NoteListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockNoteRepository: NoteRepository

    @Mock
    lateinit var observer: Observer<List<NoteListItem>>

    private lateinit var noteListViewModel: NoteListViewModel

    private lateinit var testContextDispatchers: ContextDispatchers

    @Before
    fun setup() {
        //intializing mockito library
        MockitoAnnotations.initMocks(this)
        testContextDispatchers = TestContextDispatchers()
        noteListViewModel = NoteListViewModel(testContextDispatchers, mockNoteRepository)
    }

    @Test
    fun `test findSavedNotes WhenNoteIsEmpty IsNotAddedField ShouldBeFalse`() {
        //here livedata is empty as we had nothing added to this live data
        val liveData = MutableLiveData<List<Note>>()
        //Assuming that find save notes method will call and give us a livedata instance
        Mockito.`when`(mockNoteRepository.findSavedNotes()).thenReturn(liveData)

        noteListViewModel.getNoteList().observeForever(observer)
        verify(observer, never()).onChanged(ArgumentMatchers.anyList())
        assertFalse(noteListViewModel.isNoteListVisible)
    }

    @Test
    fun testNoteList_WhenNoteIsNotEmpty_IsNotAddedField_ShouldBeTrue() {
        val liveData = MutableLiveData<List<Note>>()
        liveData.value = listOf(
            Note(text = "Listen to music")
        )
        Mockito.`when`(mockNoteRepository.findSavedNotes()).thenReturn(liveData)
        noteListViewModel.getNoteList().observeForever(observer)
        verify(observer, times(1)).onChanged(ArgumentMatchers.anyList())
        assertTrue(noteListViewModel.isNoteListVisible)

    }

    @Test
    fun testDeleteNote() = runBlocking {
        noteListViewModel.deleteNote(2)
        verify(mockNoteRepository).deleteNote(Mockito.anyLong())
    }

}