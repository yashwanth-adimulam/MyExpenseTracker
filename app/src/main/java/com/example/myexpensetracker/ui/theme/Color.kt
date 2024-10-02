package com.example.myexpensetracker.ui.theme

import androidx.compose.ui.graphics.Color

val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val Zinc = Color(0xFF2F7E79)
val LightGrey = Color(0xFF666666)
val Navy = Color(0xFF1B1B1F)
val Teal = Color(0xFF00AEAE)
val LightBlue = Color(0xFF007AAA)
val ButtonBlue = Color(0xFF3F9BE2)
val Teal80 = Color(0xCCFFFFFF)
val Indigo = Color(0xFF4B0082)
val Amethyst = Color(0xFF9370DB)
val White = Color(0xFFFFFFFF)
val LightRed = Color(0xFFE57373)

sealed class ThemeColors(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val text: Color
) {
    data object Night : ThemeColors(
        background = Navy,
        surface = Teal,
        primary = Zinc,
        secondary = Indigo,
        tertiary = Amethyst,
        text = White
    )

    data object Day : ThemeColors(
        background = Color.White,
        surface = Color.White,
        primary = Zinc,
        secondary = PurpleGrey40,
        tertiary = Pink40,
        text = Color.Black
    )
}