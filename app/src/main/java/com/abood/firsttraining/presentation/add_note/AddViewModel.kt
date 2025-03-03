package com.abood.firsttraining.presentation.add_note

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abood.firsttraining.di.SharedPreferencesManager
import kotlinx.coroutines.launch


class AddViewModel : ViewModel() {


    val sharedPreferences = SharedPreferencesManager


    var noteText by mutableStateOf("")
        private set

    fun onNoteChange(newText: String) {
        noteText = newText
    }


    fun saveNote(context: Context, desc: String, noteId: String? = null) {
        sharedPreferences.saveNote(context, desc, noteId)
    }


}