package com.travellog.core.network

interface TokenProvider {
    fun accessToken(): String?
}
