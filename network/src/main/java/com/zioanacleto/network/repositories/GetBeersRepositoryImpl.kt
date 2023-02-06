package com.zioanacleto.network.repositories

import com.zioanacleto.models.responses.dto.GetBeersResponseDTO
import com.zioanacleto.network.callinterfaces.BeersCallInterface
import com.zioanacleto.network.resources.Resource
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class GetBeersRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
):  GetBeersRepository{

    override val request: BeersCallInterface
        get() = retrofit.create(BeersCallInterface::class.java)

    override suspend fun getBeers(pageNumber: Int): Resource<GetBeersResponseDTO> {
        return try{
            val response = request.getAllBeers(pageNumber)
            Timber.d("Response: $response")

            Resource.success(response)
        }
        catch (exception: Exception){
            Timber.d("Error: $exception")
            Resource.error(exception)
        }
    }

    override suspend fun searchBeers(query: String?, pageNumber: Int): Resource<GetBeersResponseDTO> {
        return try{
            val response = request.searchBeer(query, pageNumber)
            Timber.d("Response: $response")

            Resource.success(response)
        }
        catch (exception: Exception){
            Timber.d("Error: $exception")
            Resource.error(exception)
        }
    }
}