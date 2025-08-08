package com.kioskkmm.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform