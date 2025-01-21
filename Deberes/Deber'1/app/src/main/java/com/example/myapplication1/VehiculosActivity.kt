package com.example.myapplication1

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.adapters.VehiculosAdapter
import com.example.myapplication1.models.BVehiculo

class VehiculosActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregarVehiculo: Button
    private var vehiculos = mutableListOf<BVehiculo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehiculos)

        val idConcesionario = intent.getIntExtra("idConcesionario", -1)
        val concesionario = BBaseDatosMemoria.concesionarios.find { it.id == idConcesionario }
        vehiculos = concesionario?.vehiculos ?: mutableListOf()

        recyclerView = findViewById(R.id.recyclerViewVehiculos)
        btnAgregarVehiculo = findViewById(R.id.btnAgregarVehiculo)

        val adapter = VehiculosAdapter(
            vehiculos,
            onEdit = { vehiculo ->
                // Lógica para editar vehículo
            },
            onDelete = { id ->
                vehiculos.removeAll { it.id == id }
                recyclerView.adapter?.notifyDataSetChanged()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnAgregarVehiculo.setOnClickListener {
            // Lógica para agregar un nuevo vehículo
        }
    }
}
