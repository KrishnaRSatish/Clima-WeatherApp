package com.example.clima.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.clima.R
import com.example.clima.model.CardsModel
import com.example.clima.util.*
import com.example.clima.viewmodel.MainViewModel


class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    /* private lateinit var editCityName:EditText
     private lateinit var llDataView:LinearLayout
     private lateinit var tvTempValue:TextView
     private lateinit var tvCountyCode:TextView
     private lateinit var tvCityName:TextView
     private lateinit var tvHumidity:TextView
     private lateinit var tvSpeed:TextView
     private lateinit var tvLat:TextView
     private lateinit var tvLong:TextView
     private lateinit var ivWeatherIcon:ImageView*/
    private lateinit var rvCard: RecyclerView
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var tvSearch: EditText
    private lateinit var ivAdd: ImageView
    private lateinit var ivWeatherIcon:ImageView
    private var  errorMsgDisplayed = false

    private val cardsArrayList: ArrayList<CardsModel> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        rvCard = findViewById(R.id.rv_card_view)
        swipeLayout = findViewById(R.id.swipe_refresh)
        tvSearch = findViewById(R.id.edt_city_name)
        ivAdd = findViewById(R.id.iv_add)



        /*  editCityName  = findViewById<EditText>(R.id.edt_city_name)
           llDataView = findViewById(R.id.ll_data_view)
           tvTempValue = findViewById(R.id.tv_temp_value)
           tvCountyCode = findViewById(R.id.tv_country_code)
           tvCityName = findViewById(R.id.tv_city_name)
           tvHumidity = findViewById(R.id.tv_humidity)
           tvSpeed = findViewById(R.id.tv_speed)
           tvLat = findViewById(R.id.tv_lat)
           tvLong = findViewById(R.id.tv_long)
           ivWeatherIcon = findViewById(R.id.iv_weather_icon)*/


        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        // editCityName.setText(cityName)
        swipeLayout.setOnRefreshListener {
            //TODO -- handle swipe to refresh
            swipeLayout.isRefreshing = false

        }
        ivAdd.setOnClickListener {
            val city = tvSearch.text.toString()
            SET.putString("cityName", city)
            SET.apply()
            viewModel.refreshData(city)
            getLiveData()
            if(!errorMsgDisplayed) {
                errorMessage()
            }
            tvSearch.setText("")
            try {
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
                // TODO: handle exception
            }

        }


    }

    private fun getLiveData() {
        viewModel.weatherData.observe(this) { data ->
            data?.let {
                cardsArrayList.add(
                    CardsModel(
                        tempDisplayWithUnit(convertKelvinToCelsius(data.main.temp).toString()),
                        data.name, humidityDisplayWithUnit(data.main.humidity.toString()),
                        windSpeedDisplayWithUnit(data.wind.speed.toString()),
                        pressureDisplayWithUnit(data.main.pressure.toString()),
                        data.weather[0].description,
                        displayCountryCode(data.sys.country)

                    )
                )

                removeDuplicates(cardsArrayList)


                val linearLayoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                val cardAdapter = CardAdapter(this, cardsArrayList)
                rvCard.layoutManager = linearLayoutManager
                rvCard.adapter = cardAdapter


                //      llDataView.visibility = View.VISIBLE

                /* cardsArrayList.add(CardsModel(tempDisplayWithUnit(convertKelvinToCelsius(data.main.temp).toString()),data.name))
                 removeDuplicates(cardsArrayList)*/

                //
                //   cardsArrayList.add(CardsModel("25℃" , "Delhi"))*/


                //  tvTempValue.text =tempDisplayWithUnit(convertKelvinToCelsius(data.main.temp).toString())
                /*   tvCountyCode.text = data.sys.country
                   tvCityName.text = data.name
                   tvHumidity.text = data.main.humidity.toString()
                   tvSpeed.text = data.wind.speed.toString()
                   tvLat.text = data.coord.lat.toString()
                   tvLong.text = data.coord.lon.toString()*/


                /*Glide.with(this).load(Constants.APIVALUES.API_IMG_URL+ data.weather[0].icon+Constants.APIVALUES.API_IMG_SUFFIX)
                    .into(ivWeatherIcon)*/
            }



        }

    }
    private fun errorMessage(){
        viewModel.weatherError.observe(this){

            if (it.equals(true)){
                Toast.makeText(this,"Error in finding city",Toast.LENGTH_SHORT).show();
            }
            errorMsgDisplayed = true
        }
    }


}