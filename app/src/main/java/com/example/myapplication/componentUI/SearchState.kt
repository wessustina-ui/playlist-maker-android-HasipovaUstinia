package com.example.myapplication.componentUI

import com.example.myapplication.domain.Track

sealed class SearchState {
    object Initial : SearchState()
    object Searching : SearchState()
    data class Success(val foundList: List<Track>) : SearchState()
    data class Fail(val error: String) : SearchState()
}