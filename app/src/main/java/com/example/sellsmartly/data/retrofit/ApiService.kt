package com.example.sellsmartly.data.retrofit

import com.example.sellsmartly.data.response.AddMenuResponse
import com.example.sellsmartly.data.response.LoginResponse
import com.example.sellsmartly.data.response.MenuResponse
import com.example.sellsmartly.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("store") store: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("menu")
    suspend fun getMenu(
        @Header("Authorization") token: String,
        @Query("name") name: String,
        @Query("price") price: Double
    ): MenuResponse

    @Multipart
    @POST("menu")
    suspend fun postMenu(
        @Header("Authorization") token: String,
        @Query("name") name: String,
        @Query("price") price: Double
    ): AddMenuResponse

}