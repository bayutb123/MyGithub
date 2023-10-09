package com.bayutb123.mygithub.di

import android.content.Context
import androidx.room.Room
import com.bayutb123.mygithub.data.repository.DatabaseRepositoryImpl
import com.bayutb123.mygithub.data.source.local.MyDatabase
import com.bayutb123.mygithub.data.source.local.UsersDao
import com.bayutb123.mygithub.domain.repository.DatabaseRepository
import com.bayutb123.mygithub.domain.usecase.DatabaseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context,
        MyDatabase::class.java,
        "my_github"
    ).build()

    @Singleton
    @Provides
    fun provideUsersDao(database: MyDatabase) = database.usersDao()

    @Singleton
    @Provides
    fun provideDatabaseRepository(dao: UsersDao) : DatabaseRepository {
        return DatabaseRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun provideDatabaseUseCase(databaseRepository: DatabaseRepository) = DatabaseUseCase(databaseRepository)

}
