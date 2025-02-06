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
    private lateinit var dbHelper: DBHelper
    private val vehiculos = mutableListOf<BVehiculo>()  // Lista de vehículos del concesionario
    private var concesionarioId: Int = 0  // ID del concesionario al que pertenecen los vehículos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehiculos)

        recyclerView = findViewById(R.id.recyclerViewVehiculos)
        btnAgregarVehiculo = findViewById(R.id.btnAgregarVehiculo)
        dbHelper = DBHelper(this)

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
                // Eliminar vehículo desde la base de datos SQLite
                eliminarVehiculo(id)
                cargarVehiculos()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == AGREGAR_VEHICULO_REQUEST || requestCode == EDITAR_VEHICULO_REQUEST) && resultCode == RESULT_OK) {
            cargarVehiculos()
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun cargarVehiculos() {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Vehiculos WHERE concesionarioId = ?", arrayOf(concesionarioId.toString()))

        val vehiculosList = mutableListOf<BVehiculo>()
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow("id"))
                val marca = it.getString(it.getColumnIndexOrThrow("marca"))
                val modelo = it.getString(it.getColumnIndexOrThrow("modelo"))
                val anio = it.getInt(it.getColumnIndexOrThrow("anio"))
                val precio = it.getDouble(it.getColumnIndexOrThrow("precio"))
                val esNuevo = it.getInt(it.getColumnIndexOrThrow("esNuevo")) == 1
                val color = it.getString(it.getColumnIndexOrThrow("color"))

                val vehiculo = BVehiculo(id, marca, modelo, anio, precio, 0, esNuevo, color)
                vehiculosList.add(vehiculo)
            }
        }
        vehiculos.clear()
        vehiculos.addAll(vehiculosList)
    }

    private fun eliminarVehiculo(id: Int) {
        val db = dbHelper.writableDatabase
        db.delete("Vehiculos", "id = ?", arrayOf(id.toString()))
    }

    companion object {
        const val AGREGAR_VEHICULO_REQUEST = 1
        const val EDITAR_VEHICULO_REQUEST = 2
    }
}
