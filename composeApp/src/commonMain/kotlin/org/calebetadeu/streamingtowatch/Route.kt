package org.calebetadeu.streamingtowatch

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object GymMate : Route

}