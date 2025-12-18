package com.practicum.playlistmaker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.presentation.AppTrack
import com.practicum.playlistmaker.ui.materialTheme.YS
import kotlin.math.max

@Composable
fun TrackRow(
    track: AppTrack,
    isDarkTheme: Boolean,
    onClick: () -> Unit = {},
    onLongClick: (() -> Unit)? = null
) {
    val itemBackground = if (!isDarkTheme) Color.White else Color(0xFF1A1B22)
    val textPrimary = if (!isDarkTheme) Color(0xFF1A1B22) else Color.White
    val textSecondary = if (!isDarkTheme) Color(0xFFAEAFB4) else Color.White.copy(alpha = 0.84f)
    val chevronTint = if (!isDarkTheme) Color(0xFFAEAFB4) else Color.White

    val rowModifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 61.dp)
        .background(itemBackground)
        .let { modifier ->
            if (onLongClick != null) {
                modifier.combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                )
            } else {
                modifier.clickable(onClick = onClick)
            }
        }
        .padding(horizontal = 13.dp, vertical = 8.dp)

    Row(
        modifier = rowModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = track.artworkUrl100 ?: R.drawable.ic_music,
            placeholder = painterResource(R.drawable.ic_music),
            error = painterResource(R.drawable.ic_music),
            contentDescription = stringResource(R.string.track_item_description, track.trackName),
            modifier = Modifier
                .size(45.dp)
                .clip(RoundedCornerShape(0.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = track.trackName,
                fontFamily = YS,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 19.sp,
                color = textPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            ArtistWithDuration(
                artistName = track.artistName,
                duration = track.trackTime,
                textColor = textSecondary
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = null,
            tint = chevronTint,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun ArtistWithDuration(
    artistName: String,
    duration: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Layout(
        modifier = modifier.fillMaxWidth(),
        content = {
            ArtistText(artistName = artistName, textColor = textColor)
            DurationPart(duration = duration, textColor = textColor)
        }
    ) { measurables, constraints ->
        val fixedMeasurable: Measurable = measurables[1]
        val fixedPlaceable = fixedMeasurable.measure(constraints.copy(minWidth = 0, minHeight = 0))

        val artistConstraints = constraints.copy(
            maxWidth = constraints.maxWidth - fixedPlaceable.width,
            minWidth = 0
        )
        val artistPlaceable = measurables[0].measure(artistConstraints)

        val width = constraints.maxWidth
        val height = max(artistPlaceable.height, fixedPlaceable.height)

        layout(width, height) {
            artistPlaceable.placeRelative(0, (height - artistPlaceable.height) / 2)
            fixedPlaceable.placeRelative(artistPlaceable.width, (height - fixedPlaceable.height) / 2)
        }
    }
}

@Composable
private fun ArtistText(artistName: String, textColor: Color) {
    Text(
        text = artistName,
        fontFamily = YS,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 13.sp,
        color = textColor,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun DurationPart(duration: String, textColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(4.dp))
        Box(
            modifier = Modifier
                .size(3.dp)
                .background(textColor, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = duration,
            fontFamily = YS,
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            lineHeight = 13.sp,
            color = textColor
        )
    }
}