package com.abood.firsttraining

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abood.firsttraining.presentation.add_note.AddNoteScreen
import com.abood.firsttraining.presentation.note_list.HomeScreen
import com.abood.firsttraining.ui.theme.FirstTrainingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstTrainingTheme {
                val navController = rememberNavController()


                NavHost(navController = navController, startDestination = Screen.NoteScreen.route) {
                    composable(Screen.NoteScreen.route) {
                        HomeScreen(navController)
                    }

                    composable(Screen.AddNoteScreen.route) {
                        AddNoteScreen(navController)
                    }

                    composable(
                        route = Screen.EditNoteScreen.route,
                        arguments = listOf(navArgument("noteId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId") ?: ""
                        AddNoteScreen(navController, noteId)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FirstTrainingTheme {
    }
}