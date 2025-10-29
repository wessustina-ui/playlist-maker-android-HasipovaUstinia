package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.componentUI.SearchState
import com.example.myapplication.componentUI.TrackListItem
import com.example.myapplication.componentUI.SearchViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModelProvider
class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this, SearchViewModel.getViewModelFactory())
            .get(SearchViewModel::class.java)
        setContent {
            MaterialTheme {
                Surface(color = Color.White) {
                    val state by viewModel.searchScreenState.collectAsState()
                    Column {
                        SearchHeader(onBackClick = { finish() })
                        SearchScreen(
                            viewModel = viewModel,
                            searchState = state
                        )

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
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    searchState: SearchState
) {
    var text by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        val placeholderTextStyle = LocalTextStyle.current.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            color = Color(0xFFAEAFB4)
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = { viewModel.search(text) }) {Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFFAEAFB4)
                )
                }
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.search),
                    style = placeholderTextStyle
                )
            },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(onClick = { text = "" }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear"
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
                cursorColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(color = Color(0xFFE6E8EB), shape = RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (searchState) {
            is SearchState.Initial -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    stringResource(R.string.search)
                }
            }
            is SearchState.Searching -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is SearchState.Success -> {
                LazyColumn {
                    items(searchState.foundList) { track ->
                        TrackListItem(track)
                        Divider(thickness = 0.5.dp)
                    }
                }
            }
            is SearchState.Fail -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Ошибка: ${searchState.error}", color = Color.Red)
                }
            }
        }
    }
}