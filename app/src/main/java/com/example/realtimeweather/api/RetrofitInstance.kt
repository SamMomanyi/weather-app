package com.example.realtimeweather.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val baseUrl = "https://api.weatherapi.com";

    private fun getInstance() : Retrofit { //we pass url convert it,build it then create weatherApi class
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val weatherApi : WeatherApi = getInstance().create(WeatherApi::class.java)
  }