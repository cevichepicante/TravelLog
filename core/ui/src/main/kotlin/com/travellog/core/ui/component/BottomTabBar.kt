package com.travellog.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.travellog.core.ui.theme.*

enum class TravelTab(
    val label: String,
    val icon: ImageVector,
    val route: String,
) {
    MAP("지도", Icons.Default.Map, "map"),
    RECORDS("기록", Icons.Default.Article, "records"),
    SCHEDULE("일정", Icons.Default.CalendarMonth, "schedule"),
    PROFILE("프로필", Icons.Default.Person, "profile"),
}

@Composable
fun TravelLogBottomBar(
    currentRoute: String?,
    onTabSelected: (TravelTab) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Surface)
            .windowInsetsPadding(WindowInsets.navigationBars),
    ) {
        // 상단 구분선
        HorizontalDivider(color = Border, thickness = 1.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 지도, 기록 탭
            TravelTab.entries.take(2).forEach { tab ->
                TabItem(
                    tab = tab,
                    selected = currentRoute == tab.route,
                    onClick = { onTabSelected(tab) },
                    modifier = Modifier.weight(1f),
                )
            }

            // 중앙 FAB
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Yellow)
                        .clickable(onClick = onAddClick),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "기록 추가",
                        tint = TextPrimary,
                        modifier = Modifier.size(28.dp),
                    )
                }
            }

            // 일정, 프로필 탭
            TravelTab.entries.drop(2).forEach { tab ->
                TabItem(
                    tab = tab,
                    selected = currentRoute == tab.route,
                    onClick = { onTabSelected(tab) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun TabItem(
    tab: TravelTab,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = if (selected) Yellow else TextTertiary
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = tab.icon,
            contentDescription = tab.label,
            tint = color,
            modifier = Modifier.size(24.dp),
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = tab.label,
            style = MaterialTheme.typography.labelSmall,
            color = color,
        )
    }
}
