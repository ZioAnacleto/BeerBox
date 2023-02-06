package com.zioanacleto.beerbox.presentation.interfaces

import com.zioanacleto.models.responses.entities.BeerModel

interface MainFlowContoller {
    fun onBeerClicked(beer: BeerModel)
}