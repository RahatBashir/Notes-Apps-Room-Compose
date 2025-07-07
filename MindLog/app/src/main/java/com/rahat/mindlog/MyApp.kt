package com.rahat.mindlog

import android.app.Application
import com.rahat.mindlog.db.NotesDatabase
import com.rahat.mindlog.repository.NotesRepository

class MyApp : Application() {

    // Create database and repository once for the whole app
    val database by lazy { NotesDatabase.getDatabase(this) }
    val repository by lazy { NotesRepository(database.notesDao()) }
}
