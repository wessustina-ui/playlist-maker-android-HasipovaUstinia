package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Column {
                        SearchHeader(onBackClick = { finish() })
                        SearchScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun SearchHeader(onBackClick: () -> Unit) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(id = R.string.search),
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun SearchScreen() {
    var searchQuery by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = { /* Логика нажатия на лупу остается пустой */ }) {
                    Image(
                        painter = painterResource(R.drawable.grey_search_icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp) // Размер иконки
                    )
                }
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search),
                    color = Color(0xFFAEAFB4),
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Start
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear Icon"
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                focusedContainerColor = Color(0xFFE6E8EB),
                unfocusedContainerColor = Color(0xFFE6E8EB),
                disabledContainerColor = Color(0xFFE6E8EB),
                focusedPlaceholderColor = Color(0xFF7D7E83),
                unfocusedPlaceholderColor = Color(0xFF7D7E83),
                disabledPlaceholderColor = Color(0xFFB0B1B6),
                cursorColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)  // высота инпута 36dp внутри контейнера 52dp
        )
    }
}