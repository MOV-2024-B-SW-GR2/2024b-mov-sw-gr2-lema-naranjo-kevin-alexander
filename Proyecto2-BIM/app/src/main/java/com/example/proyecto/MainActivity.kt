package com.example.proyecto

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar el helper de base de datos
        dbHelper = DatabaseHelper(this)

        val btnAgregar: ImageButton = findViewById(R.id.btn_add)
        btnAgregar.setOnClickListener {
            mostrarDialogoAgregar()
        }

        // Inicializar y configurar RecyclerViews
        setupVehicleRecyclerView()
        setupRepuestoRecyclerView()
    }

    private fun mostrarDialogoAgregar() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_agregar)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true) // Permite cerrar el diálogo tocando fuera
        dialog.show()

        val btnVehicle: LinearLayout = dialog.findViewById(R.id.btn_vehicle)
        btnVehicle.setOnClickListener {
            val intent = Intent(this, AddVehicleActivity::class.java)
            startActivityForResult(intent, 1) // Iniciar con startActivityForResult
            dialog.dismiss() // Cerrar el diálogo al hacer clic en el botón
        }
    }

    // Configurar RecyclerView para mostrar los vehículos
    private fun setupVehicleRecyclerView() {
        val vehicles = dbHelper.obtenerVehiculos() // Recupera los vehículos desde la BD

        val vehicleRecyclerView: RecyclerView = findViewById(R.id.recycler_vehicles)
        vehicleRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        vehicleRecyclerView.adapter = VehicleAdapter(vehicles) { selectedVehicle ->
            // Verifica si el vehículo tiene un ID válido
            if (selectedVehicle.id != null) {
                Toast.makeText(this, "Vehículo seleccionado: ${selectedVehicle.id}", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, VerDetalleActivity::class.java)
                intent.putExtra("vehicle_id", selectedVehicle.id) // Pasar el ID del vehículo
                startActivityForResult(intent, 100) // Código 100 para editar vehículo
            } else {
                Toast.makeText(this, "ID del vehículo no válido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Configurar RecyclerView para mostrar repuestos
    private fun setupRepuestoRecyclerView() {
        val repuestos = dbHelper.obtenerRepuestos()

        for (repuesto in repuestos) {
            Log.d("DB_TEST", "Repuestoaaaaaaaaaaa: ${repuesto.name}, Image ID: ${repuesto.imageResId}")
        }

        val repuestoRecyclerView: RecyclerView = findViewById(R.id.recycler_repuestos)
        repuestoRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        repuestoRecyclerView.adapter = RepuestoAdapter(repuestos) { selectedRepuesto ->
            Toast.makeText(this, "Repuesto seleccionado: ${selectedRepuesto.name}", Toast.LENGTH_SHORT).show()
        }
    }




    // Método que maneja el resultado de otras actividades
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) { // Código 100 para la edición de vehículos
                Log.d("MainActivity", "Resultado OK recibido de EditVehicleActivity, actualizando lista de vehículos.")
                refreshVehicleList() // Actualiza la lista de vehículos
            } else if (requestCode == 1) {
                Log.d("MainActivity", "Resultado OK recibido de AddVehicleActivity, actualizando lista de vehículos.")
                refreshVehicleList() // Actualiza la lista de vehículos al agregar uno nuevo
            }
        }
    }

    // Método para actualizar el RecyclerView de vehículos
    private fun refreshVehicleList() {
        val updatedVehicles = dbHelper.obtenerVehiculos() // Recupera los vehículos actualizados
        Log.d("MainActivity", "Vehículos actualizados: $updatedVehicles")
        val vehicleRecyclerView: RecyclerView = findViewById(R.id.recycler_vehicles)
        val adapter = vehicleRecyclerView.adapter as VehicleAdapter
        adapter.updateVehicles(updatedVehicles) // Método en el adaptador para actualizar la lista
        Log.d("MainActivity", "Lista de vehículos actualizada en el adaptador.")
    }
}
