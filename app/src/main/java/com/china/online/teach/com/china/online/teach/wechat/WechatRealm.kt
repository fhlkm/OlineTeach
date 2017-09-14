package com.china.online.teach.com.china.online.teach.wechat

import io.reactivex.Observable
import io.reactivex.ObservableSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.fastjson.FastJsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by hanlu.feng on 9/14/2017.
 */
class WechatRealm private constructor(){
    private var TAl = WechatRealm::class.java
    private val base_url ="https://api.weixin.qq.com/"
    private val client: OkHttpClient
    private val retrofit: Retrofit
    private val mApiService :ApiEndPoint
    init{
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY

        this.client = OkHttpClient.Builder().addInterceptor(logging)
                .connectTimeout(25, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .build()
        retrofit = Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        mApiService = retrofit!!.create(ApiEndPoint::class.java)
    }



    companion object {
        val instance by lazy { WechatRealm() }
    }

    fun getAccessToken(appid:String, secrect:String,code:String, grand_type:String):Observable<GetAccessTokenResponse>{
        return  mApiService.getWechatAccessToken(appid,secrect,code,grand_type).compose ( WechatResultToResponseWithErrorHandlingTransformer()).
                flatMap {
                    response ->
                    Observable.just(response.body() as  GetAccessTokenResponse)
                }

    }

}