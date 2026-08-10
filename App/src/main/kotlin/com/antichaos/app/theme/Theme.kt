package com.antichaos.app.theme

import androidx.compose.ui.graphics.Color

// Brand colors (from original design)
val Primary = Color(0xFF6C5CE7)       // Purple — main brand color
val Secondary = Color(0xFF00CEC9)     // Teal — secondary accent
val Tertiary = Color(0xFFFFA502)      // Orange — highlights, energy

// Dark theme (default)
val AntiChaosDarkColorScheme = androidx.compose.material3.darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    surfaceVariant = Color(0xFF2D2D2D),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    outline = Color(0xFF666666)
)

// Light theme (optional)
val AntiChaosLightColorScheme = androidx.compose.material3.lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    background = Color.White,
    surface = Color(0xFFF5F5F5),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)
