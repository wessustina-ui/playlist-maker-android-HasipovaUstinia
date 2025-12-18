package com.practicum.playlistmaker.ui.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.domain.Playlist
import com.practicum.playlistmaker.domain.PlaylistsRepository
import com.practicum.playlistmaker.domain.Track
import com.practicum.playlistmaker.domain.TracksLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val playlistService: PlaylistsRepository,
    private val localTrackRepo: TracksLocalRepository
) : ViewModel() {

    val allPlaylistsFlow: Flow<List<Playlist>> = playlistService.fetchAllPlaylists()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _imageUriState = MutableStateFlow<String?>(null)
    val imageUriFlow: Flow<String?> = _imageUriState.asStateFlow()

    fun updateCoverImageUri(uri: String?) {
        _imageUriState.value = uri
    }

    fun getPlaylistFlow(id: Long): Flow<Playlist?> = playlistService.fetchPlaylistById(id)

    fun addPlaylist(name: String, description: String) {
        viewModelScope.launch {
            playlistService.createPlaylist(name, description, _imageUriState.value)
        }
    }

    fun deletePlaylist(id: Long) {
        viewModelScope.launch {
            playlistService.removePlaylist(id)
        }
    }

    fun addTrackToExistingPlaylist(track: Track, playlistId: Long) {
        viewModelScope.launch {
            localTrackRepo.addTrackToPlaylist(track, playlistId)
        }
    }

    fun changeTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        viewModelScope.launch {
            localTrackRepo.setTrackFavoriteStatus(track, isFavorite)
        }
    }

    fun fetchTrackById(trackId: Long): Flow<Track?> = localTrackRepo.fetchTrackById(trackId)

    fun removeTrackFromPlaylist(trackId: Long, playlistId: Long) {
        viewModelScope.launch {
            localTrackRepo.removeTrackFromPlaylist(trackId, playlistId)
        }
    }
}