package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.VehiculosActivity.Companion.EDITAR_VEHICULO_REQUEST
import com.example.myapplication1.adapters.ConcesionariosAdapter
import com.example.myapplication1.models.BConcesionario

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregarConcesionario: Button
    private lateinit var dbHelper: DBHelper
    private lateinit var adapter: ConcesionariosAdapter
    private var concesionarios: MutableList<BConcesionario> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewConcesionarios)

        btnAgregarConcesionario = findViewById(R.id.btnAgregarConcesionario)
        dbHelper = DBHelper(this)

        // Configurar el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Cargar concesionarios desde la base de datos SQLite
        cargarConcesionarios()

        // Configuración del adapter para el RecyclerView
        adapter = ConcesionariosAdapter(
            concesionarios,
            onClick = { concesionario ->
                val intent = Intent(this, VehiculosActivity::class.java)
                intent.putExtra("idConcesionario", concesionario.id)
                startActivityForResult(intent, EDITAR_VEHICULO_REQUEST)
            },
            onEdit = { concesionario ->
                // Lógica para editar el concesionario
                val intent = Intent(this, ActivityEditarConcesionario::class.java)
                intent.putExtra("idConcesionario", concesionario.id)
                startActivityForResult(intent, EDITAR_CONCESIONARIO_REQUEST)
            },
            onDelete = { id ->
                concesionarios.removeAll { it.id == id }
                recyclerView.adapter?.notifyDataSetChanged()
            },
            onVerUbicacion = { concesionario ->
                // Lógica para manejar el click en "Ver Ubicación"
                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("latitud", concesionario.latitud)
                intent.putExtra("longitud", concesionario.longitud)
                startActivity(intent)
            }

        )

        recyclerView.adapter = adapter

        // Manejo de la acción de agregar un concesionario
        btnAgregarConcesionario.setOnClickListener {
            val intent = Intent(this, AgregarConcesionarioActivity::class.java)
            startActivityForResult(intent, AGREGAR_CONCESIONARIO_REQUEST)
        }
    }

    private fun cargarConcesionarios() {
        // Cargar los concesionarios desde la base de datos
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Concesionarios", null)

        val concesionariosList = mutableListOf<BConcesionario>()

        cursor.use {
            while (it.moveToNext()) {
                val idIndex = it.getColumnIndex("id")
                val nombreIndex = it.getColumnIndex("nombre")
                val direccionIndex = it.getColumnIndex("direccion")
                val telefonoIndex = it.getColumnIndex("telefono")
                val esFranquiciaIndex = it.getColumnIndex("esFranquicia")
                val latitudIndex = it.getColumnIndex("latitud")
                val longitudIndex = it.getColumnIndex("longitud")

                // Asegurarse de que todas las columnas estén presentes
                if (idIndex != -1 && nombreIndex != -1 && direccionIndex != -1 && telefonoIndex != -1 && esFranquiciaIndex != -1 && latitudIndex != -1 && longitudIndex != -1) {
                    val id = it.getInt(idIndex) // Obtener el ID
                    val nombre = it.getString(nombreIndex) ?: "" // Nombre, manejar valor nulo
                    val direccion = it.getString(direccionIndex) ?: "" // Dirección, manejar valor nulo
                    val telefono = it.getString(telefonoIndex) ?: "" // Teléfono, manejar valor nulo
                    val esFranquicia = it.getInt(esFranquiciaIndex) == 1 // Convertir a booleano
                    val latitud = it.getDouble(latitudIndex) // Latitud
                    val longitud = it.getDouble(longitudIndex) // Longitud

                    // Crear el concesionario y agregarlo a la lista
                    val concesionario = BConcesionario(id, nombre, direccion, telefono, esFranquicia, mutableListOf() , latitud, longitud, )
                    concesionariosList.add(concesionario)
                } else {
                    // Manejar el error de columna no encontrada
                    Log.e("DatabaseError", "Una o más columnas no se encontraron en el cursor.")
                }
            }
        }
        concesionarios.clear()
        concesionarios.addAll(concesionariosList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == AGREGAR_CONCESIONARIO_REQUEST || requestCode == EDITAR_CONCESIONARIO_REQUEST) && resultCode == RESULT_OK) {
            cargarConcesionarios()
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        const val EDITAR_CONCESIONARIO_REQUEST = 1
        const val AGREGAR_CONCESIONARIO_REQUEST = 2
    }
}
