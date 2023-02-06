package com.zioanacleto.network.retrofit

import retrofit2.Retrofit

interface ApiContainer {
    fun provideRetrofitClient(): Retrofit
}