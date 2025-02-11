package com.example.proyecto

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class VehicleAdapter(
    private var vehicles: List<Vehicle>, // Lista de vehículos
    private val onItemClick: (Vehicle) -> Unit // Función que se llama al hacer clic en un vehículo
) : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    // Crear y devolver el ViewHolder para cada ítem
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vehicle, parent, false)
        return VehicleViewHolder(view)
    }

    // Vincular los datos de cada vehículo con la vista del ViewHolder
    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle = vehicles[position]
        holder.bind(vehicle)
        holder.itemView.setOnClickListener { onItemClick(vehicle) } // Maneja el clic en cada ítem
    }

    // Devuelve el número total de elementos en la lista
    override fun getItemCount(): Int = vehicles.size

    // Actualiza la lista de vehículos y notifica a la vista que se debe actualizar
    fun updateVehicles(newVehicles: List<Vehicle>) {
        vehicles = newVehicles
        notifyDataSetChanged()
    }

    // ViewHolder que maneja cada ítem individual
    inner class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val vehicleImage: ImageView = itemView.findViewById(R.id.vehicle_image)
        private val vehicleName: TextView = itemView.findViewById(R.id.vehicle_name)
        private val vehicleYear: TextView = itemView.findViewById(R.id.vehicle_year)

        // Asocia los datos del vehículo con las vistas del ViewHolder
        fun bind(vehicle: Vehicle) {
            // Asigna el nombre y año del vehículo a los TextViews
            vehicleName.text = "${vehicle.marca} ${vehicle.modelo}"
            vehicleYear.text = "Año: ${vehicle.anio}"

            // Cargar la imagen del vehículo
            val file = File(itemView.context.filesDir, vehicle.imagePath)
            if (file.exists()) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath) // Decodifica la imagen
                vehicleImage.setImageBitmap(bitmap)
            } else {
                vehicleImage.setImageResource(R.drawable.carro1) // Imagen por defecto si no se encuentra
            }
        }
    }
}
