package com.example.realtimeweather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realtimeweather.api.Constant
import com.example.realtimeweather.api.NetworkResponse
import com.example.realtimeweather.api.RetrofitInstance
import com.example.realtimeweather.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult //we can then show the result on the UI


    fun getData(city  : String){
        //we call that method inside a viewModelScope for additional possibilities and it is also a coroutine
        //we also need to add internet permission which is added from manifests <uses-permission>
        //before we get the response from the coroutine we show loading
        _weatherResult.value  = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(
                    Constant.apiKey,
                    city
                ) //to call get weather we require two things api key and city
                //whenever we get the response we introduce the concept of network respone
                if (response.isSuccessful) {
                    //Log.i("REspones: ",response.body().toString())
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    //Log.i("Error : ", response.message())
                    _weatherResult.value = NetworkResponse.Error("Failed to load dTA")
                }
            }
            catch(e : Exception){
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }
}