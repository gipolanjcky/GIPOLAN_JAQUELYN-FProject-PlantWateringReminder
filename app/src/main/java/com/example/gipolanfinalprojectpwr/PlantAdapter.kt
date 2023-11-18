package com.example.gipolanfinalprojectpwr

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PlantAdapter(context: Context, plants: List<Plant>) :
    ArrayAdapter<Plant>(context, 0, plants) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                android.R.layout.simple_list_item_2,
                parent,
                false
            )
        }

        val currentPlant = getItem(position)

        val plantNameTextView = listItemView?.findViewById<TextView>(android.R.id.text1)
        val wateringIntervalTextView = listItemView?.findViewById<TextView>(android.R.id.text2)

        plantNameTextView?.text = currentPlant?.name.toString()
        "Watering Interval: ${currentPlant?.wateringInterval} days".also { wateringIntervalTextView?.text = it }

        return listItemView!!
    }
}
