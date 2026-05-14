package com.zaidxme.zesho.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zaidxme.zesho.ui.theme.DarkerGray
import com.zaidxme.zesho.ui.theme.PrimaryPurple
import com.zaidxme.zesho.ui.theme.White

@Composable
fun ModernSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search resources..."
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = White.copy(alpha = 0.4f)) },
        leadingIcon = { 
            Icon(
                Icons.Default.Search, 
                contentDescription = null, 
                tint = if (isFocused) PrimaryPurple else White.copy(alpha = 0.4f)
            ) 
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryPurple,
            unfocusedBorderColor = White.copy(alpha = 0.1f),
            focusedContainerColor = DarkerGray.copy(alpha = 0.5f),
            unfocusedContainerColor = DarkerGray.copy(alpha = 0.3f),
            focusedTextColor = White,
            unfocusedTextColor = White
        ),
        interactionSource = interactionSource,
        singleLine = true
    )
}
