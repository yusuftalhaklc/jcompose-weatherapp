package com.yusuftalhaklc.weatherapp.service

import com.yusuftalhaklc.weatherapp.Model.Weather
import com.yusuftalhaklc.weatherapp.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WeatherAPI {

    @GET("v1/forecast.json?key=608d0d1d968244f389c121315220802&q=London&days=1&aqi=no&alerts=no")
    suspend fun getWeather():List<Weather>

    companion object {
        var weatherApi: WeatherAPI? = null
        fun getInstance() : WeatherAPI {
            if (weatherApi == null) {
                weatherApi = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherAPI::class.java)
            }
            return weatherApi!!
        }
    }

}