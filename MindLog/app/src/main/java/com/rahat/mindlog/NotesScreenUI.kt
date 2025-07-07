package com.rahat.mindlog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rahat.mindlog.data.Notes
import com.rahat.mindlog.viewModel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(notesViewModel: NotesViewModel) {
    val notes by notesViewModel.allNotes.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editingNote by remember { mutableStateOf<Notes?>(null) }
    var noteContent by remember { mutableStateOf("") }
    var noteTitle by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Notes") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editingNote = null
                noteContent = ""
                noteTitle = ""
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            LazyColumn {
                items(notes) { note ->
                    NoteCard(
                        note = note,
                        onEdit = {
                            editingNote = note
                            noteContent = note.content
                            noteTitle = note.title
                            showDialog = true
                        },
                        onDelete = {
                            notesViewModel.deleteNotes(note)
                        }
                    )
                }
            }

            if (showDialog) {
                NoteDialog(
                    noteContent = noteContent,
                    noteTitle = noteTitle,
                    onContentChange = { noteContent = it },
                    onTitleChange = { noteTitle = it },
                    onDismiss = { showDialog = false },
                    onConfirm = {
                        if (noteContent.isNotBlank() || noteTitle.isNotBlank()) {
                            if (editingNote != null) {
                                notesViewModel.updateNotes(
                                    editingNote!!.copy(content = noteContent, title = noteTitle)
                                )
                            } else {
                                notesViewModel.addNotes(
                                    Notes(
                                        title = noteTitle,
                                        content = noteContent,
                                        timestamp = System.currentTimeMillis()
                                    )
                                )
                            }
                            showDialog = false
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NoteCard(note: Notes, onEdit: () -> Unit, onDelete: () -> Unit) {
    val formatter = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())
    val formattedDate = formatter.format(Date(note.timestamp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                    Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
                }
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Created: $formattedDate", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun NoteDialog(noteContent: String, noteTitle: String, onContentChange: (String) -> Unit, onTitleChange: (String) -> Unit, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Note") },
        text = {
            Column {
                OutlinedTextField(
                    value = noteTitle,
                    onValueChange = onTitleChange,
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = noteContent,
                    onValueChange = onContentChange,
                    label = { Text("Content") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Save")
            }
        }
    )
}
