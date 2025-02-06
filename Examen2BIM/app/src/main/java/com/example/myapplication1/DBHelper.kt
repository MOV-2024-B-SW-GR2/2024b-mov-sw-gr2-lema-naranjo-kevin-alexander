package com.example.myapplication1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "concesionarios_db", null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        // Crear la tabla de concesionarios con latitud y longitud
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS Concesionarios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, " +
                    "direccion TEXT, " +
                    "telefono TEXT, " +
                    "esFranquicia INTEGER, " +
                    "latitud REAL, " +
                    "longitud REAL)"
        )

        // Crear la tabla de vehículos con clave foránea a concesionarios
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS Vehiculos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "marca TEXT, " +
                    "modelo TEXT, " +
                    "anio INTEGER, " +
                    "precio REAL, " +
                    "kilometraje INTEGER, " +
                    "esNuevo INTEGER, " +
                    "color TEXT, " +
                    "concesionarioId INTEGER, " +
                    "FOREIGN KEY(concesionarioId) REFERENCES Concesionarios(id))"
        )

        // Insertar datos de prueba
        insertarConcesionariosDePrueba(db)
        insertarVehiculosDePrueba(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Vehiculos")
        db?.execSQL("DROP TABLE IF EXISTS Concesionarios")
        onCreate(db)
    }

    // Método para insertar concesionarios de prueba
    private fun insertarConcesionariosDePrueba(db: SQLiteDatabase?) {
        val concesionarios = listOf(
            ContentValues().apply {
                put("nombre", "AutoCenter")
                put("direccion", "Av. Principal 123")
                put("telefono", "123456789")
                put("esFranquicia", 1)
                put("latitud", -12.04318)
                put("longitud", -77.02824)
            },
            ContentValues().apply {
                put("nombre", "CarMax")
                put("direccion", "Calle Secundaria 456")
                put("telefono", "987654321")
                put("esFranquicia", 0)
                put("latitud", -12.04567)
                put("longitud", -77.03012)
            }
        )

        concesionarios.forEach { db?.insert("Concesionarios", null, it) }
    }

    // Método para insertar vehículos de prueba con concesionarios existentes
    private fun insertarVehiculosDePrueba(db: SQLiteDatabase?) {
        val vehiculos = listOf(
            ContentValues().apply {
                put("marca", "Toyota")
                put("modelo", "Corolla")
                put("anio", 2020)
                put("precio", 20000)
                put("kilometraje", 15000)
                put("esNuevo", 0) // 0 para usado, 1 para nuevo
                put("color", "Rojo")
                put("concesionarioId", 1) // ID del primer concesionario
            },
            ContentValues().apply {
                put("marca", "Honda")
                put("modelo", "Civic")
                put("anio", 2019)
                put("precio", 18000)
                put("kilometraje", 20000)
                put("esNuevo", 0)
                put("color", "Azul")
                put("concesionarioId", 2) // ID del segundo concesionario
            }
        )

        vehiculos.forEach { db?.insert("Vehiculos", null, it) }
    }
}
