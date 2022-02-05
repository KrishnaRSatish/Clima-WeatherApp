package com.example.clima.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clima.model.CardsModel
import com.example.clima.model.WeatherModel
import com.example.clima.services.WeatherApiServices
import com.example.clima.util.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val weatherAPIService = WeatherApiServices()
    private val disposable = CompositeDisposable()

    val weatherData = MutableLiveData<WeatherModel>()
    val weatherError = MutableLiveData<Boolean>()
    val weatherLoad = MutableLiveData<Boolean>()
    val cardList = MutableLiveData<CardsModel>()

    fun refreshData(cityName:String) {
        getDataFromAPI(cityName)
        //getDataFromLocal()
    }

    private fun getDataFromAPI(cityName:String ) {
        weatherLoad.value = true
        disposable.add(
            weatherAPIService.getDataServices(cityName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {
                    override fun onSuccess(t: WeatherModel) {
                        weatherData.value = t
                        weatherError.value = false
                        weatherLoad.value = false
                        cardList.value = CardsModel(t.main.temp.toString() ,t.name ,
                            t.main.humidity.toString() , t.wind.speed.toString(),
                            t.main.pressure.toString() , t.weather[0].description,
                            t.sys.country
                        )
                    }

                    override fun onError(e: Throwable) {
                        weatherError.value = true
                        weatherLoad.value = false
                    }

                })
        )
    }

}