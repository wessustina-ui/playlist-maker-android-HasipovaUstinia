package com.example.myapplication.data

class Storage {
    private val dummyData = listOf(
        TrackDto("Song A", "Artist A", 210000),
        TrackDto("Song B", "Artist B", 180000),
        TrackDto("Song C", "Artist C", 240000)
    )

    fun search(expression: String): List<TrackDto> {
        return dummyData.filter { it.trackName.contains(expression, ignoreCase = true) }
    }
}