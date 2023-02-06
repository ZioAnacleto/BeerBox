package com.zioanacleto.models.mappers

import com.zioanacleto.basecomponents.default
import com.zioanacleto.models.responses.dto.GetBeersResponseDTO
import com.zioanacleto.models.responses.entities.BeerModel
import com.zioanacleto.models.responses.entities.GetBeersResponseModel

class GetBeersDTOToModelMapper: DataMapper<GetBeersResponseDTO, GetBeersResponseModel> {
    override fun map(input: GetBeersResponseDTO): GetBeersResponseModel {
        return with(input){
            GetBeersResponseModel(
                beers = this.map { it.map() }
            )
        }
    }

    private fun GetBeersResponseDTO.GetBeersResponseItemDTO.map() = BeerModel(
        id = id.toString(),
        name = name.default(),
        tagline = tagline.default(),
        description = description.default(),
        imageThumbnail = imageUrl.default()
    )
}