package org.calebetadeu.streamingtowatch.data

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object GymMateDatabaseConstructor: RoomDatabaseConstructor<GymMateDatabase> {
    override fun initialize(): GymMateDatabase
}