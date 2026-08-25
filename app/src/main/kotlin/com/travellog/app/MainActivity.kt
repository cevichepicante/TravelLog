package com.travellog.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.travellog.app.navigation.AppNavHost
import com.travellog.app.navigation.Route
import com.travellog.core.ui.component.TravelLogBottomBar
import com.travellog.core.ui.theme.TravelLogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelLogTheme {
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStack?.destination?.route

                // 탭 바를 숨길 화면 목록
                val hideBottomBar = currentRoute?.startsWith("trip_detail/") == true
                    || currentRoute == Route.AddRecord.route

                Scaffold(
                    bottomBar = {
                        if (!hideBottomBar) {
                            TravelLogBottomBar(
                                currentRoute = currentRoute,
                                onTabSelected = { tab ->
                                    navController.navigate(tab.route) {
                                        popUpTo(Route.Map.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                onAddClick = {
                                    navController.navigate(Route.AddRecord.route)
                                },
                            )
                        }
                    }
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
