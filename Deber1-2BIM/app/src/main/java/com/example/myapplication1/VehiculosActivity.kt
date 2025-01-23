package com.example.myapplication1


import android.content.Intent
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
    private val vehiculos = mutableListOf<BVehiculo>()  // Lista de vehículos del concesionario
    private var concesionarioId: Int = 0  // ID del concesionario al que pertenecen los vehículos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehiculos)

        recyclerView = findViewById(R.id.recyclerViewVehiculos)
        btnAgregarVehiculo = findViewById(R.id.btnAgregarVehiculo)

        // Obtener el ID del concesionario desde el intent
        concesionarioId = intent.getIntExtra("idConcesionario", 0)

        // Cargar los vehículos del concesionario
        cargarVehiculos()

        // Configuración del adapter para el RecyclerView
        val adapter = VehiculosAdapter(
            vehiculos,
            onEdit = { vehiculo ->
                // Lógica para editar el vehículo
                val intent = Intent(this, EditarVehiculoActivity::class.java)
                intent.putExtra("idVehiculo", vehiculo.id)
                startActivityForResult(intent, EDITAR_VEHICULO_REQUEST)
            },
            onDelete = { id ->
                // Eliminar vehículo y actualizar la lista
                vehiculos.removeAll { it.id == id }
                recyclerView.adapter?.notifyDataSetChanged()
            }
        )

        // Configurar el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Manejo de la acción de agregar un vehículo
        btnAgregarVehiculo.setOnClickListener {
            val intent = Intent(this, AgregarVehiculoActivity::class.java)
            intent.putExtra("idConcesionario", concesionarioId)
            startActivityForResult(intent, AGREGAR_VEHICULO_REQUEST)
        }
    }

    // Este método es necesario para recibir el resultado de la actividad de agregar/editar vehículo
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AGREGAR_VEHICULO_REQUEST && resultCode == RESULT_OK) {
            // Si la actividad de agregar vehículo se completó correctamente, actualizar la lista
            cargarVehiculos()
            recyclerView.adapter?.notifyDataSetChanged()
        } else if (requestCode == EDITAR_VEHICULO_REQUEST && resultCode == RESULT_OK) {
            // Si la actividad de editar vehículo se completó correctamente, actualizar la lista
            cargarVehiculos()
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    // Este método carga los vehículos del concesionario seleccionado
    private fun cargarVehiculos() {
        // Aquí cargamos la lista de vehículos del concesionario específico
        val concesionario = BBaseDatosMemoria.concesionarios.find { it.id == concesionarioId }
        vehiculos.clear()
        concesionario?.vehiculos?.let {
            vehiculos.addAll(it)
        }
    }

    companion object {
        const val AGREGAR_VEHICULO_REQUEST = 1
        const val EDITAR_VEHICULO_REQUEST = 2
    }
}