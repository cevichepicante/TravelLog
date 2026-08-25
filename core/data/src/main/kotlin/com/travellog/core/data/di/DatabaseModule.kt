package com.travellog.core.data.di

import android.content.Context
import androidx.room.Room
import com.travellog.core.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "travel_log.db")
            .build()

    @Provides
    fun provideTripDao(db: AppDatabase) = db.tripDao()

    @Provides
    fun providePhotoDao(db: AppDatabase) = db.photoDao()

    @Provides
    fun providePlaceDao(db: AppDatabase) = db.placeDao()

    @Provides
    fun provideMemoDao(db: AppDatabase) = db.memoDao()

    @Provides
    fun provideBadgeDao(db: AppDatabase) = db.badgeDao()

    @Provides
    fun provideScheduleDao(db: AppDatabase) = db.scheduleDao()

    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()
}
