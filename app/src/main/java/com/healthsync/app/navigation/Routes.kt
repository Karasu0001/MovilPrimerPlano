package com.healthsync.app.navigation

sealed class Routes(val route: String) {
    object Welcome : Routes("welcome")
    object Login : Routes("login")
    object Register : Routes("register")
    object ForgotPassword : Routes("forgot-password")
    object Dashboard : Routes("dashboard")
    object CodePairingFlow : Routes("pairing/code/flow")
    object CodePairingSearch : Routes("pairing/code/search")
    object CodePairingConfirm : Routes("pairing/code/confirm")
    object CodePairingSyncing : Routes("pairing/code/syncing")
    object CodePairingSuccess : Routes("pairing/code/success")
    object CodePairingCancelled : Routes("pairing/code/cancelled")
    object QrPairingFlow : Routes("pairing/qr/flow")
    object PairingMethod : Routes("pairing/method")
    object VitalTrackFlow : Routes("vitaltrack/flow")
    object Activity : Routes("activity")
    object Profile : Routes("profile")
}
