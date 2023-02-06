package com.zioanacleto.beerbox.di

import com.zioanacleto.basecomponents.dispatcher.DispatcherProvider
import com.zioanacleto.basecomponents.dispatcher.DispatcherProviderImpl
import com.zioanacleto.models.mappers.DataMapper
import com.zioanacleto.models.mappers.GetBeersDTOToModelMapper
import com.zioanacleto.models.responses.dto.GetBeersResponseDTO
import com.zioanacleto.models.responses.entities.GetBeersResponseModel
import com.zioanacleto.network.repositories.GetBeersRepository
import com.zioanacleto.network.repositories.GetBeersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *  Class to manage dependency injection
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindGetBeersRepository(
        repositoryImpl: GetBeersRepositoryImpl
    ): GetBeersRepository
}

@Module
@InstallIn(SingletonComponent::class)
class AppModule{

    @Provides
    @Singleton
    fun provideBeersDataMapper(): DataMapper<GetBeersResponseDTO, GetBeersResponseModel> {
        return GetBeersDTOToModelMapper()
    }

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl()
    }
}