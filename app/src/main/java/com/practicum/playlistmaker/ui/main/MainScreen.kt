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

// Цвета для выделения
private val PrimaryBlue = Color(0xFF3772E7)
private val NeutralGray = Color(0xFFAEAFB4)
private val LightSurface = Color.White
private val DarkSurface = Color(0xFF1A1B22)
private val TitleColorDark = Color(0xFF1A1B22)
private val TitleColorLight = Color.White

@Composable
fun MainScreen(
    onSearchNavigation: () -> Unit,
    onPlaylistsNavigation: () -> Unit,
    onFavoritesNavigation: () -> Unit,
    onSettingsNavigation: () -> Unit,
    darkThemeEnabled: Boolean
) {
    val chevronColor = if (darkThemeEnabled) TitleColorLight else NeutralGray
    Box(modifier = Modifier.fillMaxSize()) {
        // Заголовок
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryBlue)
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
        val containerBackground = if (darkThemeEnabled) DarkSurface else LightSurface
        // Основная часть с меню
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(containerBackground)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { MenuItem(R.drawable.ic_glass_main, stringResource(R.string.search), onSearchNavigation, chevronColor, darkThemeEnabled) }
                item { MenuItem(R.drawable.ic_playlist, stringResource(R.string.playlists), onPlaylistsNavigation, chevronColor, darkThemeEnabled) }
                item { MenuItem(R.drawable.ic_liked, stringResource(R.string.favorites), onFavoritesNavigation, chevronColor, darkThemeEnabled) }
                item { MenuItem(R.drawable.ic_seting, stringResource(R.string.settings), onSettingsNavigation, chevronColor, darkThemeEnabled) }
            }
        }
    }
}

@Composable
private fun MenuItem(
    iconResId: Int,
    titleText: String,
    onClickAction: () -> Unit,
    chevronColor: Color,
    darkTheme: Boolean
) {
    val backgroundColor = if (darkTheme) DarkSurface else LightSurface
    val textColor = if (darkTheme) TitleColorLight else TitleColorDark
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .clickable { onClickAction() }
            .padding(horizontal = 16.dp)
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = titleText,
            modifier = Modifier.size(24.dp),
            tint = textColor
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = titleText,
            color = textColor,
            fontFamily = YS,
            fontSize = 22.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_r),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = chevronColor
        )
    }
}