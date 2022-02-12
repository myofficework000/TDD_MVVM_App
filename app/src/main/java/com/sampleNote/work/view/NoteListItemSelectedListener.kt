package com.sampleNote.work.view

import com.sampleNote.work.viewmodel.dto.NoteListItem

interface NoteListItemSelectedListener {
    fun onEdit(note: NoteListItem)
    fun onDelete(note: NoteListItem)
}