package com.sampleNote.work.model.network

import com.google.gson.GsonBuilder
import com.sampleNote.work.model.Note
import com.sampleNote.work.model.Priority
import com.sampleNote.work.model.repository.network.NoteService
import com.sampleNote.work.util.DateTimeDeserializer
import com.sampleNote.work.util.DateTimeSerializer
import com.sampleNote.work.util.PriorityDeserializer
import com.sampleNote.work.util.PrioritySerializer
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
Testing our service class API's by using mock server
*/
class NoteServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var noteService: NoteService

    /*Setting up the server and Gson builder also calling our service instance*/
    @Before
    fun setup(){
        mockWebServer = MockWebServer()

        val gson = GsonBuilder()
                .registerTypeAdapter(LocalDateTime::class.java, DateTimeSerializer())
                .registerTypeAdapter(LocalDateTime::class.java, DateTimeDeserializer())
                .registerTypeAdapter(Priority::class.java, PrioritySerializer())
                .registerTypeAdapter(Priority::class.java, PriorityDeserializer())
                .create()

        noteService = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(NoteService::class.java)
    }

    @Test
    fun testGetNoteById(){
        enqueueResponseFromJsonFile("get-single-note.json")
        val note = noteService.getNoteById(2).execute().body()
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("GET", recordedRequest.method)
        assertEquals(recordedRequest.path, "/notes/2")
        assertNotNull(note)
        assertEquals(note?.noteId, 2.toLong())
        assertEquals(note?.text, "Listen to music")
        assertEquals(note?.color, "#FFFFFF")
        assertEquals(note?.priority, Priority.LOW)
        assertEquals(note?.modifiedAt, ZonedDateTime.parse("2018-12-14T17:13:30.00Z").toLocalDateTime())
    }

    /*fetching notes from list of note API*/
    @Test
    fun testGetSavedNotes(){
        enqueueResponseFromJsonFile("get-note-list.json")
        val notes = noteService.getSavedNotes().execute().body()
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("GET", recordedRequest.method)
        assertEquals(recordedRequest.path, "/notes")
        assertEquals(notes?.size, 3)

        val note = notes!![0]
        assertEquals(note.noteId, 1.toLong())
        assertEquals(note.text, "Eat Breakfast")
        assertEquals(note.color, "#FFFFFF")
        assertEquals(note.priority, Priority.MEDIUM)
        assertEquals(note.modifiedAt, ZonedDateTime.parse("2018-12-13T07:15:21.00Z").toLocalDateTime())

        val note2 = notes[1]
        assertEquals(note2.noteId, 2.toLong())
        assertEquals(note2.text, "Listen to music")
        assertEquals(note2.color, "#FFFFFF")
        assertEquals(note2.priority, Priority.LOW)
        assertEquals(note2.modifiedAt, ZonedDateTime.parse("2018-12-14T17:13:30.00Z").toLocalDateTime())
    }

    /*Saving one new note to the list of notes*/
    @Test
    fun testSaveNote(){
        mockWebServer.enqueue(MockResponse().setBody("""{}"""))

        val newNote = Note(
                noteId = 7,
                text = "Buy cornflakes",
                priority = Priority.HIGH,
                modifiedAt = LocalDateTime.parse("2018-12-18T11:59:57.883Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        )
        noteService.saveNote(newNote).execute()
        val recordedRequest = mockWebServer.takeRequest()
        val recordedRequestBody = recordedRequest.body.readString(Charsets.UTF_8)
        val json = JSONObject(recordedRequestBody)

        assertEquals("/notes", recordedRequest.path)
        assertEquals("POST", recordedRequest.method)
        assertEquals(json.getLong("noteId"), 7.toLong())
        assertEquals(json.getString("text"), "Buy cornflakes")
        assertEquals(json.getString("color"), "#FFFFFF")
        assertEquals(json.getString("priority"), "2")
    }


    @Test
    fun testUpdateNote(){
        mockWebServer.enqueue(MockResponse().setBody("""{}"""))

        val partialNote = mutableMapOf<String, String>()
        partialNote["text"] = "Practice Databinding"
        partialNote["priority"] = Priority.LOW.ordinal.toString()

        noteService.updateNote(25, partialNote).execute()
        val recordedRequest = mockWebServer.takeRequest()
        val recordedRequestBody = recordedRequest.body.readString(Charsets.UTF_8)
        val json = JSONObject(recordedRequestBody)

        assertEquals("PATCH", recordedRequest.method)
        assertEquals("/notes/25", recordedRequest.path)
        assertEquals("Practice Databinding", json.getString("text"))
        assertEquals("0", json.getString("priority"))


    }

   /* delete a note*/
    @Test
    fun testDeleteNoteById(){
        mockWebServer.enqueue(MockResponse())
        noteService.deleteNoteById(4).execute()

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("DELETE", recordedRequest.method)
        assertEquals(recordedRequest.path, "/notes/4")
    }


    private fun enqueueResponseFromJsonFile(fileName: String){
        val stream = javaClass.classLoader.getResourceAsStream("api-responses/$fileName")
        val source = stream.source().buffer()
        mockWebServer.enqueue(MockResponse().setBody(source.readString(Charsets.UTF_8)))
    }

    /*Stop all server*/
    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}