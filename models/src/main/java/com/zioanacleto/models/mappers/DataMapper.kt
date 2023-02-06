package com.zioanacleto.models.mappers

interface DataMapper<Input, Output> {
    fun map(input: Input): Output
}