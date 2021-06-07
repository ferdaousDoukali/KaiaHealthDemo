package com.kaiahealth.demo.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeSuspendCall(suspendCall: suspend () -> T): SuspendCallResult<T> {
    return try {
        withContext(Dispatchers.Main){
            SuspendCallResult.Success(suspendCall.invoke())
        }
    } catch (throwable: Throwable) {
        withContext(Dispatchers.Main) {
            SuspendCallResult.Error(throwable)
        }
    }
}

sealed class SuspendCallResult<out T> {
    data class Success<out T>(val value: T) : SuspendCallResult<T>()
    data class Error(val throwable: Throwable) : SuspendCallResult<Nothing>()
}