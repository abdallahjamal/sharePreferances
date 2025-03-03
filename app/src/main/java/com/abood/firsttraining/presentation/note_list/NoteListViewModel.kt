package com.abood.firsttraining.presentation.note_list

import android.content.Context

import androidx.lifecycle.ViewModel
import com.abood.firsttraining.di.SharedPreferencesManager
import com.abood.firsttraining.domain.notes


class NoteListViewModel : ViewModel() {

    fun getAllNote(context: Context): ArrayList<notes> {
        return SharedPreferencesManager.getAllNotes(context)
    }

    fun deleteNote(context: Context, id: String) {
        SharedPreferencesManager.deleteNote(context, id)
    }
}
