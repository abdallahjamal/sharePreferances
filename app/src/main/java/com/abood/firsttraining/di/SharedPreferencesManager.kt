package com.abood.firsttraining.di

import android.content.Context
import android.util.Log
import com.abood.firsttraining.domain.notes

object SharedPreferencesManager {
    private const val PREF_NAME = "MyPrefs"
    private const val NOTES_KEY = "notes_list"



    fun saveNote(context: Context, desc: String, noteId: String? = null) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val notesList = getAllNotes(context)
        val newId = noteId ?: (notesList.size + 1).toString()

        if (noteId != null) {
            // Update Note If Found It
            val updatedList = notesList.map {
                if (it.id == noteId) it.copy(desc = desc) else it
            }
            val notesString = updatedList.joinToString("#") { "${it.id},${it.desc}" }
            editor.putString(NOTES_KEY, notesString)
        } else {
            // add new Note
            notesList.add(notes(newId, desc))
            val notesString = notesList.joinToString("#") { "${it.id},${it.desc}" }
            editor.putString(NOTES_KEY, notesString)
        }
        editor.apply()

        Log.d("saveNote", "Saved: $newId || $desc")
    }

    fun getAllNotes(context: Context): ArrayList<notes> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val notesString = sharedPreferences.getString(NOTES_KEY, "") ?: ""

        val notesList = ArrayList<notes>()

        if (notesString.isNotEmpty()) {
            val notesArray = notesString.split("#")
            for (note in notesArray) {
                val parts = note.split(",")
                if (parts.size == 2) {
                    notesList.add(notes(parts[0], parts[1]))
                }
            }
        }

        return notesList
    }

    fun deleteNote(context: Context, noteId: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val notesList = getAllNotes(context)

        val updatedList = notesList.filter { it.id != noteId }
        val notesString = updatedList.joinToString("#") { "${it.id},${it.desc}" }
        editor.putString(NOTES_KEY, notesString)
        editor.apply()

        Log.d("deleteNote", "Deleted note with ID: $noteId")
    }

}