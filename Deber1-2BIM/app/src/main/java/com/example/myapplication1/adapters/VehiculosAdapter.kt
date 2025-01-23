package com.example.myapplication1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.models.BVehiculo

class VehiculosAdapter(
    private val vehiculos: MutableList<BVehiculo>,
    private val onEdit: (BVehiculo) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<VehiculosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMarca: TextView = view.findViewById(R.id.tvMarcaVehiculo)
        val tvModelo: TextView = view.findViewById(R.id.tvModeloVehiculo)
        val btnEditar: Button = view.findViewById(R.id.btnEditarVehiculo)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminarVehiculo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehiculo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vehiculo = vehiculos[position]
        holder.tvMarca.text = vehiculo.marca
        holder.tvModelo.text = vehiculo.modelo
        holder.btnEditar.setOnClickListener { onEdit(vehiculo) }
        holder.btnEliminar.setOnClickListener { onDelete(vehiculo.id) }
    }

    override fun getItemCount(): Int = vehiculos.size
}

