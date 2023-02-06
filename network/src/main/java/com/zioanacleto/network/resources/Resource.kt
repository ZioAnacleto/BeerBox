package com.zioanacleto.network.resources

import java.lang.Exception

class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val exception: Exception? = null
): BaseResource {

    override fun isSuccess(): Boolean = status == Status.SUCCESS
    override fun isError(): Boolean = status == Status.ERROR
    override fun isLoading(): Boolean = status == Status.LOADING

    companion object{
        fun <T> success(data: T) =
            Resource(status = Status.SUCCESS, data = data)
        fun <T> error(exception: Exception, data: T? = null) =
            Resource(status = Status.ERROR, data = data, exception = exception)
        fun <T> loading(data: T? = null) =
            Resource(status = Status.LOADING, data = data)
    }
}

enum class Status{
    SUCCESS,
    ERROR,
    LOADING
}