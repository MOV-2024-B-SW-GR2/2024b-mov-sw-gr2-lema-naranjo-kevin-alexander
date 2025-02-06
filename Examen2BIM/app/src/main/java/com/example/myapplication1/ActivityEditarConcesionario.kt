package com.example.myapplication1

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.models.BConcesionario

class ActivityEditarConcesionario : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etTelefono: EditText
    private lateinit var cbEsFranquicia: CheckBox
    private lateinit var etLatitud: EditText
    private lateinit var etLongitud: EditText
    private lateinit var btnGuardar: Button
    private lateinit var dbHelper: DBHelper
    private var concesionarioId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_concesionario)

        etNombre = findViewById(R.id.etNombreConcesionario)
        etDireccion = findViewById(R.id.etDireccionConcesionario)
        etTelefono = findViewById(R.id.etTelefonoConcesionario)
        cbEsFranquicia = findViewById(R.id.cbEsFranquicia)
        etLatitud = findViewById(R.id.etLatitudConcesionario)
        etLongitud = findViewById(R.id.etLongitudConcesionario)
        btnGuardar = findViewById(R.id.btnGuardarConcesionario)
        dbHelper = DBHelper(this)

        // Obtener ID del concesionario a editar
        concesionarioId = intent.getIntExtra("idConcesionario", -1)

        if (concesionarioId != -1) {
            // Cargar datos del concesionario si existe
            val concesionario = buscarConcesionarioPorId(concesionarioId!!)
            concesionario?.let {
                etNombre.setText(it.nombre)
                etDireccion.setText(it.direccion)
                etTelefono.setText(it.telefono)
                cbEsFranquicia.isChecked = it.esFranquicia
                etLatitud.setText(it.latitud.toString())
                etLongitud.setText(it.longitud.toString())
            }
        }

        btnGuardar.setOnClickListener {
            guardarCambios()
        }
    }

    private fun guardarCambios() {
        // Validar entradas
        val nombre = etNombre.text.toString().trim()
        val direccion = etDireccion.text.toString().trim()
        val telefono = etTelefono.text.toString().trim()
        val latitudText = etLatitud.text.toString().trim()
        val longitudText = etLongitud.text.toString().trim()

        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || latitudText.isEmpty() || longitudText.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val latitud = latitudText.toDoubleOrNull()
        val longitud = longitudText.toDoubleOrNull()

        if (latitud == null || longitud == null) {
            Toast.makeText(this, "Latitud y longitud deben ser valores numéricos", Toast.LENGTH_SHORT).show()
            return
        }

        val esFranquicia = cbEsFranquicia.isChecked

        concesionarioId?.let { id ->
            val updatedConcesionario = BConcesionario(id, nombre, direccion, telefono, esFranquicia, mutableListOf(), latitud, longitud)
            val filasActualizadas = actualizarConcesionario(updatedConcesionario)

            if (filasActualizadas > 0) {
                Toast.makeText(this, "Concesionario actualizado correctamente", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar concesionario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Buscar un concesionario por ID en la base de datos
    private fun buscarConcesionarioPorId(id: Int): BConcesionario? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Concesionarios WHERE id = ?", arrayOf(id.toString()))

        var concesionario: BConcesionario? = null
        cursor.use {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndex("id")
                val nombreIndex = it.getColumnIndex("nombre")
                val direccionIndex = it.getColumnIndex("direccion")
                val telefonoIndex = it.getColumnIndex("telefono")
                val esFranquiciaIndex = it.getColumnIndex("esFranquicia")
                val latitudIndex = it.getColumnIndex("latitud")
                val longitudIndex = it.getColumnIndex("longitud")

                if (idIndex != -1 && nombreIndex != -1 && direccionIndex != -1 &&
                    telefonoIndex != -1 && esFranquiciaIndex != -1 && latitudIndex != -1 && longitudIndex != -1
                ) {
                    val id = it.getInt(idIndex)
                    val nombre = it.getString(nombreIndex)
                    val direccion = it.getString(direccionIndex)
                    val telefono = it.getString(telefonoIndex)
                    val esFranquicia = it.getInt(esFranquiciaIndex) == 1
                    val latitud = it.getDouble(latitudIndex)
                    val longitud = it.getDouble(longitudIndex)

                    concesionario = BConcesionario(id, nombre, direccion, telefono, esFranquicia, mutableListOf(), latitud, longitud)
                } else {
                    Log.e("DatabaseError", "Una o más columnas no se encontraron en el cursor.")
                }
            }
        }
        db.close()
        return concesionario
    }

    // Actualizar concesionario en la base de datos
    private fun actualizarConcesionario(concesionario: BConcesionario): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", concesionario.nombre)
            put("direccion", concesionario.direccion)
            put("telefono", concesionario.telefono)
            put("esFranquicia", if (concesionario.esFranquicia) 1 else 0)
            put("latitud", concesionario.latitud)
            put("longitud", concesionario.longitud)
        }
        val filasActualizadas = db.update("Concesionarios", values, "id = ?", arrayOf(concesionario.id.toString()))
        db.close()
        return filasActualizadas
    }
}
