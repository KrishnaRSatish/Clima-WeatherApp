package com.example.clima.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clima.R
import com.example.clima.model.CardsModel

class CardAdapter(
 private val context: Context,
 private val cardsArrayList: ArrayList<CardsModel> = arrayListOf()

) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {


 // create new views
 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
  // inflates the card_view_design view
  // that is used to hold list item
  val view = LayoutInflater.from(parent.context)
   .inflate(R.layout.layout_temp_cards, parent, false)

  return ViewHolder(view)
 }

 // binds the list items to a view
 override fun onBindViewHolder(holder: ViewHolder, position: Int) {

  val model: CardsModel = cardsArrayList[position]
  holder.itemView.findViewById<TextView>(R.id.tv_temp_value).text = model.tempValue
  holder.itemView.findViewById<TextView>(R.id.tv_city_name).text = model.cityName
  holder.itemView.findViewById<TextView>(R.id.tv_humidity_value).text = model.humidity
  holder.itemView.findViewById<TextView>(R.id.tv_wind).text = model.wind
  holder.itemView.findViewById<TextView>(R.id.tv_pressure_value).text = model.pressure
  holder.itemView.findViewById<TextView>(R.id.tv_description).text = model.description
 }

 // return the number of the items in the list
 override fun getItemCount(): Int {
  return cardsArrayList.size
 }

 // Holds the views for adding it to image and text
 class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
 }
}