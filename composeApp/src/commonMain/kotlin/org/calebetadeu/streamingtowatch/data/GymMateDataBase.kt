package org.calebetadeu.streamingtowatch.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ExerciseEntity::class],
    version = 1
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(GymMateDatabaseConstructor::class)
abstract class GymMateDatabase: RoomDatabase() {
    abstract val exerciseDao: ExerciseDao

    companion object {
        const val DB_NAME = "gymmate.db"
    }
}