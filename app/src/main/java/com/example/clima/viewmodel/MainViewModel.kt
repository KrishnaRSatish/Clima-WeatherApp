package com.example.clima.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clima.model.WeatherModel
import com.example.clima.services.WeatherApiServices
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

    fun refreshData() {
        getDataFromAPI()
        //getDataFromLocal()
    }

    private fun getDataFromAPI() {
        weatherLoad.value = true
        disposable.add(
            weatherAPIService.getDataServices()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {
                    override fun onSuccess(t: WeatherModel) {
                        weatherData.value = t
                        weatherError.value = false
                        weatherLoad.value = false
                    }

                    override fun onError(e: Throwable) {
                        weatherError.value = true
                        weatherLoad.value = false
                    }

                })
        )
    }

}