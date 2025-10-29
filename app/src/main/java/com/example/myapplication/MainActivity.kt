package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
            verticalArrangement = Arrangement.SpaceBetween // Распределение пространства между элементами
        ) {
            Header()
            BottomWhiteBlock(modifier = Modifier.weight(1f)) // Используем weight для растягивания блока
        }
    }
}

@Composable
fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF3772E7)), // Цвет фона блока
        contentAlignment = Alignment.CenterStart // Выравнивание текста по левому краю
    ) {
        Text(
            text = "Playlist maker",
            color = Color.White, // Цвет текста
            style = customTextStyle(), // Применяем пользовательский стиль текста
            modifier = Modifier.padding(start = 16.dp) // Отступ от левого края
        )
    }
}

// Функция для определения пользовательского стиля текста
@Composable
fun customTextStyle(): TextStyle {
    return TextStyle(
        fontSize = 22.sp, // Размер шрифта 22px (22sp в Compose)
        lineHeight = 22.sp, // Установите lineHeight в соответствии с вашими требованиями (100% от fontSize)
        letterSpacing = 0.sp // Установите letterSpacing в соответствии с вашими требованиями (по умолчанию 0)
    )
}

@Composable
fun BottomWhiteBlock(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) // Белый фон с закругленными углами сверху
            .padding(top = 8.dp, start = 16.dp, end = 16.dp) // Внутренние отступы
    ) {
        ButtonWithPadding(text = "Поиск", leftIconResId = R.drawable.search, rightIconResId = R.drawable.continue_btn, targetActivity = SearchActivity::class.java ) // Замените на ваш ресурс
        ButtonWithPadding(text = "Плейлист", leftIconResId = R.drawable.music, rightIconResId = R.drawable.continue_btn,targetActivity = SearchActivity::class.java) // Замените на ваш ресурс
        ButtonWithPadding(text = "Избранное", leftIconResId = R.drawable.like, rightIconResId = R.drawable.continue_btn,targetActivity = SearchActivity::class.java) // Замените на ваш ресурс
        ButtonWithPadding(text = "Настройки", leftIconResId = R.drawable.setting, rightIconResId = R.drawable.continue_btn, targetActivity = SettingsActivity::class.java) // Замените на ваш ресурс
    }
}

@Composable
fun ButtonWithPadding(text: String, leftIconResId: Int, rightIconResId: Int, targetActivity: Class<*>) {
    val context = LocalContext.current // Получаем контекст для использования в Toast

    Button(
        onClick = {
            val intent = Intent(context, targetActivity)
            context.startActivity(intent)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 21.dp) // Отступ сверху и снизу
            .padding(horizontal = 12.dp), // Отступ по бокам
        colors = ButtonDefaults.buttonColors(containerColor = Color.White) // Фон кнопки белый
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Размещаем элементы по краям
        ) {
            // Левая иконка
            Image(
                painter = painterResource(id = leftIconResId),
                contentDescription = null,
                modifier = Modifier.size(24.dp) // Размер левой иконки
            )

            // Текст кнопки
            Text(
                text = text,
                color = Color.Black, // Цвет текста (можно изменить)
                modifier = Modifier.weight(1f) // Позволяем тексту занимать доступное пространство
                    .padding(start = 8.dp), // Отступ между текстом и левой иконкой
                style = TextStyle(
                    fontSize = 22.sp, // Размер шрифта
                    lineHeight = 22.sp, // Высота строки (можно настроить)
                    textAlign = TextAlign.Left // Выравнивание текста по центру
                )
            )

            // Правая иконка
            Image(
                painter = painterResource(id = rightIconResId),
                contentDescription = null,
                modifier = Modifier.size(24.dp) // Размер правой иконки
            )
        }
    }
}