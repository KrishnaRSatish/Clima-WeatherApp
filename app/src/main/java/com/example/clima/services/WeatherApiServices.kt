package com.example.clima.services

import com.example.clima.model.WeatherModel
import com.example.clima.util.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApiServices {

    //private val BASE_URL = "https://api.openweathermap.org"
    private val api = Retrofit.Builder()
        .baseUrl(Constants.APIVALUES.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    fun getDataServices(cityName:String):Single<WeatherModel>{
        return api.getData(cityName)
    }
}