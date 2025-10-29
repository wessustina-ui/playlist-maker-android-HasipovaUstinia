package com.example.myapplication.domain

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}