package com.healthsync.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.healthsync.app.ui.screens.auth.ForgotPasswordScreen
import com.healthsync.app.ui.screens.auth.LoginScreen
import com.healthsync.app.ui.screens.auth.RegisterScreen
import com.healthsync.app.ui.screens.dashboard.DashboardScreen
import com.healthsync.app.ui.screens.onboarding.WelcomeScreen
import com.healthsync.app.ui.screens.pairing.code.CodePairingFlowScreen
import com.healthsync.app.ui.screens.pairing.qr.QrPairingFlowScreen
import com.healthsync.app.ui.screens.wearable.vitaltrack.VitalTrackScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Welcome.route
    ) {
        composable(Routes.Welcome.route) {
            WelcomeScreen(
                onStart = { navController.navigate(Routes.Register.route) },
                onLoginClick = { navController.navigate(Routes.Login.route) }
            )
        }

        composable(Routes.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Routes.Dashboard.route) },
                onForgotPassword = { navController.navigate(Routes.ForgotPassword.route) },
                onRegister = { navController.navigate(Routes.Register.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Routes.Dashboard.route) },
                onLogin = { navController.popBackStack() }
            )
        }

        composable(Routes.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.Dashboard.route) {
            DashboardScreen(
                onNavigateToPairing = { navController.navigate(Routes.QrPairingFlow.route) },
                onNavigateToActivity = { },
                onNavigateToProfile = { },
                onNavigateToVitalTrack = { navController.navigate(Routes.VitalTrackFlow.route) }
            )
        }

        composable(Routes.CodePairingFlow.route) {
            CodePairingFlowScreen(
                onGoToDashboard = { navController.navigate(Routes.Dashboard.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.QrPairingFlow.route) {
            QrPairingFlowScreen(
                onGoToDashboard = { navController.navigate(Routes.Dashboard.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.VitalTrackFlow.route) {
            VitalTrackScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
