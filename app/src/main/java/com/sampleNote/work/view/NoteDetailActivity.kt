package com.sampleNote.work.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sampleNote.work.R
import com.sampleNote.work.databinding.ActivityNoteDetailBinding
import com.sampleNote.work.viewmodel.NoteDetailViewModel
import com.sampleNote.work.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class NoteDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var mFactory: ViewModelFactory

    private lateinit var binding: ActivityNoteDetailBinding
    private lateinit var noteDetailViewModel: NoteDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note_detail)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        noteDetailViewModel = ViewModelProviders.of(this, mFactory).get(NoteDetailViewModel::class.java)
        binding.vm = noteDetailViewModel
        binding.setLifecycleOwner(this)

        noteDetailViewModel.loadNote(intent.extras.getLong("noteId"))

        noteDetailViewModel.isNoteUpdated.observe(this, Observer {
            finish()
        })
    }

}
