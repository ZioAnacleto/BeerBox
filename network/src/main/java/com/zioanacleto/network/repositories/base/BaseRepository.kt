package com.zioanacleto.network.repositories.base

interface BaseRepository<out T> {
    val request: T
}