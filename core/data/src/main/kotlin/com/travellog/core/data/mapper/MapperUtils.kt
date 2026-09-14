package com.travellog.core.data.mapper

internal fun String?.hexToColorLong(): Long {
    if (this.isNullOrBlank()) return 0L
    return try {
        trimStart('#').toLong(16)
    } catch (e: NumberFormatException) {
        0L
    }
}
