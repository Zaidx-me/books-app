package com.zaidxme.zesho.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Primary Colors
val PrimaryPurple = Color(0xFF6366F1)
val SecondaryIndigo = Color(0xFF4F46E5)
val AccentBlue = Color(0xFF3B82F6)

// Gradient Colors
val Violet = Color(0xFF8B5CF6)
val Pink = Color(0xFFEC4899)

// Neutral Colors
val White = Color(0xFFFFFFFF)
val LightGray = Color(0xFFF9FAFB)
val MediumGray = Color(0xFFE5E7EB)
val DarkGray = Color(0xFF4B5563)
val DarkerGray = Color(0xFF1F2937)
val AlmostBlack = Color(0xFF111827)
val DarkSlate = Color(0xFF0F172A)

// Semantic Colors
val SuccessGreen = Color(0xFF10B981)
val WarningYellow = Color(0xFFF59E0B)
val ErrorRed = Color(0xFFEF4444)
val InfoBlue = Color(0xFF3B82F6)

// Gradients
val PrimaryGradient = Brush.linearGradient(
    colors = listOf(PrimaryPurple, SecondaryIndigo)
)

val AccentGradient = Brush.linearGradient(
    colors = listOf(Violet, AccentBlue)
)

val MixedGradient = Brush.linearGradient(
    colors = listOf(Pink, Violet, AccentBlue)
)
