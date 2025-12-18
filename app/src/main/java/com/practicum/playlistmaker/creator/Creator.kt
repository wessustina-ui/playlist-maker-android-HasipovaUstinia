package com.practicum.playlistmaker.creator

import android.content.Context
import com.practicum.playlistmaker.data.db.AppDatabase
import com.practicum.playlistmaker.data.network.ITunesApiService
import com.practicum.playlistmaker.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker.data.preferences.SearchHistoryPreferences
import com.practicum.playlistmaker.data.preferences.dataStore
import com.practicum.playlistmaker.data.repository.PlaylistsRepositoryImpl
import com.practicum.playlistmaker.data.repository.SearchHistoryRepositoryImpl
import com.practicum.playlistmaker.data.repository.TracksLocalRepositoryImpl
import com.practicum.playlistmaker.data.repository.TracksRepositoryImpl
import com.practicum.playlistmaker.domain.PlaylistsRepository
import com.practicum.playlistmaker.domain.SearchHistoryRepository
import com.practicum.playlistmaker.domain.TracksLocalRepository
import com.practicum.playlistmaker.domain.TracksRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Creator {
    private const val BASE_URL = "https://itunes.apple.com"

    private val apiService: ITunesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApiService::class.java)
    }

    fun getTracksRepository(): TracksRepository {
        val networkClient = RetrofitNetworkClient(apiService)
        return TracksRepositoryImpl(networkClient)
    }

    fun getAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    fun getPlaylistsRepository(context: Context): PlaylistsRepository {
        val database = getAppDatabase(context)
        return PlaylistsRepositoryImpl(database.playlistDao(), database.trackDao())
    }

    fun getTracksLocalRepository(context: Context): TracksLocalRepository {
        val database = getAppDatabase(context)
        return TracksLocalRepositoryImpl(database.trackDao(), database.playlistDao())
    }

    fun getSearchHistoryRepository(context: Context): SearchHistoryRepository {
        val preferences = SearchHistoryPreferences(context.dataStore)
        return SearchHistoryRepositoryImpl(preferences)
    }
}