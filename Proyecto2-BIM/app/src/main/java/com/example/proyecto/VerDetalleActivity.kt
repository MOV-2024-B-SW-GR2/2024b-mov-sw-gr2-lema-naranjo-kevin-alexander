package com.example.proyecto

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VerDetalleActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private var vehicle: Vehicle? = null  // Usamos nullable para manejar el caso de null
    private val EDIT_REQUEST_CODE = 1
    private lateinit var recyclerView: RecyclerView
    private lateinit var reparacionAdapter: ReparacionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_detalle)

        dbHelper = DatabaseHelper(this)

        val vehicleId = intent.getIntExtra("vehicle_id", -1)

        if (vehicleId != -1) {
            vehicle = dbHelper.obtenerVehiculoPorId(vehicleId)

            if (vehicle != null) {
                val imgVehiculo: ImageView = findViewById(R.id.imgVehiculo)
                val txtMarca: TextView = findViewById(R.id.txtMarca)
                val txtTitulo: TextView = findViewById(R.id.txtTitulo)
                val txtModelo: TextView = findViewById(R.id.txtModelo)
                val txtPlaca: TextView = findViewById(R.id.txtPlaca)
                val txtAnio: TextView = findViewById(R.id.txtAnio)
                val txtFechaCompra: TextView = findViewById(R.id.txtFechaCompra)
                val btnEditar: ImageButton = findViewById(R.id.btnEditar)
                val btnEliminar: ImageButton = findViewById(R.id.btnEliminar)
                val btnAgregarReparacion: ImageButton = findViewById(R.id.btnAgregarReparacion)
                recyclerView = findViewById(R.id.recyclerReparaciones)

                txtTitulo.text = "${vehicle?.marca} ${vehicle?.modelo}"
                txtMarca.text = "Marca: ${vehicle?.marca}"
                txtModelo.text = "Modelo: ${vehicle?.modelo}"
                txtPlaca.text = "Placa: ${vehicle?.placa}"
                txtAnio.text = "Año: ${vehicle?.anio}"
                txtFechaCompra.text = "Fecha de compra: ${vehicle?.fechaCompra}"

                val imagePath = vehicle?.imagePath
                if (!imagePath.isNullOrEmpty()) {
                    try {
                        val fileInputStream = openFileInput(imagePath)
                        val bitmap = BitmapFactory.decodeStream(fileInputStream)
                        imgVehiculo.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show()
                    }
                }

                btnEditar.setOnClickListener {
                    val intent = Intent(this, EditVehicleActivity::class.java)
                    intent.putExtra("vehicle_id", vehicle?.id)
                    startActivityForResult(intent, EDIT_REQUEST_CODE)
                }

                btnEliminar.setOnClickListener {
                    mostrarDialogoConfirmacion(vehicleId)
                }

                btnAgregarReparacion.setOnClickListener {
                    val intent = Intent(this, AgregarReparacionActivity::class.java)
                    intent.putExtra("vehicle_id", vehicle?.id)
                    startActivity(intent)
                }

                // Configurar RecyclerView
                recyclerView.layoutManager = LinearLayoutManager(this)
                cargarReparaciones(vehicleId)
            } else {
                Toast.makeText(this, "Vehículo no encontrado", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "ID del vehículo no válido", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun cargarReparaciones(vehicleId: Int) {
        val reparaciones = dbHelper.obtenerReparacionesPorVehiculo(vehicleId)
        reparacionAdapter = ReparacionAdapter(reparaciones)
        recyclerView.adapter = reparacionAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun mostrarDialogoConfirmacion(vehicleId: Int) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar vehículo")
            .setMessage("¿Estás seguro de que deseas eliminar este vehículo?")
            .setPositiveButton("Sí") { _, _ ->
                dbHelper.eliminarVehiculo(vehicleId)
                Toast.makeText(this, "Vehículo eliminado", Toast.LENGTH_SHORT).show()
                val intent = Intent()
                setResult(RESULT_OK, intent)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }
}

