package com.travellog.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.travellog.core.ui.theme.*

data class TripCardData(
    val id: String,
    val title: String,
    val flag: String,
    val coverUris: List<String>,       // 최대 3장
    val photoCount: Int,
    val startDate: String,             // "2024.03.15"
    val endDate: String,               // "2024.03.22"
    val durationDays: Int,
    val cities: List<String>,
    val placeCount: Int,
    val badgeCount: Int,
    val accentColor: Long,
)

@Composable
fun TripCard(
    data: TripCardData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(CardRadius),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column {
            // 커버 이미지 (2:1 비율)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                    .clip(RoundedCornerShape(topStart = CardRadius, topEnd = CardRadius))
                    .background(Color(data.accentColor).copy(alpha = 0.2f)),
            ) {
                CoverGrid(uris = data.coverUris)

                // 그라데이션 오버레이
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.45f))
                            )
                        )
                )

                // 국기 + 제목 (좌하단)
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(data.flag, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = data.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                    )
                }

                // 사진 수 (우하단)
                Text(
                    text = "📷 ${data.photoCount}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Black.copy(alpha = 0.4f))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                )
            }

            // 카드 하단 정보
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // 날짜 + 일수 배지
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "${data.startDate} ~ ${data.endDate}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                    )
                    Text(
                        text = "${data.durationDays}일",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextPrimary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(YellowLight)
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                    )
                }

                // 도시 칩 목록
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(data.cities) { city ->
                        CityChip(label = city)
                    }
                }

                // 장소 수 + 뱃지 수
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MetaInfo(icon = "📍", count = data.placeCount, label = "장소")
                    MetaInfo(icon = "🧲", count = data.badgeCount, label = "마그넷")
                }
            }
        }
    }
}

@Composable
private fun CoverGrid(uris: List<String>) {
    when (uris.size) {
        0 -> Box(Modifier.fillMaxSize().background(Color(0xFFE0E0E0)))
        1 -> AsyncImage(
            model = uris[0], contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        2 -> Row(Modifier.fillMaxSize()) {
            uris.forEach { uri ->
                AsyncImage(
                    model = uri, contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                )
            }
        }
        else -> Row(Modifier.fillMaxSize()) {
            AsyncImage(
                model = uris[0], contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight(),
            )
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                uris.drop(1).take(2).forEach { uri ->
                    AsyncImage(
                        model = uri, contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
private fun MetaInfo(icon: String, count: Int, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(icon, style = MaterialTheme.typography.labelSmall)
        Text(
            text = "$count $label",
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary,
        )
    }
}
