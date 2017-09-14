package com.china.online.teach.com.china.online.teach.wechat

import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.*

/**
 * Created by hanlu.feng on 9/14/2017.
 */

interface  ApiEndPoint{
    @Headers("Accept: application/json;charset=utf-8", "Content-Type: application/json;charset=utf-8")
    @GET("/sns/oauth2/access_token")
    fun getWechatAccessToken(
            @Path("appid")  appid:String,
            @Path("secret") secret: String,
            @Path("code") code: String,
            @Path("grand_type") grand_type: String): Observable<Result<GetAccessTokenResponse>>


}