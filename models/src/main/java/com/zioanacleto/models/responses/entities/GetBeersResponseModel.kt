package com.zioanacleto.models.responses.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetBeersResponseModel(
    val beers: List<BeerModel>
): Parcelable

@Parcelize
data class BeerModel(
    val id: String,
    val name: String,
    val tagline: String,
    val description: String,
    val imageThumbnail: String
): Parcelable
