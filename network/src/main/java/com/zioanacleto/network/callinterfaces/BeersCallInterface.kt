package com.zioanacleto.network.callinterfaces

import com.zioanacleto.models.responses.dto.GetBeersResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersCallInterface {

    @GET("beers")
    suspend fun getAllBeers(@Query("page") pageNumber: Int): GetBeersResponseDTO

    @GET("beers")
    suspend fun searchBeer(
        @Query("beer_name") name: String?,
        @Query("page") pageNumber: Int
    ): GetBeersResponseDTO
}