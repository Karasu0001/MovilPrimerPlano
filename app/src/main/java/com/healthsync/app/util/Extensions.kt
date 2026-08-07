package com.healthsync.app.util

import java.util.Locale

fun Double.formatVital(): String {
    return if (this == this.toLong().toDouble()) {
        String.format(Locale.US, "%d", this.toLong())
    } else {
        String.format(Locale.US, "%.1f", this)
    }
}

fun Double?.formatVitalOrDefault(): String {
    return this?.formatVital() ?: "--"
}
