package com.abood.firsttraining.presentation.add_note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.abood.firsttraining.di.SharedPreferencesManager
import com.abood.firsttraining.presentation.add_note.components.showDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(navController: NavController, noteId: String? = null) {

    val context = LocalContext.current
    var isError by remember { mutableStateOf(false) }
    var mgsError by remember { mutableStateOf("") }


    val viewModels = remember { AddViewModel() }
    if (noteId != null) {
        val note = SharedPreferencesManager.getAllNotes(context).find { it.id == noteId }
        note?.let {
            viewModels.onNoteChange(it.desc)
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (noteId == null) "Add Note" else "Edit Note",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                actions = {
                    IconButton(onClick = {
                        if (viewModels.noteText.isEmpty()) {
                            isError = true
                            mgsError = "The note is empty. Please enter some text."
                        } else {
//                                viewModels.saveNoteToPreferences(context)
//                                navController.popBackStack()
                            // Update or Add new Note
                            viewModels.saveNote(context, viewModels.noteText, noteId)
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Save",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Input Your Note", fontSize = 22.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))

            if (isError) {
                showDialog(mgsError = mgsError, onDismiss = { isError = false })
            }

            OutlinedTextField(
                value = viewModels.noteText,
                onValueChange = { viewModels.onNoteChange(it) },
                label = { Text("The Note") },
                singleLine = false,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { })
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (viewModels.noteText.isEmpty()) {
                        isError = true
                        mgsError = "The note is empty. Please enter some text."
                    } else {
                        viewModels.saveNote(context, viewModels.noteText, noteId)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Save", fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }

}

