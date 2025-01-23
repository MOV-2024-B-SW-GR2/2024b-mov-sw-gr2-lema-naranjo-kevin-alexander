package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.models.BVehiculo


class EditarVehiculoActivity : AppCompatActivity() {

    private lateinit var etMarca: EditText
    private lateinit var etModelo: EditText
    private lateinit var etAnio: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etKilometraje: EditText
    private lateinit var etColor: EditText
    private lateinit var btnGuardar: Button

    private var idVehiculo: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_vehiculo)

        // Enlazar los elementos del layout
        etMarca = findViewById(R.id.etMarca)
        etModelo = findViewById(R.id.etModelo)
        etAnio = findViewById(R.id.etAnio)
        etPrecio = findViewById(R.id.etPrecio)
        etKilometraje = findViewById(R.id.etKilometraje)
        etColor = findViewById(R.id.etColor)
        btnGuardar = findViewById(R.id.btnGuardar)

        // Obtener el ID del vehículo pasado por el Intent
        idVehiculo = intent.getIntExtra("idVehiculo", -1)

        // Buscar el vehículo usando el ID
        val vehiculo = buscarVehiculoPorId(idVehiculo)

        // Cargar los datos del vehículo en los EditText
        vehiculo?.let {
            etMarca.setText(it.marca)
            etModelo.setText(it.modelo)
            etAnio.setText(it.anio.toString())
            etPrecio.setText(it.precio.toString())
            etKilometraje.setText(it.kilometraje.toString())
            etColor.setText(it.color)
        }

        // Guardar los cambios
        btnGuardar.setOnClickListener {
            val marca = etMarca.text.toString()
            val modelo = etModelo.text.toString()
            val anio = etAnio.text.toString().toInt()
            val precio = etPrecio.text.toString().toDouble()
            val kilometraje = etKilometraje.text.toString().toInt()
            val color = etColor.text.toString()

            // Actualizar el vehículo
            val vehiculoActualizado = BVehiculo(idVehiculo!!, marca, modelo, anio, precio, kilometraje, false, color)
            actualizarVehiculo(vehiculoActualizado)

            // Devolver el resultado y cerrar la actividad
            val resultIntent = Intent()
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    // Buscar un vehículo por ID en la lista de vehículos del concesionario
    private fun buscarVehiculoPorId(id: Int?): BVehiculo? {
        for (concesionario in BBaseDatosMemoria.concesionarios) {
            val vehiculo = concesionario.vehiculos.find { it.id == id }
            if (vehiculo != null) return vehiculo
        }
        return null
    }

    // Actualizar el vehículo en la base de datos
    private fun actualizarVehiculo(vehiculo: BVehiculo) {
        for (concesionario in BBaseDatosMemoria.concesionarios) {
            val index = concesionario.vehiculos.indexOfFirst { it.id == vehiculo.id }
            if (index != -1) {
                concesionario.vehiculos[index] = vehiculo
                break
            }
        }
    }
}
