package com.example.proyecto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RepuestoAdapter(
    private val repuestos: List<Repuesto>,
    private val onItemClick: (Repuesto) -> Unit
) : RecyclerView.Adapter<RepuestoAdapter.RepuestoViewHolder>() {

    class RepuestoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.repuesto_image)
        private val nameView: TextView = itemView.findViewById(R.id.repuesto_name)
        private val priceView: TextView = itemView.findViewById(R.id.repuesto_price)
        private val availabilityView: TextView = itemView.findViewById(R.id.repuesto_availability)

        fun bind(repuesto: Repuesto, onItemClick: (Repuesto) -> Unit) {
            nameView.text = repuesto.name
            priceView.text = "Precio: $${repuesto.price}"
            availabilityView.text = repuesto.availability

            if (repuesto.imageResId != 0) {
                imageView.setImageResource(repuesto.imageResId)  //
            } else {
                imageView.setImageResource(R.drawable.home)  //
            }

            itemView.setOnClickListener { onItemClick(repuesto) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepuestoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repuesto, parent, false)
        return RepuestoViewHolder(view)
    }


    override fun onBindViewHolder(holder: RepuestoViewHolder, position: Int) {
        holder.bind(repuestos[position], onItemClick)
    }

    override fun getItemCount() = repuestos.size
}