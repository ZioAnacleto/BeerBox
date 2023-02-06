package com.zioanacleto.basecomponents.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

/**
 *  This interface allows to use a class to provide a [CoroutineDispatcher]
 *  instead of using Dispatchers
 */
interface DispatcherProvider {
    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}