package com.abood.firsttraining

sealed class Screen(val route : String) {
    object NoteScreen: Screen("notes_screen")
    object AddNoteScreen: Screen("add_note_screen")
    object EditNoteScreen: Screen("edit_note_screen/{noteId}") {
        fun createRoute(noteId: String) = "edit_note_screen/$noteId"
    }
}