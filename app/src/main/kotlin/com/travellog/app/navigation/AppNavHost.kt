package com.travellog.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.travellog.feature.map.GlobeMapScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Route.Map.route,
        modifier = modifier,
    ) {
        composable(Route.Map.route) {
            GlobeMapScreen(modifier = Modifier.fillMaxSize())
        }
        composable(Route.Records.route) {
            RecordsPlaceholder(
                onTripClick = { tripId ->
                    navController.navigate(Route.TripDetail.createRoute(tripId))
                }
            )
        }
        composable(
            route = Route.TripDetail.route,
            arguments = listOf(navArgument(Route.TripDetail.ARG_TRIP_ID) {
                type = NavType.StringType
            }),
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString(Route.TripDetail.ARG_TRIP_ID)
                ?: return@composable
            TripDetailPlaceholder(
                tripId = tripId,
                onBack = { navController.popBackStack() },
            )
        }
        composable(Route.Schedule.route) {
            SchedulePlaceholder()
        }
        composable(Route.Profile.route) {
            ProfilePlaceholder()
        }
        composable(Route.AddRecord.route) {
            AddRecordPlaceholder(
                onDismiss = { navController.popBackStack() }
            )
        }
    }
}
