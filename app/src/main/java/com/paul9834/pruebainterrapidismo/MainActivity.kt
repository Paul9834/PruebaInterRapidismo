package com.paul9834.pruebainterrapidismo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paul9834.pruebainterrapidismo.presentation.ui.*
import com.paul9834.pruebainterrapidismo.presentation.ui.theme.PruebaInterRapidismoTheme
import com.paul9834.pruebainterrapidismo.presentation.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaInterRapidismoTheme {

                val navController = rememberNavController()
                val vm: MainViewModel = viewModel(factory = MainViewModel.Factory)

                vm.initAppFlow(localVersion = "0.1")

                Surface {
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(vm,
                                onGoToTables = { navController.navigate("tables") },
                                onGoToLocations = {
                                    vm.fetchLocations()
                                    navController.navigate("locations")
                                }
                            )
                        }
                        composable("tables") { TablesScreen(vm) }
                        composable("locations") { LocationsScreen(vm) }
                    }
                }
            }
        }
    }
}