package com.zaidxme.zesho.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaidxme.zesho.ui.components.ZeshoPrimaryButton
import com.zaidxme.zesho.ui.components.ZeshoSecondaryButton
import com.zaidxme.zesho.ui.components.ZeshoTextField
import com.zaidxme.zesho.ui.theme.AlmostBlack
import com.zaidxme.zesho.ui.theme.MixedGradient
import com.zaidxme.zesho.ui.theme.PrimaryGradient
import com.zaidxme.zesho.ui.theme.White
import com.zaidxme.zesho.ui.theme.ZeshoTheme

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import com.zaidxme.zesho.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkSlate,
                        AlmostBlack
                    )
                )
            )
    ) {
        // Decorative background elements
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = (-150).dp, y = (-150).dp)
                .background(PrimaryPurple.copy(alpha = 0.1f), CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            
            // Logo / App Name
            Text(
                text = "ZESHO",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 8.sp
                ),
                color = White
            )
            
            Text(
                text = "Level up your learning",
                style = MaterialTheme.typography.bodyLarge,
                color = White.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(80.dp))
            
            // Input Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                color = DarkerGray.copy(alpha = 0.5f),
                border = AssistChipDefaults.assistChipBorder(enabled = true, borderColor = White.copy(alpha = 0.1f))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sign In",
                        style = MaterialTheme.typography.headlineSmall,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    ModernTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        icon = Icons.Default.Email
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    ModernTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        icon = Icons.Default.Lock,
                        isPassword = true,
                        isPasswordVisible = isPasswordVisible,
                        onPasswordToggle = { isPasswordVisible = !isPasswordVisible }
                    )
                    
                    TextButton(
                        onClick = { /* Forgot password */ },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Forgot Password?", color = PrimaryPurple)
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    ZeshoPrimaryButton(
                        text = "Get Started",
                        onClick = onLoginSuccess,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = White.copy(alpha = 0.1f)
                        )
                        Text(
                            " or ",
                            color = White.copy(alpha = 0.4f),
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = White.copy(alpha = 0.1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SocialLoginButton(
                            text = "Google",
                            icon = Icons.Default.Email, // Placeholder for Google icon
                            onClick = onLoginSuccess,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Don't have an account?", color = White.copy(alpha = 0.6f))
                TextButton(onClick = onSignUpClick) {
                    Text("Create One", color = PrimaryPurple, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onPasswordToggle: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = if (isFocused) PrimaryPurple else White.copy(alpha = 0.4f)) },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = onPasswordToggle) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = White.copy(alpha = 0.4f)
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryPurple,
            unfocusedBorderColor = White.copy(alpha = 0.1f),
            focusedLabelColor = PrimaryPurple,
            unfocusedLabelColor = White.copy(alpha = 0.4f),
            focusedTextColor = White,
            unfocusedTextColor = White
        ),
        visualTransformation = if (isPassword && !isPasswordVisible) 
            androidx.compose.ui.text.input.PasswordVisualTransformation() 
        else 
            androidx.compose.ui.text.input.VisualTransformation.None,
        interactionSource = interactionSource,
        singleLine = true
    )
}

@Composable
fun SocialLoginButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = White
        ),
        border = AssistChipDefaults.assistChipBorder(enabled = true, borderColor = White.copy(alpha = 0.1f))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ZeshoTheme {
        LoginScreen(onLoginSuccess = {}, onSignUpClick = {})
    }
}
