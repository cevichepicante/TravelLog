package com.travellog.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.travellog.core.ui.theme.*

@Composable
fun CityChip(
    label: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = TextPrimary,
        modifier = modifier
            .clip(RoundedCornerShape(ChipRadius))
            .background(YellowLight)
            .padding(horizontal = 10.dp, vertical = 4.dp),
    )
}
