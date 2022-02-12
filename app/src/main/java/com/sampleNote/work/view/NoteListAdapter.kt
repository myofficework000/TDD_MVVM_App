package com.sampleNote.work.view

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sampleNote.work.R
import com.sampleNote.work.databinding.NoteListItemBinding
import com.sampleNote.work.viewmodel.dto.NoteListItem

class NoteListAdapter(val listener: NoteListItemSelectedListener) : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>(){

    private val noteList = arrayListOf<NoteListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
       val binding = DataBindingUtil.inflate<NoteListItemBinding>(
               LayoutInflater.from(parent.context), R.layout.note_list_item, parent, false
       )
        return NoteViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return noteList.size
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    fun setNoteList(list: List<NoteListItem>){
        noteList.clear() //Inefficient
        noteList.addAll(list)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder (private val binding: NoteListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(note: NoteListItem){
            binding.note = note
            binding.root.setOnClickListener { listener.onEdit(note) }
            binding.root.setOnLongClickListener {
                listener.onDelete(note)
                true
            }
            binding.executePendingBindings()
        }
    }
}