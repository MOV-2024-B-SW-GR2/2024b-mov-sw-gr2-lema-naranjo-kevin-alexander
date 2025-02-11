package com.example.proyecto

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val vehicleImage: ImageView = itemView.findViewById(R.id.vehicle_image)
    val vehicleName: TextView = itemView.findViewById(R.id.vehicle_name)
    val vehicleYear: TextView = itemView.findViewById(R.id.vehicle_year)
}