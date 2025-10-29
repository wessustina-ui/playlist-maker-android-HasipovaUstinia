package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.res.stringResource

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = Color.White) {
                Column {
                    SettingsHeader(onBackClick = { finish() })
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                    ) {
                        ContainerRow(
                            leftText = stringResource(id = R.string.dark_theme),
                            rightContent = {
                                var isChecked by remember { mutableStateOf(false) }
                                CustomSwitch(
                                    checked = isChecked,
                                    onCheckedChange = { isChecked = it }
                                )
                            }
                        )
                        ContainerRow(
                            leftText = stringResource(id = R.string.share_app),
                            rightContent = {
                                ShareAppRow()
                            }
                        )
                        SupportRow(supportEmail = "student@example.com")
                        UserAgreementRow()
                    }
                }
            }
        }
    }
}@Composable
fun SettingsHeader(onBackClick: () -> Unit) {
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
                text = stringResource(id = R.string.settings),
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ContainerRow(
    leftText: String,
    rightContent: @Composable () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp),
        color = Color.White
    ) {
        Row(

            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = leftText,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 20.sp
                )
            )
            rightContent()
        }
    }
}
@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    trackWidth: Dp = 32.dp,
    trackHeight: Dp = 12.dp,
    thumbSize: Dp = 18.dp
) {
    val trackColor = animateColorAsState(targetValue = if (checked){ Color(0xFF3772E7) } else Color(0xFFE6E8EB))
    val thumbColor = animateColorAsState(targetValue = if (checked) Color(0xFF3772E7) else Color(0xFFAEAFB4))
    val thumbOffset by animateDpAsState(targetValue = if (checked) trackWidth - thumbSize else 0.dp)
    val trackOpacity by animateFloatAsState(targetValue = if (checked) 0.48f else 1f)
    Box(
        modifier = modifier
            .width(trackWidth)
            .height(thumbSize)
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.CenterStart
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(trackHeight)
        ) {
            drawRoundRect(
                color = trackColor.value.copy(alpha = trackOpacity),
                cornerRadius = CornerRadius(x = trackHeight.toPx() / 2, y = trackHeight.toPx() / 2),
                style = Fill,
            )
        }
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .height(thumbSize)
                .width(thumbSize)
                .clip(CircleShape)
                .background(thumbColor.value)
                .border(
                    width = 0.dp,
                    color = Color.Transparent,
                    shape = CircleShape
                )
        )
    }
}
@Composable
fun ShareAppRow() {
    val context = LocalContext.current
    Icon(
        imageVector = Icons.Default.Share,
        contentDescription = null,
        tint = Color.Gray,
        modifier = Modifier
            .size(24.dp)
            .clickable {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Здравствуйте! Посмотрите это приложение: [ваша ссылка]")
                    type = "text/plain"
                }
                context.startActivity(
                    Intent.createChooser(shareIntent, "Поделиться через")
                )
            }
    )
}
@Composable
fun SupportRow(
    supportEmail: String,
    leftText: String = stringResource(id = R.string.write_to_support),
    emailSubject: String = stringResource(id = R.string.message_from_developers),
    emailBody: String = stringResource(id = R.string.thanks_from_developers)
) {
    val context = LocalContext.current
    ContainerRow(
        leftText = leftText,
        rightContent = {
            Image(
                painter = painterResource(R.drawable.support),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:$supportEmail")
                            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                            putExtra(Intent.EXTRA_TEXT, emailBody)
                        }
                        if (intent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(context, "Почтовый клиент не установлен", Toast.LENGTH_SHORT).show()
                        }
                    }
            )
        }
    )
}
@Composable
fun UserAgreementRow() {
    val context = LocalContext.current
    val url = stringResource(id = R.string.offer_url)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                }
            }
    ) {
        ContainerRow(
            leftText = stringResource(id = R.string.user_agreement),
            rightContent = {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        )
    }
}