package com.healthsync.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.healthsync.app.data.AuthSessionStore
import com.healthsync.app.data.PatientSessionStore
import com.healthsync.app.ui.screens.activity.ActivityScreen
import com.healthsync.app.ui.screens.auth.ForgotPasswordScreen
import com.healthsync.app.ui.screens.auth.LoginScreen
import com.healthsync.app.ui.screens.auth.RegisterScreen
import com.healthsync.app.ui.screens.dashboard.DashboardScreen
import com.healthsync.app.ui.screens.patient.PatientDetailScreen
import com.healthsync.app.ui.screens.patient.CreatePatientScreen
import com.healthsync.app.ui.screens.alerts.AlertsScreen
import com.healthsync.app.ui.screens.onboarding.WelcomeScreen
import com.healthsync.app.ui.screens.pairing.PairingMethodScreen
import com.healthsync.app.ui.screens.pairing.code.CodePairingFlowScreen
import com.healthsync.app.ui.screens.pairing.qr.QrPairingFlowScreen
import com.healthsync.app.ui.screens.profile.ProfileScreen
import com.healthsync.app.ui.screens.wearable.vitaltrack.VitalTrackScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Welcome.route
    ) {
        composable(Routes.Welcome.route) {
            var sessionChecked by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                val token = AuthSessionStore.getToken()
                val pairingCode = PatientSessionStore.getPairingCode()
                when {
                    !token.isNullOrEmpty() -> {
                        navController.navigate(Routes.Dashboard.route) {
                            popUpTo(Routes.Welcome.route) { inclusive = true }
                        }
                    }
                    !pairingCode.isNullOrEmpty() -> {
                        navController.navigate(Routes.VitalTrackFlow.route) {
                            popUpTo(Routes.Welcome.route) { inclusive = true }
                        }
                    }
                    else -> sessionChecked = true
                }
            }
            if (sessionChecked) {
                WelcomeScreen(
                    onStart = { navController.navigate(Routes.Register.route) },
                    onLoginClick = { navController.navigate(Routes.Login.route) },
                    onPairingMethod = { navController.navigate(Routes.PairingMethod.route) }
                )
            }
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
                onNavigateToPairing = { navController.navigate(Routes.PairingMethod.route) },
                onNavigateToActivity = { navController.navigate(Routes.Activity.route) },
                onNavigateToProfile = { navController.navigate(Routes.Profile.route) },
                onNavigateToVitalTrack = { navController.navigate(Routes.VitalTrackFlow.route) },
                onNavigateToPatientDetail = { patientId ->
                    navController.navigate("detail/$patientId")
                },
                onNavigateToCreatePatient = { navController.navigate(Routes.CreatePatient.route) },
                onNavigateToAlerts = { navController.navigate(Routes.Alerts.route) }
            )
        }

        composable("detail/{patientId}") { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId") ?: ""
            PatientDetailScreen(
                patientId = patientId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.CreatePatient.route) {
            CreatePatientScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.Alerts.route) {
            AlertsScreen(
                onBack = { navController.popBackStack() },
                onNavigateToPatientDetail = { patientId -> navController.navigate("detail/$patientId") }
            )
        }

        composable(Routes.PairingMethod.route) {
            PairingMethodScreen(
                onSelectCode = { navController.navigate(Routes.CodePairingFlow.route) },
                onSelectQr = { navController.navigate(Routes.QrPairingFlow.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.CodePairingFlow.route) {
            CodePairingFlowScreen(
                onGoToDashboard = { navController.navigate(Routes.VitalTrackFlow.route) {
                    popUpTo(Routes.Welcome.route) { inclusive = true }
                }},
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.QrPairingFlow.route) {
            QrPairingFlowScreen(
                onGoToDashboard = { navController.navigate(Routes.VitalTrackFlow.route) {
                    popUpTo(Routes.Welcome.route) { inclusive = true }
                }},
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.VitalTrackFlow.route) {
            val activity = androidx.compose.ui.platform.LocalContext.current as? android.app.Activity
            VitalTrackScreen(
                onBack = {
                    if (navController.previousBackStackEntry == null) {
                        // Se llegó acá sin nada atrás (flujo de paciente sin cuenta):
                        // no hay que borrar la sesión, solo salir como haría el back
                        // del sistema. La próxima vez que se abra la app, Welcome ve
                        // el código guardado y vuelve a traer acá con datos en vivo.
                        activity?.finish()
                    } else {
                        // Se llegó desde el Dashboard del cuidador: volver normal.
                        navController.popBackStack()
                    }
                }
            )
        }

        composable(Routes.Activity.route) {
            ActivityScreen(
                onBack = { navController.popBackStack() },
                onNavigateToDashboard = { navController.navigate(Routes.Dashboard.route) },
                onNavigateToPairing = { navController.navigate(Routes.PairingMethod.route) },
                onNavigateToProfile = { navController.navigate(Routes.Profile.route) }
            )
        }

        composable(Routes.Profile.route) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Routes.Welcome.route) {
                        popUpTo(0)
                    }
                },
                onNavigateToDashboard = { navController.navigate(Routes.Dashboard.route) },
                onNavigateToAlerts = { navController.navigate(Routes.Alerts.route) }
            )
        }
    }
}