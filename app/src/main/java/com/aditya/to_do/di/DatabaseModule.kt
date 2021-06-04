package com.aditya.to_do.di

import android.content.Context
import androidx.room.Room
import com.aditya.to_do.data.AppDatabase
import com.aditya.to_do.util.Utils.DATABASE_NAME
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
    fun provideDatabase(@ApplicationContext ctx: Context) = Room.databaseBuilder(
        ctx,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideNewsDao(database: AppDatabase) = database.getTaskDao()

}