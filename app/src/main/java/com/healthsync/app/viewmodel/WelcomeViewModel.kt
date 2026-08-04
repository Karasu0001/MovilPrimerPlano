package com.healthsync.app.viewmodel

import androidx.lifecycle.ViewModel

class WelcomeViewModel : ViewModel() {
    // No se necesita estado complejo, es una pantalla puramente informativa/CTA
    fun onHelpClick() {
        // TODO: navegar a /help o abrir modal de ayuda
    }
}
