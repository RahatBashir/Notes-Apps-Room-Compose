package com.rahat.mindlog.repository

import com.rahat.mindlog.dao.NotesDao
import com.rahat.mindlog.data.Notes

class NotesRepository(private val dao: NotesDao) {
    val allNotes = dao.getAllNotes()

    suspend fun insert(notes: Notes) = dao.insert(notes)
    suspend fun update(notes: Notes) = dao.update(notes)
    suspend fun delete(notes: Notes) = dao.delete(notes)
}
