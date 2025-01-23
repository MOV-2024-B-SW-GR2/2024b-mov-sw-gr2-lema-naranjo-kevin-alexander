package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
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
    private lateinit var cbEsFranquicia: CheckBox
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_concesionario)

        etNombre = findViewById(R.id.etNombre)
        etDireccion = findViewById(R.id.etDireccion)
        etTelefono = findViewById(R.id.etTelefono)
        cbEsFranquicia = findViewById(R.id.cbEsFranquicia)
        btnGuardar = findViewById(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val direccion = etDireccion.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val esFranquicia = cbEsFranquicia.isChecked

            // Verificar que los campos no estén vacíos
            if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Crear el nuevo concesionario
                val nuevoConcesionario = BConcesionario(
                    id = BBaseDatosMemoria.concesionarios.size + 1,
                    nombre = nombre,
                    direccion = direccion,
                    telefono = telefono,
                    esFranquicia = esFranquicia,
                    vehiculos = mutableListOf()
                )

                // Agregar a la base de datos en memoria
                BBaseDatosMemoria.concesionarios.add(nuevoConcesionario)

                // Mostrar mensaje de éxito
                Toast.makeText(this, "Concesionario agregado exitosamente", Toast.LENGTH_SHORT).show()

                // Devolver resultado OK a MainActivity
                setResult(RESULT_OK)
                finish()
            }
        }
    }
}