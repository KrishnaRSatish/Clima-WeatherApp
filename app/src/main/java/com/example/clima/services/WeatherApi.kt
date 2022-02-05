package com.example.clima.services

import com.example.clima.model.WeatherModel
import com.example.clima.util.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    //https://api.openweathermap.org/data/2.5/weather?q=bangalore,indiak&APPID=4bb1418bffb03681a4b479f4d1952953



    @GET(Constants.APIVALUES.API_KEY)
    fun getData(
        @Query("q") cityName:String
    ): Single<WeatherModel>


}