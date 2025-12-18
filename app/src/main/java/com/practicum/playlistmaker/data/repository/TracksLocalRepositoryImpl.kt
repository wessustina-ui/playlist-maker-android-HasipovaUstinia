package com.practicum.playlistmaker.data.repository

import com.practicum.playlistmaker.data.dao.PlaylistRepository
import com.practicum.playlistmaker.data.dao.TrackRepository
import com.practicum.playlistmaker.data.entity.PlaylistTrackCrossRef
import com.practicum.playlistmaker.data.entity.TrackEntity
import com.practicum.playlistmaker.domain.Track
import com.practicum.playlistmaker.domain.TracksLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TracksLocalRepositoryImpl(
    private val trackDao: TrackRepository,
    private val playlistDao: PlaylistRepository
) : TracksLocalRepository {
    override suspend fun addTrackToPlaylist(track: Track, playlistId: Long) = withContext(Dispatchers.IO) {
        var entity = trackDao.fetchTrackById(track.trackId).firstOrNull()
        if (entity == null) {
            entity = TrackEntity(
                trackId = track.trackId,
                trackName = track.trackName,
                artistName = track.artistName,
                trackTimeMillis = track.trackTimeMillis,
                artworkUrl100 = track.artworkUrl100,
                previewUrl = track.previewUrl,
                isFavorite = track.favorite
            )
            trackDao.addTrack(entity)
        }
        playlistDao.addCrossReference(PlaylistTrackCrossRef(playlistId, track.trackId))
    }

    override suspend fun removeTrackFromPlaylist(trackId: Long, playlistId: Long) = withContext(Dispatchers.IO) {
        playlistDao.removeSpecificCrossRef(playlistId, trackId)
        val count = trackDao.getPlaylistCountForTrack(trackId)
        val entity = trackDao.fetchTrackById(trackId).firstOrNull()
        if (entity != null && !entity.isFavorite && count == 0L) {
            trackDao.removeTrack(entity)
        }
    }

    override suspend fun setTrackFavoriteStatus(track: Track, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        var existing = trackDao.fetchTrackById(track.trackId).firstOrNull()
        if (existing == null) {
            if (isFavorite) {
                existing = TrackEntity(
                    trackId = track.trackId,
                    trackName = track.trackName,
                    artistName = track.artistName,
                    trackTimeMillis = track.trackTimeMillis,
                    artworkUrl100 = track.artworkUrl100,
                    previewUrl = track.previewUrl,
                    isFavorite = true
                )
                trackDao.addTrack(existing)
            }
        } else {
            val updated = existing.copy(isFavorite = isFavorite)
            trackDao.refreshTrack(updated)
            if (!isFavorite) {
                val count = trackDao.getPlaylistCountForTrack(track.trackId)
                if (count == 0L) {
                    trackDao.refreshTrack(updated)
                }
            }
        }
    }

    override fun fetchFavoriteTracks(): Flow<List<Track>> = trackDao.fetchFavoriteTracks().map { entities ->
        entities.map {
            Track(
                trackId = it.trackId,
                trackName = it.trackName,
                artistName = it.artistName,
                trackTimeMillis = it.trackTimeMillis,
                artworkUrl100 = it.artworkUrl100,
                previewUrl = it.previewUrl,
                favorite = it.isFavorite
            )
        }
    }

    override fun fetchTrackById(trackId: Long): Flow<Track?> = trackDao.fetchTrackById(trackId).map { entity ->
        entity?.let {
            Track(
                trackId = it.trackId,
                trackName = it.trackName,
                artistName = it.artistName,
                trackTimeMillis = it.trackTimeMillis,
                artworkUrl100 = it.artworkUrl100,
                previewUrl = it.previewUrl,
                favorite = it.isFavorite
            )
        }
    }
}