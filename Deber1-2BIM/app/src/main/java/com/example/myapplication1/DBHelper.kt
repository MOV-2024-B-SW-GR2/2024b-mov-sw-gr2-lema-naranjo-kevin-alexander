package com.example.concesionariosapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Definimos la clase para la base de datos
class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        // Crear tabla Concesionarios
        val createConcesionariosTable = """
            CREATE TABLE $TABLE_CONCESIONARIOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT,
                $COLUMN_DIRECCION TEXT,
                $COLUMN_TELEFONO TEXT,
                $COLUMN_ES_FRANQUICIA INTEGER
            )
        """.trimIndent()

        // Crear tabla Vehículos
        val createVehiculosTable = """
            CREATE TABLE $TABLE_VEHICULOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MARCA TEXT,
                $COLUMN_MODELO TEXT,
                $COLUMN_ANIO INTEGER,
                $COLUMN_PRECIO REAL,
                $COLUMN_CONCESIONARIO_ID INTEGER,
                FOREIGN KEY($COLUMN_CONCESIONARIO_ID) REFERENCES $TABLE_CONCESIONARIOS($COLUMN_ID)
            )
        """.trimIndent()

        db?.execSQL(createConcesionariosTable)
        db?.execSQL(createVehiculosTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CONCESIONARIOS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_VEHICULOS")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "ConcesionariosDB"
        const val DATABASE_VERSION = 1

        const val TABLE_CONCESIONARIOS = "concesionarios"
        const val TABLE_VEHICULOS = "vehiculos"

        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_DIRECCION = "direccion"
        const val COLUMN_TELEFONO = "telefono"
        const val COLUMN_ES_FRANQUICIA = "es_franquicia"

        const val COLUMN_MARCA = "marca"
        const val COLUMN_MODELO = "modelo"
        const val COLUMN_ANIO = "anio"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_CONCESIONARIO_ID = "concesionario_id"
    }
}
