package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        val adapter = ConcesionariosAdapter(
            concesionarios,
            onClick = { concesionario ->
                val intent = Intent(this, VehiculosActivity::class.java)
                intent.putExtra("idConcesionario", concesionario.id)
                startActivity(intent)
            },
            onEdit = { concesionario ->
                // Lógica para editar el concesionario
            },
            onDelete = { id ->
                concesionarios.removeAll { it.id == id }
                recyclerView.adapter?.notifyDataSetChanged()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnAgregarConcesionario.setOnClickListener {
            // Lógica para agregar un nuevo concesionario

        }

    }
}
