package com.example.myapplication1

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private lateinit var dbHelper: DBHelper

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
        dbHelper = DBHelper(this)

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

    // Buscar un vehículo por ID en la base de datos
    private fun buscarVehiculoPorId(id: Int?): BVehiculo? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Vehiculos WHERE id = ?", arrayOf(id.toString()))

        var vehiculo: BVehiculo? = null
        cursor.use {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndex("id")
                val marcaIndex = it.getColumnIndex("marca")
                val modeloIndex = it.getColumnIndex("modelo")
                val anioIndex = it.getColumnIndex("anio")
                val precioIndex = it.getColumnIndex("precio")
                val kilometrajeIndex = it.getColumnIndex("kilometraje")
                val esNuevoIndex = it.getColumnIndex("esNuevo")
                val colorIndex = it.getColumnIndex("color")

                if (idIndex != -1 && marcaIndex != -1 && modeloIndex != -1 && anioIndex != -1 &&
                    precioIndex != -1 && kilometrajeIndex != -1 && esNuevoIndex != -1 && colorIndex != -1) {

                    val id = it.getInt(idIndex)
                    val marca = it.getString(marcaIndex)
                    val modelo = it.getString(modeloIndex)
                    val anio = it.getInt(anioIndex)
                    val precio = it.getDouble(precioIndex)
                    val kilometraje = it.getInt(kilometrajeIndex)
                    val esNuevo = it.getInt(esNuevoIndex) == 1
                    val color = it.getString(colorIndex)

                    vehiculo = BVehiculo(id, marca, modelo, anio, precio, kilometraje, esNuevo, color)
                } else {
                    Log.e("DatabaseError", "Una o más columnas no se encontraron en el cursor.")
                }
            }
        }
        return vehiculo
    }

    // Actualizar el vehículo en la base de datos
    private fun actualizarVehiculo(vehiculo: BVehiculo) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("marca", vehiculo.marca)
            put("modelo", vehiculo.modelo)
            put("anio", vehiculo.anio)
            put("precio", vehiculo.precio)
            put("kilometraje", vehiculo.kilometraje)
            put("esNuevo", if (vehiculo.esNuevo) 1 else 0)
            put("color", vehiculo.color)
        }
        db.update("Vehiculos", values, "id = ?", arrayOf(vehiculo.id.toString()))
    }
}
