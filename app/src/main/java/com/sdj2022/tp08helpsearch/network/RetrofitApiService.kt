package com.sdj2022.tp08helpsearch.network

import com.google.cloud.audit.AuthorizationInfo
import com.sdj2022.tp08helpsearch.model.KakaoSearchPlaceResponse
import com.sdj2022.tp08helpsearch.model.NidUserInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApiService {

    // 네아로 사용자정보 API
    @GET("/v1/nid/me")
    fun getNidUserInfo(@Header("Authorization") authorization:String):Call<NidUserInfoResponse>

    // 카카오 키워드 장소검색 API
    @Headers("Authorization: KakaoAK 4974a4e738818590fc32b08119063074") //파라미터로 받으면 Header 위에 쓰면 Headers
    @GET("/v2/local/search/keyword.json")
    fun searchPlaces(@Query("query") query:String, @Query("x") longitude:String, @Query("y") latitude:String):Call<KakaoSearchPlaceResponse>
}