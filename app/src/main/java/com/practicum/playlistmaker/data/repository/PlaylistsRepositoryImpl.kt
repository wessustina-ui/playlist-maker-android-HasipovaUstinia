package com.practicum.playlistmaker.data.repository

import com.practicum.playlistmaker.data.dao.PlaylistRepository
import com.practicum.playlistmaker.data.dao.TrackRepository
import com.practicum.playlistmaker.data.entity.PlaylistEntity
import com.practicum.playlistmaker.domain.Playlist
import com.practicum.playlistmaker.domain.PlaylistsRepository
import com.practicum.playlistmaker.domain.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PlaylistsRepositoryImpl(
    private val playlistDao: PlaylistRepository,
    trackDao: TrackRepository,
) : PlaylistsRepository {

    override fun fetchAllPlaylists(): Flow<List<Playlist>> = playlistDao.fetchAllPlaylists().map { playlistsWithTracks ->
        playlistsWithTracks.map { playlistWithTracks ->
            Playlist(
                id = playlistWithTracks.playlist.id,
                name = playlistWithTracks.playlist.name,
                description = playlistWithTracks.playlist.description,
                tracks = playlistWithTracks.tracks.map { trackEntity ->
                    Track(
                        trackId = trackEntity.trackId,
                        trackName = trackEntity.trackName,
                        artistName = trackEntity.artistName,
                        trackTimeMillis = trackEntity.trackTimeMillis,
                        artworkUrl100 = trackEntity.artworkUrl100,
                        previewUrl = trackEntity.previewUrl,
                        favorite = trackEntity.isFavorite
                    )
                },
                coverImageUri = playlistWithTracks.playlist.coverImageUri
            )
        }
    }

    override fun fetchPlaylistById(id: Long): Flow<Playlist?> = playlistDao.fetchPlaylistById(id).map { playlistWithTracks ->
        playlistWithTracks?.let {
            Playlist(
                id = it.playlist.id,
                name = it.playlist.name,
                description = it.playlist.description,
                tracks = it.tracks.map { trackEntity ->
                    Track(
                        trackId = trackEntity.trackId,
                        trackName = trackEntity.trackName,
                        artistName = trackEntity.artistName,
                        trackTimeMillis = trackEntity.trackTimeMillis,
                        artworkUrl100 = trackEntity.artworkUrl100,
                        previewUrl = trackEntity.previewUrl,
                        favorite = trackEntity.isFavorite
                    )
                },
                coverImageUri = it.playlist.coverImageUri
            )
        }
    }

    override suspend fun createPlaylist(name: String, description: String, coverUri: String?) = withContext(Dispatchers.IO) {
        playlistDao.savePlaylist(PlaylistEntity(name = name, description = description, coverImageUri = coverUri))
    }

    override suspend fun removePlaylist(id: Long) = withContext(Dispatchers.IO) {
        playlistDao.clearCrossRefsForPlaylist(id)
        playlistDao.removePlaylist(PlaylistEntity(id = id, name = "", description = ""))
    }
}