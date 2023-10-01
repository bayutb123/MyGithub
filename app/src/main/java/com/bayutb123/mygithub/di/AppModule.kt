package com.bayutb123.mygithub.di

import com.bayutb123.mygithub.data.repository.UsersRepositoryImpl
import com.bayutb123.mygithub.data.source.remote.ApiService
import com.bayutb123.mygithub.domain.repository.UserRepository
import com.bayutb123.mygithub.domain.usecase.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    @Provides
    fun provideUserRepository(apiService: ApiService) : UserRepository {
        return UsersRepositoryImpl(apiService)
    }

    @Provides
    fun providesUserUseCase(userRepository: UserRepository) = UserUseCase(userRepository)
}