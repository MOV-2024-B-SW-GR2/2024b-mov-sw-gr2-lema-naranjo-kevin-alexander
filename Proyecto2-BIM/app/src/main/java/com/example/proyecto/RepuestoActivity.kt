package com.example.proyecto

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RepuestoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repuestos)

        // Lista de repuestos
        val repuestos = listOf(
            Repuesto(R.drawable.repuesto1, "Volante XMAX", 120.0, "En stock"),
            Repuesto(R.drawable.repuesto1, "Auto King Workshop", 250.0, "Pocas unidades"),
            Repuesto(R.drawable.repuesto1, "Luces LED Ultra", 80.0, "Agotado"),
            Repuesto(R.drawable.repuesto1, "Filtro de aire deportivo", 50.0, "En stock"),
            Repuesto(R.drawable.repuesto1, "Pastillas de freno", 90.0, "Pocas unidades")
        )

        // Configurar RecyclerView
        val repuestoRecyclerView: RecyclerView = findViewById(R.id.recycler_repuestos)
        repuestoRecyclerView.layoutManager = LinearLayoutManager(this)
        repuestoRecyclerView.adapter = RepuestoAdapter(repuestos) { selectedRepuesto ->
            Toast.makeText(this, "Repuesto seleccionado: ${selectedRepuesto.name}", Toast.LENGTH_SHORT).show()
        }
    }
}
