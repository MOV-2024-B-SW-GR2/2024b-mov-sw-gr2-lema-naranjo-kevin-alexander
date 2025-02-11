package com.example.proyecto

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AgregarReparacionActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var spinnerRepuestos: Spinner
    private var selectedRepuesto: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_reparacion)

        dbHelper = DatabaseHelper(this)
        val vehicleId = intent.getIntExtra("vehicle_id", -1)

        // Inicializamos el Spinner
        spinnerRepuestos = findViewById(R.id.spinnerRepuestos)

        // Cargamos los repuestos desde la base de datos y los mostramos en el Spinner
        val repuestosList = dbHelper.obtenerTodosLosRepuestos() // Ahora devuelve una lista de Strings
        Log.d("SpinnerDebug", "Lista de repuestos: $repuestosList")
        Toast.makeText(this, "Repuestos: $repuestosList", Toast.LENGTH_LONG).show()
        Log.d("SpinnerDebug", "Lista de repuestos: $repuestosList")
        if (repuestosList.isNotEmpty()) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                repuestosList
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRepuestos.adapter = adapter
        } else {
            Toast.makeText(this, "No hay repuestos disponibles", Toast.LENGTH_SHORT).show()
        }

        // Obtenemos el repuesto seleccionado del Spinner
        spinnerRepuestos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                selectedRepuesto = repuestosList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedRepuesto = ""
            }
        }

        // Configuración del botón Guardar
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        btnGuardar.setOnClickListener {
            val fecha = findViewById<EditText>(R.id.etFecha).text.toString()
            val tipo = findViewById<EditText>(R.id.etTipo).text.toString()
            val precio = findViewById<EditText>(R.id.etPrecio).text.toString().toDoubleOrNull() ?: 0.0
            val observacion = findViewById<EditText>(R.id.etObservacion).text.toString()

            if (fecha.isEmpty() || tipo.isEmpty() || selectedRepuesto.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val reparacion = Reparacion(
                vehicleId = vehicleId,
                fecha = fecha,
                tipo = tipo,
                repuestos = selectedRepuesto, // Usamos el repuesto seleccionado
                precio = precio,
                observacion = observacion
            )

            dbHelper.agregarReparacion(reparacion)
            Toast.makeText(this, "Reparación añadida con éxito", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
