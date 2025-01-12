package org.calebetadeu.streamingtowatch.data

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<GymMateDatabase>
}