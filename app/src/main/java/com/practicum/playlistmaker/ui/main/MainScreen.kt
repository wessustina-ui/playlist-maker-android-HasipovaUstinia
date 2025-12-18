package com.practicum.playlistmaker.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.materialTheme.YS

private val ActiveBlue = Color(0xFF3772E7)
private val TextGray = Color(0xFFAEAFB4)
private val SurfaceLight = Color.White
private val SurfaceDark = Color(0xFF1A1B22)
private val TitleBlack = Color(0xFF1A1B22)
private val TitleWhite = Color.White

@Composable
fun MainScreen(
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onPlaylistsClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    isDarkTheme: Boolean
) {
    val chevronTint = if (isDarkTheme) TitleWhite else TextGray
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(ActiveBlue)
                .padding(horizontal = 16.dp, vertical = 28.dp)
        ) {
            Text(
                text = stringResource(R.string.playlist_maker),
                color = Color.White,
                fontFamily = YS,
                fontSize = 22.sp,
                lineHeight = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }
        val containerBg = if (isDarkTheme) SurfaceDark else SurfaceLight
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(containerBg)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { MainMenuItem(R.drawable.ic_search_main, stringResource(R.string.search), onSearchClick, chevronTint, isDarkTheme) }
                item { MainMenuItem(R.drawable.ic_playlists, stringResource(R.string.playlists), onPlaylistsClick, chevronTint, isDarkTheme) }
                item { MainMenuItem(R.drawable.ic_favorite, stringResource(R.string.favorites), onFavoritesClick, chevronTint, isDarkTheme) }
                item { MainMenuItem(R.drawable.ic_settings, stringResource(R.string.settings), onSettingsClick, chevronTint, isDarkTheme) }
            }
        }
    }
}

@Composable
private fun MainMenuItem(
    iconResId: Int,
    title: String,
    onClick: () -> Unit,
    chevronTint: Color,
    isDarkTheme: Boolean
) {
    val itemBackground = if (isDarkTheme) SurfaceDark else SurfaceLight
    val contentTint = if (isDarkTheme) TitleWhite else TitleBlack
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .clickable { onClick() }
            .padding(horizontal = 16.dp)
            .background(itemBackground),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = contentTint
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            color = contentTint,
            fontFamily = YS,
            fontSize = 22.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = chevronTint
        )
    }
}