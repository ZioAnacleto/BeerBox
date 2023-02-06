package com.zioanacleto.basecomponents

import androidx.lifecycle.ViewModel
import com.zioanacleto.basecomponents.dispatcher.DispatcherProvider
import com.zioanacleto.basecomponents.dispatcher.DispatcherProviderImpl
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 *  Base class to implement a clean MVVM pattern
 */
abstract class BaseViewModel(
    private val dispatcherProvider: DispatcherProvider
): ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + dispatcherProvider.main()

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    //extension function to make code more readable, to use I/O dispatcher
    fun CoroutineScope.launchSupervisored(
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return launch(dispatcherProvider.io()){
            supervisorScope(block)
        }
    }

    //extension function to post results on main thread
    suspend fun postSupervisored(
        block: () -> Unit
    ){
        return withContext(dispatcherProvider.main()){
            block.invoke()
        }
    }

    fun cancelChildren(){
        coroutineContext.cancelChildren()
    }
}