package com.zaidxme.zesho.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaidxme.zesho.ui.theme.DarkSlate
import com.zaidxme.zesho.ui.theme.White

@Composable
fun GlassTopBar(
    title: String,
    modifier: Modifier = Modifier,
    alpha: Float = 0.8f,
    content: @Composable RowScope.() -> Unit = {}
) {
    val backgroundColor = if (isSystemInDarkTheme()) DarkSlate else Color.White
    val contentColor = if (isSystemInDarkTheme()) White else Color.Black

    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(64.dp)
            .background(backgroundColor.copy(alpha = alpha))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = contentColor,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                content = content
            )
        }
        
        // Bottom border line for glass effect
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.BottomCenter)
                .background(contentColor.copy(alpha = 0.1f))
        )
    }
}
