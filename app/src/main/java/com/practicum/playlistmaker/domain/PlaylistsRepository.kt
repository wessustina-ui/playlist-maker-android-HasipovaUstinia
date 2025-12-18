package com.practicum.playlistmaker.domain

import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {

    fun fetchAllPlaylists(): Flow<List<Playlist>>

    fun fetchPlaylistById(id: Long): Flow<Playlist?>

    suspend fun createPlaylist(name: String, description: String, coverUri: String? = null)

    suspend fun removePlaylist(id: Long)
}