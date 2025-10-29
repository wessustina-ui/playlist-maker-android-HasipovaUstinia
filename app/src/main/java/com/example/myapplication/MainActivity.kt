package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun MainContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize().background(Color(0xFF3772E7))) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Header()
            BottomWhiteBlock(modifier = Modifier.weight(1f))
        }
    }
}
@Composable
fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF3772E7)),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "Playlist maker",
            color = Color.White,
            style = customTextStyle(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
@Composable
fun customTextStyle(): TextStyle {
    return TextStyle(
        fontSize = 22.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    )
}
@Composable
fun BottomWhiteBlock(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        ButtonWithPadding(stringResource(id = R.string.search),
            leftIconResId = R.drawable.search,
            rightIconResId = R.drawable.continue_btn,
            targetActivity = SearchActivity::class.java
        )
        ButtonWithPadding(text = stringResource(R.string.playlist),
            leftIconResId = R.drawable.music,
            rightIconResId = R.drawable.continue_btn,
            targetActivity = SearchActivity::class.java
        )
        ButtonWithPadding(text = stringResource(R.string.favorite),
            leftIconResId = R.drawable.like,
            rightIconResId = R.drawable.continue_btn,
            targetActivity = SearchActivity::class.java
        )
        ButtonWithPadding(stringResource(id = R.string.settings),
            leftIconResId = R.drawable.setting,
            rightIconResId = R.drawable.continue_btn,
            targetActivity = SettingsActivity::class.java
        )
    }
}
@Composable
fun ButtonWithPadding(text: String, leftIconResId: Int, rightIconResId: Int, targetActivity: Class<*>) {val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(context, targetActivity)
            context.startActivity(intent)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 21.dp)
            .padding(horizontal = 12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = leftIconResId),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = text,
                color = Color.Black,
                modifier = Modifier.weight(1f)
                    .padding(start = 8.dp),
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Left
                )
            )
            Image(
                painter = painterResource(id = rightIconResId),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}