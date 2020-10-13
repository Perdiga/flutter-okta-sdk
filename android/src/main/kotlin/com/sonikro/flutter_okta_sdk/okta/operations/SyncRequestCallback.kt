package com.sonikro.flutter_okta_sdk.okta.operations

import com.okta.oidc.RequestCallback
import java.lang.Exception
import java.util.concurrent.CountDownLatch

class SyncRequestCallback<T, E : Exception>: RequestCallback<T, E> {

    private val latch = CountDownLatch(1)
    var mResponse: T? = null
    var mError: E? = null

    fun getResponse(): T {
        latch.await()
        if(mError != null){
            throw mError as E
        }
        return mResponse!!
    }

    override fun onSuccess(result: T) {
        latch.countDown()
        mResponse = result
    }

    override fun onError(error: String?, exception: E?) {
        latch.countDown()
        mError = exception
    }
}