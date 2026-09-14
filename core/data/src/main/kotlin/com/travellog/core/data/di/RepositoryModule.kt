package com.travellog.core.data.di

import com.travellog.core.data.repository.AuthRepository
import com.travellog.core.data.repository.BadgeRepository
import com.travellog.core.data.repository.FriendRepository
import com.travellog.core.data.repository.MapRepository
import com.travellog.core.data.repository.MemoRepository
import com.travellog.core.data.repository.PhotoRepository
import com.travellog.core.data.repository.PlaceRepository
import com.travellog.core.data.repository.ScheduleRepository
import com.travellog.core.data.repository.TripRepository
import com.travellog.core.data.repository.UserRepository
import com.travellog.core.data.repository.impl.AuthRepositoryImpl
import com.travellog.core.data.repository.impl.BadgeRepositoryImpl
import com.travellog.core.data.repository.impl.FriendRepositoryImpl
import com.travellog.core.data.repository.impl.MapRepositoryImpl
import com.travellog.core.data.repository.impl.MemoRepositoryImpl
import com.travellog.core.data.repository.impl.PhotoRepositoryImpl
import com.travellog.core.data.repository.impl.PlaceRepositoryImpl
import com.travellog.core.data.repository.impl.ScheduleRepositoryImpl
import com.travellog.core.data.repository.impl.TripRepositoryImpl
import com.travellog.core.data.repository.impl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
    @Binds @Singleton abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
    @Binds @Singleton abstract fun bindTripRepository(impl: TripRepositoryImpl): TripRepository
    @Binds @Singleton abstract fun bindPhotoRepository(impl: PhotoRepositoryImpl): PhotoRepository
    @Binds @Singleton abstract fun bindPlaceRepository(impl: PlaceRepositoryImpl): PlaceRepository
    @Binds @Singleton abstract fun bindMemoRepository(impl: MemoRepositoryImpl): MemoRepository
    @Binds @Singleton abstract fun bindMapRepository(impl: MapRepositoryImpl): MapRepository
    @Binds @Singleton abstract fun bindBadgeRepository(impl: BadgeRepositoryImpl): BadgeRepository
    @Binds @Singleton abstract fun bindScheduleRepository(impl: ScheduleRepositoryImpl): ScheduleRepository
    @Binds @Singleton abstract fun bindFriendRepository(impl: FriendRepositoryImpl): FriendRepository
}
