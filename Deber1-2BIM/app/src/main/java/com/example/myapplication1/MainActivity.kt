package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
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
    private val concesionarios = BBaseDatosMemoria.concesionarios

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewConcesionarios)
        btnAgregarConcesionario = findViewById(R.id.btnAgregarConcesionario)

        // Configuración del adapter para el RecyclerView
        val adapter = ConcesionariosAdapter(
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
                // Eliminar concesionario y actualizar el RecyclerView
                concesionarios.removeAll { it.id == id }
                recyclerView.adapter?.notifyDataSetChanged()
            }
        )

        // Configurar el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Manejo de la acción de agregar un concesionario
        btnAgregarConcesionario.setOnClickListener {
            val intent = Intent(this, AgregarConcesionarioActivity::class.java)
            startActivityForResult(intent ,AGREGAR_CONCESIONARIO_REQUEST)
        }
    }

    // Este método es necesario para recibir el resultado de la actividad de edición
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDITAR_CONCESIONARIO_REQUEST && resultCode == RESULT_OK) {
            // Si la actividad de edición se completó correctamente, actualizar la lista
            recyclerView.adapter?.notifyDataSetChanged()
        } else if (requestCode == AGREGAR_CONCESIONARIO_REQUEST && resultCode == RESULT_OK) {
        // Si la actividad de agregar concesionario se completó correctamente, actualizar la lista
        recyclerView.adapter?.notifyDataSetChanged()
    }
    }

    companion object {
        const val EDITAR_CONCESIONARIO_REQUEST = 1
        const val AGREGAR_CONCESIONARIO_REQUEST = 2
    }
}
