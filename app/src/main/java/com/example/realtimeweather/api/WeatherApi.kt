package com.example.realtimeweather.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/current.json")
    suspend fun getWeather(
        @Query("key") apikey : String,//when we call get weather we''ll get the response of weather model else an error
        @Query("q") city : String
    ): Response<WeatherModel>  //when the weather api is called it returns a response of retrofit

}