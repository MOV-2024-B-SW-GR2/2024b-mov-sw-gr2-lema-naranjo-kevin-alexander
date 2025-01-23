package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.models.BConcesionario

class ActivityEditarConcesionario : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etTelefono: EditText
    private lateinit var cbEsFranquicia: CheckBox
    private lateinit var btnGuardar: Button
    private var concesionarioId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_concesionario)

        etNombre = findViewById(R.id.etNombreConcesionario)
        etDireccion = findViewById(R.id.etDireccionConcesionario)
        etTelefono = findViewById(R.id.etTelefonoConcesionario)
        cbEsFranquicia = findViewById(R.id.cbEsFranquicia)
        btnGuardar = findViewById(R.id.btnGuardarConcesionario)

        // Obtener ID del concesionario a editar
        concesionarioId = intent.getIntExtra("idConcesionario", -1)

        if (concesionarioId != -1) {
            // Si se pasa un ID válido, cargar los datos del concesionario
            val concesionario = BBaseDatosMemoria.concesionarios.find { it.id == concesionarioId }
            concesionario?.let {
                etNombre.setText(it.nombre)
                etDireccion.setText(it.direccion)
                etTelefono.setText(it.telefono)
                cbEsFranquicia.isChecked = it.esFranquicia
            }
        }

        btnGuardar.setOnClickListener {
            // Actualizar los datos del concesionario
            concesionarioId?.let { id ->
                val concesionario = BBaseDatosMemoria.concesionarios.find { it.id == id }
                concesionario?.apply {
                    nombre = etNombre.text.toString()
                    direccion = etDireccion.text.toString()
                    telefono = etTelefono.text.toString()
                    esFranquicia = cbEsFranquicia.isChecked
                }

                Toast.makeText(this, "Concesionario actualizado", Toast.LENGTH_SHORT).show()

                // Enviar el resultado de vuelta a MainActivity
                setResult(RESULT_OK)
                finish()  // Regresar a la pantalla principal
            }
        }
    }
}
