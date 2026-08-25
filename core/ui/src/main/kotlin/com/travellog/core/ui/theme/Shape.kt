package com.travellog.core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val CardRadius = 20.dp
val ChipRadius = 12.dp
val ButtonRadius = 14.dp
val BottomSheetRadius = 22.dp

val TravelLogShapes = Shapes(
    extraSmall = RoundedCornerShape(ChipRadius),
    small = RoundedCornerShape(ButtonRadius),
    medium = RoundedCornerShape(CardRadius),
    large = RoundedCornerShape(BottomSheetRadius),
    extraLarge = RoundedCornerShape(BottomSheetRadius),
)
