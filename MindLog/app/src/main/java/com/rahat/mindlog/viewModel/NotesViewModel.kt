package com.rahat.mindlog.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rahat.mindlog.data.Notes
import com.rahat.mindlog.repository.NotesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {
    val allNotes = repository.allNotes.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun addNotes(notes: Notes) = viewModelScope.launch {
        repository.insert(notes)
    }

    fun updateNotes(notes: Notes) = viewModelScope.launch {
        repository.update(notes)
    }

    fun deleteNotes(notes: Notes) = viewModelScope.launch {
        repository.delete(notes)
    }
}

class NotesViewModelFactory(private val repository: NotesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(repository) as T
    }
}
