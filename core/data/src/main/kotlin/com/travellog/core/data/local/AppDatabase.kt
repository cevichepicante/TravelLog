package com.travellog.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.travellog.core.data.local.converter.Converters
import com.travellog.core.data.local.dao.*
import com.travellog.core.data.local.entity.*

@Database(
    entities = [
        TripEntity::class,
        PhotoEntity::class,
        PlaceEntity::class,
        MemoEntity::class,
        BadgeEntity::class,
        ScheduleEntity::class,
        UserEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun photoDao(): PhotoDao
    abstract fun placeDao(): PlaceDao
    abstract fun memoDao(): MemoDao
    abstract fun badgeDao(): BadgeDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun userDao(): UserDao
}
