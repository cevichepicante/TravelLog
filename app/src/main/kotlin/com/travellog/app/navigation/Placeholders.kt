package com.travellog.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable fun MapPlaceholder() = ScreenPlaceholder("지도")
@Composable fun RecordsPlaceholder(onTripClick: (String) -> Unit) = ScreenPlaceholder("기록")
@Composable fun TripDetailPlaceholder(tripId: String, onBack: () -> Unit) = ScreenPlaceholder("여행 상세: $tripId")
@Composable fun SchedulePlaceholder() = ScreenPlaceholder("일정")
@Composable fun ProfilePlaceholder() = ScreenPlaceholder("프로필")
@Composable fun AddRecordPlaceholder(onDismiss: () -> Unit) = ScreenPlaceholder("기록 추가")

@Composable
private fun ScreenPlaceholder(name: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(name)
    }
}
