package com.rahat.mindlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.rahat.mindlog.ui.theme.MindLogTheme
import com.rahat.mindlog.viewModel.NotesViewModel
import com.rahat.mindlog.viewModel.NotesViewModelFactory

class MainActivity : ComponentActivity() {
    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as MyApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindLogTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NotesScreen(notesViewModel)
                    }
                }
            }
        }
    }
}