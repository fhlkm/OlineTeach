package com.china.online.teach.com.china.online.teach.wechat

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result
import io.reactivex.functions.Function
/**
 * Created by hanlu.feng on 9/14/2017.
 */
class WechatResultToResponseWithErrorHandlingTransformer : ObservableTransformer<Result<*>, Response<*>> {

    private val TAG = WechatResultToResponseWithErrorHandlingTransformer::class.java.name
    override fun apply(integerObservable: Observable<Result<*>>): Observable<Response<*>> {
        return integerObservable.flatMap(Function<Result<*>, Observable<Response<*>>> { result ->
            if (result == null || result.response() == null) {
                Log.e(TAG,"checkResultSuccess...result is empty")
                if (result != null && result.error() != null) {
                    Log.e(TAG,result.error()!!.toString())
                }
                return@Function Observable.error<Response<*>>(WebSdkErrorException(ErrorNotification.unknown_error))
            }
            if (result.isError) {
                Log.e(TAG,"checkResultSuccess..." + result.isError)
                return@Function Observable.error<Response<*>>(WebSdkErrorException(result.error()!!.message))
            }
            if (result.response()!!.isSuccessful) {
                Log.d(TAG,"checkResultSuccess..." + result.response()!!.isSuccessful)
                Log.d(TAG,"checkResultSuccess...code..." + result.response()!!.code())
                Observable.just(result.response()!!)
            } else {
                return@Function Observable.error<Response<*>>(WebSdkErrorException( result.response()!!.errorBody()!!.string()))
            }
        })
    }
}