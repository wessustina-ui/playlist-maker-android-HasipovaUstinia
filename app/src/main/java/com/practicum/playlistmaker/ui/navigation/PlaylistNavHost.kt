package com.practicum.playlistmaker.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.practicum.playlistmaker.domain.PlaylistsRepository
import com.practicum.playlistmaker.domain.TracksLocalRepository
import com.practicum.playlistmaker.domain.TracksRepository
import com.practicum.playlistmaker.domain.SearchHistoryRepository
import com.practicum.playlistmaker.presentation.AppTrack
import com.practicum.playlistmaker.ui.favorites.FavoritesScreen
import com.practicum.playlistmaker.ui.favorites.FavoritesViewModel
import com.practicum.playlistmaker.ui.favorites.FavoritesViewModelFactory
import com.practicum.playlistmaker.ui.main.MainScreen
import com.practicum.playlistmaker.ui.playlists.CreatePlaylistScreen
import com.practicum.playlistmaker.ui.playlists.PlaylistDetailsScreen
import com.practicum.playlistmaker.ui.playlists.PlaylistViewModel
import com.practicum.playlistmaker.ui.playlists.PlaylistViewModelFactory
import com.practicum.playlistmaker.ui.playlists.PlaylistsScreen
import com.practicum.playlistmaker.ui.search.SearchScreen
import com.practicum.playlistmaker.ui.search.SearchViewModel
import com.practicum.playlistmaker.ui.search.SearchViewModelFactory
import com.practicum.playlistmaker.ui.settings.SettingsScreen
import com.practicum.playlistmaker.ui.track.TrackDetailsScreen
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PlaylistNavHost(
    searchRepository: TracksRepository,
    playlistsRepository: PlaylistsRepository,
    localTracksRepository: TracksLocalRepository,
    searchHistoryRepository: SearchHistoryRepository,
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MAIN.name) {
        composable(Screen.MAIN.name) {
            MainScreen(
                onSearchNavigation = { navController.navigate(Screen.SEARCH.name) },
                onPlaylistsNavigation = { navController.navigate(Screen.PLAYLISTS.name) },
                onFavoritesNavigation = { navController.navigate(Screen.FAVORITES.name) },
                onSettingsNavigation = { navController.navigate(Screen.SETTINGS.name) },
                darkThemeEnabled = isDarkTheme
            )
        }
        composable(Screen.SEARCH.name) {
            val viewModelFactory = SearchViewModelFactory(searchRepository, searchHistoryRepository)
            val searchViewModel: SearchViewModel = viewModel(factory = viewModelFactory)
            SearchScreen(
                viewModel = searchViewModel,
                isDarkTheme = isDarkTheme,
                onBackClick = { navController.popBackStack() },
                onTrackClick = { track ->
                    navController.navigate(
                        "${Screen.TRACK_DETAILS.name}/" +
                                "${track.trackId}/" +
                                "${Uri.encode(track.trackName)}/" +
                                "${Uri.encode(track.artistName)}/" +
                                "${Uri.encode(track.trackTime)}/" +
                                "${Uri.encode(track.artworkUrl100)}"
                    )
                }
            )
        }
        composable(Screen.SETTINGS.name) {
            SettingsScreen(
                isDarkTheme = isDarkTheme,
                onToggleTheme = onThemeToggle,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.PLAYLISTS.name) {
            val viewModelFactory = PlaylistViewModelFactory(playlistsRepository, localTracksRepository)
            val playlistViewModel: PlaylistViewModel = viewModel(factory = viewModelFactory)
            PlaylistsScreen(
                viewModel = playlistViewModel,
                onCreatePlaylist = { navController.navigate(Screen.CREATE_PLAYLIST.name) },
                onOpenPlaylist = { playlistId -> navController.navigate("${Screen.PLAYLIST_DETAILS.name}/$playlistId") },
                onBackClick = { navController.popBackStack() },
                isDarkTheme = isDarkTheme
            )
        }
        composable(Screen.CREATE_PLAYLIST.name) {
            val viewModelFactory = PlaylistViewModelFactory(playlistsRepository, localTracksRepository)
            val playlistViewModel: PlaylistViewModel = viewModel(factory = viewModelFactory)
            CreatePlaylistScreen(
                viewModel = playlistViewModel,
                onBackClick = { navController.popBackStack() },
                onSaved = { navController.popBackStack() },
                isDarkTheme = isDarkTheme
            )
        }
        composable(
            route = "${Screen.TRACK_DETAILS.name}/{trackId}/{trackName}/{artistName}/{trackTime}/{artworkUrl100}",
            arguments = listOf(
                navArgument("trackId") { type = NavType.LongType },
                navArgument("trackName") { type = NavType.StringType },
                navArgument("artistName") { type = NavType.StringType },
                navArgument("trackTime") { type = NavType.StringType },
                navArgument("artworkUrl100") { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->
            val args = backStackEntry.arguments
            val trackId = args?.getLong("trackId") ?: 0L
            val trackName = args?.getString("trackName") ?: ""
            val artistName = args?.getString("artistName") ?: ""
            val trackTime = args?.getString("trackTime") ?: "0:00"
            val artworkUrl = args?.getString("artworkUrl100")
            val appTrack = AppTrack(trackId, trackName, artistName, trackTime, artworkUrl)
            val viewModelFactory = PlaylistViewModelFactory(playlistsRepository, localTracksRepository)
            val playlistViewModel: PlaylistViewModel = viewModel(factory = viewModelFactory)
            TrackDetailsScreen(
                appTrack = appTrack,
                playlistViewModel = playlistViewModel,
                onBack = { navController.popBackStack() },
                isDarkTheme = isDarkTheme
            )
        }
        composable(Screen.FAVORITES.name) {
            val viewModelFactory = FavoritesViewModelFactory(localTracksRepository)
            val favoritesViewModel: FavoritesViewModel = viewModel(factory = viewModelFactory)
            FavoritesScreen(
                viewModel = favoritesViewModel,
                onBackNavigation = { navController.popBackStack() },
                onTrackSelected = { track ->
                    navController.navigate(
                        "${Screen.TRACK_DETAILS.name}/" +
                                "${track.trackId}/" +
                                "${Uri.encode(track.trackName)}/" +
                                "${Uri.encode(track.artistName)}/" +
                                "${Uri.encode(track.trackTime)}/" +
                                "${Uri.encode(track.artworkUrl100)}"
                    )
                },
                darkThemeEnabled = isDarkTheme
            )
        }
        composable(
            route = "${Screen.PLAYLIST_DETAILS.name}/{playlistId}",
            arguments = listOf(navArgument("playlistId") { type = NavType.LongType })
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L
            val viewModelFactory = PlaylistViewModelFactory(playlistsRepository, localTracksRepository)
            val playlistViewModel: PlaylistViewModel = viewModel(factory = viewModelFactory)
            PlaylistDetailsScreen(
                playlistId = playlistId,
                viewModel = playlistViewModel,
                onBackClick = { navController.popBackStack() },
                onTrackClick = { track ->
                    navController.navigate(
                        "${Screen.TRACK_DETAILS.name}/" +
                                "${track.trackId}/" +
                                "${Uri.encode(track.trackName)}/" +
                                "${Uri.encode(track.artistName)}/" +
                                "${Uri.encode(track.trackTime)}/" +
                                "${Uri.encode(track.artworkUrl100)}"
                    )
                },
                isDarkTheme = isDarkTheme
            )
        }
    }
}