package com.example.myapplication1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.R
import com.example.myapplication1.models.BVehiculo

class AgregarVehiculoActivity : AppCompatActivity() {
    private lateinit var etMarca: EditText
    private lateinit var etModelo: EditText
    private lateinit var etAnio: EditText
    private lateinit var etPrecio: EditText
    private lateinit var btnGuardarVehiculo: Button

    private var concesionarioId: Int = 0  // ID del concesionario al que pertenece el vehículo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_vehiculo)

        etMarca = findViewById(R.id.etMarca)
        etModelo = findViewById(R.id.etModelo)
        etAnio = findViewById(R.id.etAnio)
        etPrecio = findViewById(R.id.etPrecio)
        btnGuardarVehiculo = findViewById(R.id.btnGuardarVehiculo)

        // Obtener el ID del concesionario desde el intent
        concesionarioId = intent.getIntExtra("idConcesionario", 0)

        // Lógica para guardar un nuevo vehículo
        btnGuardarVehiculo.setOnClickListener {
            val marca = etMarca.text.toString().trim()
            val modelo = etModelo.text.toString().trim()
            val anio = etAnio.text.toString().trim()
            val precio = etPrecio.text.toString().trim()

            // Verificar que los campos no estén vacíos
            if (marca.isEmpty() || modelo.isEmpty() || anio.isEmpty() || precio.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Crear el nuevo vehículo
                val nuevoVehiculo = BVehiculo(
                    id = BBaseDatosMemoria.concesionarios.flatMap { it.vehiculos }.size + 1,
                    marca = marca,
                    modelo = modelo,
                    anio = anio.toInt(),
                    precio = precio.toDouble(),
                    kilometraje = 0, // Puedes modificar este valor si es necesario
                    esNuevo = true, // Puedes definir si es nuevo o no
                    color = "Desconocido" // Puedes modificar este valor si lo deseas
                )

                // Obtener el concesionario y agregar el vehículo
                val concesionario = BBaseDatosMemoria.concesionarios.find { it.id == concesionarioId }
                concesionario?.vehiculos?.add(nuevoVehiculo)

                // Devolver resultado OK a la actividad anterior
                setResult(RESULT_OK)
                finish()  // Finaliza la actividad
            }
        }
    }
}
