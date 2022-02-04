package com.example.clima.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.clima.R
import com.example.clima.model.util.Constants
import com.example.clima.model.util.convertKelvinToCelsius
import com.example.clima.model.util.tempDisplayWithUnit
import com.example.clima.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    private lateinit var GET:SharedPreferences
    private lateinit var SET:SharedPreferences.Editor
    private lateinit var editCityName:EditText
    private lateinit var llDataView:LinearLayout
    private lateinit var tvTempValue:TextView
    private lateinit var tvCountyCode:TextView
    private lateinit var tvCityName:TextView
    private lateinit var tvHumidity:TextView
    private lateinit var tvSpeed:TextView
    private lateinit var tvLat:TextView
    private lateinit var tvLong:TextView
    private lateinit var ivWeatherIcon:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editCityName  = findViewById<EditText>(R.id.edt_city_name)
        llDataView = findViewById(R.id.ll_data_view)
        tvTempValue = findViewById(R.id.tv_temp_value)
        tvCountyCode = findViewById(R.id.tv_country_code)
        tvCityName = findViewById(R.id.tv_city_name)
        tvHumidity = findViewById(R.id.tv_humidity)
        tvSpeed = findViewById(R.id.tv_speed)
        tvLat = findViewById(R.id.tv_lat)
        tvLong = findViewById(R.id.tv_long)
        ivWeatherIcon = findViewById(R.id.iv_weather_icon)


        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        var cityName = GET.getString("cityName" , "Delhi")
        editCityName.setText(cityName)

        viewModel.refreshData()


        getLiveData()



    }

    private fun getLiveData() {
        viewModel.weatherData.observe(this) { data ->
            data?.let {
                llDataView.visibility = View.VISIBLE

                tvTempValue.text =tempDisplayWithUnit(convertKelvinToCelsius(data.main.temp).toString())
                tvCountyCode.text = data.sys.country
                tvCityName.text = data.name
                tvHumidity.text = data.main.humidity.toString()
                tvSpeed.text = data.wind.speed.toString()
                tvLat.text = data.coord.lat.toString()
                tvLong.text = data.coord.lon.toString()


                Glide.with(this).load(Constants.APIVALUES.API_IMG_URL+ data.weather[0].icon+Constants.APIVALUES.API_IMG_SUFFIX)
                    .into(ivWeatherIcon)
            }

        }

    }
}