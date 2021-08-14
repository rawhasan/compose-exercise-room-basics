package com.example.roombasics

import androidx.lifecycle.*
import com.example.roombasics.data.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    private val _openRowIndex = MutableStateFlow(-1)
    val openRowIndex: StateFlow<Int> = _openRowIndex

    fun updateOpenRowIndex(updatedIndex: Int) {
        _openRowIndex.value = updatedIndex
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun addWord(word: Word) = viewModelScope.launch {
        repository.insertWord(word)
        _openRowIndex.value = -1
    }

    fun updateWord(word: Word) = viewModelScope.launch {
        repository.updateWord(word)
        _openRowIndex.value = -1

    }

    fun deleteWord(word: Word) = viewModelScope.launch {
        repository.deleteWord(word)
        _openRowIndex.value = -1
    }

    fun clearWords() = viewModelScope.launch {
        repository.deleteAllWords()
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}