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
    private val concesionarios: List<BConcesionario>,
    private val onClick: (BConcesionario) -> Unit,
    private val onEdit: (BConcesionario) -> Unit,
    private val onDelete: (Int) -> Unit,
    private val onVerUbicacion: (BConcesionario) -> Unit // Nuevo callback para el botón "Ver Ubicación"
) : RecyclerView.Adapter<ConcesionariosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreConcesionario)
        val tvDireccion: TextView = view.findViewById(R.id.tvDireccionConcesionario)
        val btnVer: Button = view.findViewById(R.id.btnVer)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
        val btnVerUbicacion: Button = view.findViewById(R.id.btnVerUbicacion) // Nuevo botón
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

        // Lógica para el botón "Ver"
        holder.btnVer.setOnClickListener { onClick(concesionario) }

        // Lógica para el botón "Editar"
        holder.btnEditar.setOnClickListener { onEdit(concesionario) }

        // Lógica para el botón "Eliminar"
        holder.btnEliminar.setOnClickListener { onDelete(concesionario.id) }

        // Lógica para el nuevo botón "Ver Ubicación"
        holder.btnVerUbicacion.setOnClickListener { onVerUbicacion(concesionario) }
    }

    override fun getItemCount(): Int = concesionarios.size
}