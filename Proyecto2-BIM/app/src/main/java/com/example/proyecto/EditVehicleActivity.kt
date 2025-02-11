package com.example.proyecto

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class EditVehicleActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var imgVehiculo: ImageView
    private var selectedImage: Bitmap? = null
    private var vehicle: Vehicle? = null
    private var vehicleId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_vehicle)

        dbHelper = DatabaseHelper(this)
        vehicleId = intent.getIntExtra("vehicle_id", -1)

        val spinnerMarcas: Spinner = findViewById(R.id.spinnerMarcas)
        val marcas = listOf("Selecciona una marca", "Toyota", "Nissan", "Ford", "Chevrolet")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, marcas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMarcas.adapter = adapter

        val editModelo: EditText = findViewById(R.id.editModelo)
        val editPlaca: EditText = findViewById(R.id.editPlaca)
        val editAnio: EditText = findViewById(R.id.editAnio)
        val editFechaCompra: EditText = findViewById(R.id.editFechaCompra)

        imgVehiculo = findViewById(R.id.imgVehiculo)

        if (vehicleId != -1) {
            vehicle = dbHelper.obtenerVehiculoPorId(vehicleId)
            vehicle?.let {
                spinnerMarcas.setSelection(marcas.indexOf(it.marca))
                editModelo.setText(it.modelo)
                editPlaca.setText(it.placa)
                editAnio.setText(it.anio.toString())
                editFechaCompra.setText(it.fechaCompra)

                val file = File(filesDir, it.imagePath)
                if (file.exists()) {
                    val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                    imgVehiculo.setImageBitmap(bitmap)
                    selectedImage = bitmap
                } else {
                    imgVehiculo.setImageResource(R.drawable.carro1)
                }
            }
        }

        imgVehiculo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }

        val btnActualizarVehiculo: Button = findViewById(R.id.btnActualizarVehiculo)
        btnActualizarVehiculo.setOnClickListener {
            val marca = spinnerMarcas.selectedItem.toString()
            val modelo = editModelo.text.toString()
            val placa = editPlaca.text.toString()
            val anio = editAnio.text.toString().toIntOrNull() ?: 0
            val fechaCompra = editFechaCompra.text.toString()

            if (marca == "Selecciona una marca" || modelo.isEmpty() || placa.isEmpty() || anio == 0 || fechaCompra.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Guardar la imagen, si se ha seleccionado una nueva
                val imagePath = selectedImage?.let { saveImageToStorage(it) } ?: vehicle?.imagePath ?: ""

                // Crear el vehículo actualizado
                val updatedVehicle = Vehicle(
                    id = vehicleId,
                    marca = marca,
                    modelo = modelo,
                    anio = anio,
                    placa = placa,
                    fechaCompra = fechaCompra,
                    imagePath = imagePath
                )

                // Actualizar el vehículo en la base de datos
                dbHelper.actualizarVehiculo(updatedVehicle)
                Toast.makeText(this, "Vehículo actualizado correctamente", Toast.LENGTH_SHORT).show()

                // Configurar el resultado de la actividad como OK
                setResult(RESULT_OK)

                // Finalizar la actividad actual (EditVehicleActivity)
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val imageUri = data?.data
            imageUri?.let {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                imgVehiculo.setImageBitmap(bitmap)
                selectedImage = bitmap
            }
        }
    }

    private fun saveImageToStorage(image: Bitmap): String {
        val fileName = "vehicle_image_${System.currentTimeMillis()}.jpg"
        val file = File(filesDir, fileName)
        image.compress(Bitmap.CompressFormat.JPEG, 100, file.outputStream())
        return fileName
    }
}
