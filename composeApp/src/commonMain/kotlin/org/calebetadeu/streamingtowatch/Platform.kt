package org.calebetadeu.streamingtowatch

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform