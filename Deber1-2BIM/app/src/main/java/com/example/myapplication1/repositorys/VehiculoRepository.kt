package com.example.myapplication1.repositorys

import android.content.ContentValues
import com.example.concesionariosapp.DBHelper
import com.example.myapplication1.models.Vehiculo

class VehiculoRepository(private val dbHelper: DBHelper) {

    // Agregar un vehículo
    fun addVehiculo(vehiculo: Vehiculo): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_MARCA, vehiculo.marca)
            put(DBHelper.COLUMN_MODELO, vehiculo.modelo)
            put(DBHelper.COLUMN_ANIO, vehiculo.anio)
            put(DBHelper.COLUMN_PRECIO, vehiculo.precio)
            put(DBHelper.COLUMN_CONCESIONARIO_ID, vehiculo.concesionarioId)
        }

        return db.insert(DBHelper.TABLE_VEHICULOS, null, values)
    }

    // Obtener vehículos por concesionario
    fun getVehiculosPorConcesionario(concesionarioId: Long): List<Vehiculo> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DBHelper.TABLE_VEHICULOS,
            null,
            "${DBHelper.COLUMN_CONCESIONARIO_ID} = ?",
            arrayOf(concesionarioId.toString()),
            null,
            null,
            null
        )
        val vehiculos = mutableListOf<Vehiculo>()
        while (cursor.moveToNext()) {
            val vehiculo = Vehiculo(
                id = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID)),
                marca = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_MARCA)),
                modelo = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_MODELO)),
                anio = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ANIO)),
                precio = cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMN_PRECIO)),
                concesionarioId = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_CONCESIONARIO_ID))
            )
            vehiculos.add(vehiculo)
        }
        cursor.close()
        return vehiculos
    }
}