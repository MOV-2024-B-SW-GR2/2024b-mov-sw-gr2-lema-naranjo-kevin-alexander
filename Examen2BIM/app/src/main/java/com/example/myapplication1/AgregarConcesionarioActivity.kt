package com.example.myapplication1

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.models.BConcesionario

class AgregarConcesionarioActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etLatitud: EditText
    private lateinit var etLongitud: EditText
    private lateinit var cbEsFranquicia: CheckBox
    private lateinit var btnGuardar: Button
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_concesionario)

        etNombre = findViewById(R.id.etNombre)
        etDireccion = findViewById(R.id.etDireccion)
        etTelefono = findViewById(R.id.etTelefono)
        etLatitud = findViewById(R.id.etLatitud)
        etLongitud = findViewById(R.id.etLongitud)
        cbEsFranquicia = findViewById(R.id.cbEsFranquicia)
        btnGuardar = findViewById(R.id.btnGuardar)
        dbHelper = DBHelper(this)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val direccion = etDireccion.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val latitudText = etLatitud.text.toString().trim()
            val longitudText = etLongitud.text.toString().trim()
            val esFranquicia = cbEsFranquicia.isChecked

            Log.d("AgregarConcesionario", "Button clicked. Nombre: $nombre, Dirección: $direccion, Teléfono: $telefono, Lat: $latitudText, Lon: $longitudText, Es Franquicia: $esFranquicia")

            // Verificar que los campos no estén vacíos
            if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || latitudText.isEmpty() || longitudText.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convertir latitud y longitud a Double
            val latitud = latitudText.toDoubleOrNull()
            val longitud = longitudText.toDoubleOrNull()

            if (latitud == null || longitud == null) {
                Toast.makeText(this, "Ingrese valores válidos para latitud y longitud.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear el nuevo concesionario
            val nuevoConcesionario = BConcesionario(
                id = 0, // El id será autogenerado por la base de datos
                nombre = nombre,
                direccion = direccion,
                telefono = telefono,
                esFranquicia = esFranquicia,
                vehiculos = mutableListOf(),
                latitud = latitud,
                longitud = longitud
            )

            Log.d("AgregarConcesionario", "Nuevo concesionario creado: $nuevoConcesionario")

            // Insertar en la base de datos
            try {
                val db = dbHelper.writableDatabase
                val values = ContentValues().apply {
                    put("nombre", nuevoConcesionario.nombre)
                    put("direccion", nuevoConcesionario.direccion)
                    put("telefono", nuevoConcesionario.telefono)
                    put("esFranquicia", if (nuevoConcesionario.esFranquicia) 1 else 0)
                    put("latitud", nuevoConcesionario.latitud)
                    put("longitud", nuevoConcesionario.longitud)
                }
                val newRowId = db.insert("Concesionarios", null, values)
                db.close() // Cerrar la base de datos después de la operación

                Log.d("AgregarConcesionario", "Insert result: $newRowId")

                if (newRowId != -1L) {
                    Toast.makeText(this, "Concesionario agregado exitosamente", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Error al agregar el concesionario", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("AgregarConcesionario", "Error insertando en la base de datos", e)
                Toast.makeText(this, "Error en la base de datos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
