package com.example.myapplication.componentUI

import com.example.myapplication.data.RetrofitNetworkClient
import com.example.myapplication.data.Storage
import com.example.myapplication.domain.TracksRepository
import com.example.myapplication.domain.TracksRepositoryImpl
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(Storage()))
    }
}
class SearchViewModel(
    private val tracksRepository: TracksRepository
) : ViewModel() {
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState: StateFlow<SearchState> = _searchScreenState
    fun search(whatSearch: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchScreenState.value = SearchState.Searching
            try {
                val list = tracksRepository.searchTracks(whatSearch)
                _searchScreenState.value = SearchState.Success(list)
            } catch (e: IOException) {
                _searchScreenState.value = SearchState.Fail(e.message ?: "Error")
            }
        }
    }
    companion object {
        fun getViewModelFactory(): androidx.lifecycle.ViewModelProvider.Factory =
            object : androidx.lifecycle.ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(Creator.getTracksRepository()) as T
                }
            }
    }
}