package com.example.clima.util

import com.example.clima.model.CardsModel
import java.math.RoundingMode


fun convertKelvinToCelsius(temp: Double): Double {
    val num = temp - 273.15
    return num.toBigDecimal().setScale(1, RoundingMode.UP).toDouble();

}

fun tempDisplayWithUnit(temp:String): String {
    val sb = StringBuilder()
    sb.append(temp).append(Constants.UNITSTRINGS.Celsius )
    return sb.toString()
}

fun removeDuplicates(list: ArrayList<CardsModel>) {
    for (i in 0 until list.size - 1) {
        var j = i + 1
        while (j < list.size) {
            if (list[i].equals(list[j])) {
                list.removeAt(j)
                j--
            }
            j++
        }
    }
}