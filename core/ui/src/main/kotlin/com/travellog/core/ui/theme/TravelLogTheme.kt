package com.travellog.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ── Colors ──────────────────────────────────────────────
val Yellow        = Color(0xFFF5C842)
val YellowLight   = Color(0xFFFFF8E1)
val Purple        = Color(0xFFC4A8E0)
val PurpleLight   = Color(0xFFF3ECFA)
val Orange        = Color(0xFFF0885C)
val OrangeLight   = Color(0xFFFFF0E8)
val Green         = Color(0xFF6BBF8A)

val Background    = Color(0xFFFAFAF7)
val Surface       = Color(0xFFFFFFFF)
val TextPrimary   = Color(0xFF1A1A1A)
val TextSecondary = Color(0xFF6B6B6B)
val TextTertiary  = Color(0xFF9E9E9E)
val Border        = Color(0x0F000000)

private val LightColorScheme = lightColorScheme(
    primary          = Yellow,
    secondary        = Purple,
    tertiary         = Orange,
    background       = Background,
    surface          = Surface,
    onPrimary        = TextPrimary,
    onBackground     = TextPrimary,
    onSurface        = TextPrimary,
    onSurfaceVariant = TextSecondary,
)

// ── Theme ────────────────────────────────────────────────
@Composable
fun TravelLogTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        shapes      = TravelLogShapes,
        typography  = TravelLogTypography,
        content     = content,
    )
}
