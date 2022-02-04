package com.example.clima.model.util

import java.math.RoundingMode


fun convertKelvinToCelsius(temp: Double): Double {
    val num = temp - 273.15
    return num.toBigDecimal().setScale(1, RoundingMode.UP).toDouble();

}

fun tempDisplayWithUnit(temp:String):String{
    val sb = StringBuilder()
    sb.append(temp).append(Constants.UNITSTRINGS.Celsius )
    return sb.toString()
}