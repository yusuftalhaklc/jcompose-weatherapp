package com.yusuftalhaklc.weatherapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuftalhaklc.weatherapp.Model.Weather
import com.yusuftalhaklc.weatherapp.service.WeatherAPI
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var weatherListResponse:List<Weather> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")
    fun getWeather() {
        viewModelScope.launch {
            val apiService = WeatherAPI.getInstance()
            try {
                val weatherList = apiService.getWeather()
                weatherListResponse = weatherList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}