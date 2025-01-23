package com.example.myapplication1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.models.BConcesionario

class ConcesionariosAdapter(
    private val concesionarios: MutableList<BConcesionario>,
    private val onClick: (BConcesionario) -> Unit,
    private val onEdit: (BConcesionario) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<ConcesionariosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreConcesionario)
        val tvDireccion: TextView = view.findViewById(R.id.tvDireccionConcesionario)
        val btnVer: Button = view.findViewById(R.id.btnVer)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_concesionario, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val concesionario = concesionarios[position]
        holder.tvNombre.text = concesionario.nombre
        holder.tvDireccion.text = concesionario.direccion
        holder.btnVer.setOnClickListener { onClick(concesionario) }
        holder.btnEditar.setOnClickListener { onEdit(concesionario) }
        holder.btnEliminar.setOnClickListener { onDelete(concesionario.id) }
    }

    override fun getItemCount(): Int = concesionarios.size
}
