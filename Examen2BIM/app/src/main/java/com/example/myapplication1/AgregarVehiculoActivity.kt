package com.example.myapplication1

import android.content.ContentValues
import android.content.Intent
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
    private lateinit var dbHelper: DBHelper

    private var concesionarioId: Int = 0  // ID del concesionario al que pertenece el vehículo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_vehiculo)

        etMarca = findViewById(R.id.etMarca)
        etModelo = findViewById(R.id.etModelo)
        etAnio = findViewById(R.id.etAnio)
        etPrecio = findViewById(R.id.etPrecio)
        btnGuardarVehiculo = findViewById(R.id.btnGuardarVehiculo)
        dbHelper = DBHelper(this)

        // Obtener el ID del concesionario desde el intent
        concesionarioId = intent.getIntExtra("idConcesionario", 0)

        btnGuardarVehiculo.setOnClickListener {
            val marca = etMarca.text.toString().trim()
            val modelo = etModelo.text.toString().trim()
            val anio = etAnio.text.toString().trim()
            val precio = etPrecio.text.toString().trim()

            // Verificar que los campos no estén vacíos
            if (marca.isEmpty() || modelo.isEmpty() || anio.isEmpty() || precio.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Crear el nuevo vehículo con valores predeterminados para color y kilometraje
                val values = ContentValues().apply {
                    put("marca", marca)
                    put("modelo", modelo)
                    put("anio", anio.toInt())
                    put("precio", precio.toDouble())
                    put("kilometraje", 0)  // Valor predeterminado
                    put("esNuevo", 1)  // Puedes definir si es nuevo o no
                    put("color", "Desconocido")  // Valor predeterminado
                    put("concesionarioId", concesionarioId)
                }

                val db = dbHelper.writableDatabase
                val newRowId = db.insert("Vehiculos", null, values)

                if (newRowId != -1L) {
                    Toast.makeText(this, "Vehículo agregado exitosamente", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Error al agregar el vehículo", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
