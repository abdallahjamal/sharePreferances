package com.abood.firsttraining.presentation.note_list

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.abood.firsttraining.Screen
import com.abood.firsttraining.presentation.note_list.components.TaskItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current
    val viewModel = remember { NoteListViewModel() }
    var data by remember { mutableStateOf(viewModel.getAllNote(context)) }
    val colorScheme =  MaterialTheme.colorScheme



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Note List", fontSize = 22.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.primary,
                    titleContentColor = colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddNoteScreen.route)
            },   containerColor = colorScheme.primary) {
                Text("+", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = colorScheme.onPrimary)

            }
        }
    ) {paddingValues->

        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (data.isEmpty()) {
                Text(
                    text = "No Note Found",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground.copy(alpha = 0.6f)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
                ) {
                    items(data) { task ->
                        TaskItem(
                            task = task.desc,
                            onDelete = {
                                viewModel.deleteNote(context, task.id)
                                data = viewModel.getAllNote(context)
                            },
                            onClick = {
                                navController.navigate(Screen.EditNoteScreen.createRoute(task.id))
                            }

                        )
                    }
                }
            }
        }

    }
}
