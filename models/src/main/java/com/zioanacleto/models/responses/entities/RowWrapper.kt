package com.zioanacleto.models.responses.entities

data class RowWrapper<T>(val viewType: Int, var data: T)
