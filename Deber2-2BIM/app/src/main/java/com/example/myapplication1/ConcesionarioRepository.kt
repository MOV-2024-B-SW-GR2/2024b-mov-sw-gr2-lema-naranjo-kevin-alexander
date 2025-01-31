package com.example.myapplication1

import android.content.ContentValues
import com.example.concesionariosapp.DBHelper
import com.example.myapplication1.models.Concesionario

class ConcesionarioRepository(private val dbHelper: DBHelper) {

    // Agregar un concesionario
    fun addConcesionario(concesionario: Concesionario): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_NOMBRE, concesionario.nombre)
            put(DBHelper.COLUMN_DIRECCION, concesionario.direccion)
            put(DBHelper.COLUMN_TELEFONO, concesionario.telefono)
            put(DBHelper.COLUMN_ES_FRANQUICIA, if (concesionario.esFranquicia) 1 else 0)
        }

        return db.insert(DBHelper.TABLE_CONCESIONARIOS, null, values)
    }

    // Editar un concesionario
    fun updateConcesionario(concesionario: Concesionario): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_NOMBRE, concesionario.nombre)
            put(DBHelper.COLUMN_DIRECCION, concesionario.direccion)
            put(DBHelper.COLUMN_TELEFONO, concesionario.telefono)
            put(DBHelper.COLUMN_ES_FRANQUICIA, if (concesionario.esFranquicia) 1 else 0)
        }
        return db.update(DBHelper.TABLE_CONCESIONARIOS, values, "${DBHelper.COLUMN_ID} = ?", arrayOf(concesionario.id.toString()))
    }

    // Eliminar un concesionario
    fun deleteConcesionario(concesionarioId: Long): Int {
        val db = dbHelper.writableDatabase
        return db.delete(DBHelper.TABLE_CONCESIONARIOS, "${DBHelper.COLUMN_ID} = ?", arrayOf(concesionarioId.toString()))
    }

    // Obtener todos los concesionarios
    fun getAllConcesionarios(): List<Concesionario> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(DBHelper.TABLE_CONCESIONARIOS, null, null, null, null, null, null)
        val concesionarios = mutableListOf<Concesionario>()
        while (cursor.moveToNext()) {
            val concesionario = Concesionario(
                id = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID)),
                nombre = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NOMBRE)),
                direccion = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DIRECCION)),
                telefono = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TELEFONO)),
                esFranquicia = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ES_FRANQUICIA)) == 1
            )
            concesionarios.add(concesionario)
        }
        cursor.close()
        return concesionarios
    }
}