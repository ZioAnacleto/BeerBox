package com.zioanacleto.network.repositories

import com.zioanacleto.models.responses.dto.GetBeersResponseDTO
import com.zioanacleto.network.callinterfaces.BeersCallInterface
import com.zioanacleto.network.repositories.base.BaseRepository
import com.zioanacleto.network.resources.Resource

interface GetBeersRepository: BaseRepository<BeersCallInterface> {
    suspend fun getBeers(pageNumber: Int): Resource<GetBeersResponseDTO>
    suspend fun searchBeers(query: String?, pageNumber: Int): Resource<GetBeersResponseDTO>
}