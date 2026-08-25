package com.travellog.app.navigation

sealed class Route(val route: String) {
    // 탭
    data object Map : Route("map")
    data object Records : Route("records")
    data object Schedule : Route("schedule")
    data object Profile : Route("profile")

    // 기록 탭 하위
    data object TripDetail : Route("trip_detail/{tripId}") {
        fun createRoute(tripId: String) = "trip_detail/$tripId"
        const val ARG_TRIP_ID = "tripId"
    }

    // FAB 플로우
    data object AddRecord : Route("add_record")
}
