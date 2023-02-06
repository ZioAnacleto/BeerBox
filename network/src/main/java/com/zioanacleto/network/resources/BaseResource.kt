package com.zioanacleto.network.resources

interface BaseResource {
    fun isSuccess(): Boolean
    fun isError(): Boolean
    fun isLoading(): Boolean
}