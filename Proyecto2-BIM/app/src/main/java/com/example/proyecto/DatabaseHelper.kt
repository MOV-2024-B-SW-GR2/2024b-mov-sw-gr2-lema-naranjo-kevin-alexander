package com.example.proyecto

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.SQLException
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mi_base_de_datos.db"
        private const val DATABASE_VERSION = 7// Asegúrate de incrementar la versión de la base de datos
        private const val TABLE_VEHICLES = "vehicles"
        private const val COLUMN_ID = "id"
        private const val COLUMN_MARCA = "marca"
        private const val COLUMN_MODELO = "modelo"
        private const val COLUMN_ANIO = "anio"
        private const val COLUMN_IMAGE_PATH = "imagePath"
        private const val COLUMN_PLACA = "placa"  // Nueva columna placa
        private const val COLUMN_FECHA_COMPRA = "fechaCompra"  // Nueva columna fechaCompra
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_VEHICLE_TABLE = "CREATE TABLE $TABLE_VEHICLES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_MARCA TEXT," +
                "$COLUMN_MODELO TEXT," +
                "$COLUMN_ANIO INTEGER," +
                "$COLUMN_IMAGE_PATH TEXT," +
                "$COLUMN_PLACA TEXT," +
                "$COLUMN_FECHA_COMPRA TEXT)"

        val CREATE_REPAIRS_TABLE = "CREATE TABLE repairs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "vehicle_id INTEGER," +
                "fecha TEXT," +
                "tipo TEXT," +
                "repuestos TEXT," +
                "precio REAL," +
                "observacion TEXT," +
                "FOREIGN KEY(vehicle_id) REFERENCES $TABLE_VEHICLES($COLUMN_ID))"


        val CREATE_REPUESTOS_TABLE = "CREATE TABLE repuestos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "price REAL," +
                "availability TEXT," +
                "imageResId INTEGER)"

        db?.execSQL(CREATE_VEHICLE_TABLE)
        db?.execSQL(CREATE_REPAIRS_TABLE)
        db?.execSQL(CREATE_REPUESTOS_TABLE)

        insertarRepuestosIniciales(db)
    }
    /*
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 24) {  // Si la versión es menor a 5, actualiza la tabla
            db?.execSQL("ALTER TABLE $TABLE_VEHICLES ADD COLUMN $COLUMN_PLACA TEXT")
            db?.execSQL("ALTER TABLE $TABLE_VEHICLES ADD COLUMN $COLUMN_FECHA_COMPRA TEXT")
        }
    }
*/fun insertarRepuestosIniciales(db: SQLiteDatabase?) {
        val values = listOf(
            ContentValues().apply {
                put("name", "Filtro de aceite")
                put("price", 15.99)
                put("availability", "Disponible")
                put("imageResId", R.drawable.repuesto1)
            },
            ContentValues().apply {
                put("name", "Bujía")
                put("price", 5.50)
                put("availability", "Disponible")
                put("imageResId", R.drawable.repuesto1)
            },
            ContentValues().apply {
                put("name", "Pastillas de freno")
                put("price", 25.00)
                put("availability", "Agotado")
                put("imageResId", R.drawable.repuesto1)
            },
            ContentValues().apply {
                put("name", "Aceite de motor")
                put("price", 30.00)
                put("availability", "Disponible")
                put("imageResId", R.drawable.repuesto1)
            }
        )

        for (value in values) {
            Log.d("DB_TEST", "Insertandooooooooooo: ${value.getAsString("name")}, Imagen ID: ${value.getAsInteger("imageResId")}")
            db?.insert("repuestos", null, value)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < DATABASE_VERSION) {
            db?.execSQL("DROP TABLE IF EXISTS repairs")
            db?.execSQL("DROP TABLE IF EXISTS repuestos")
            onCreate(db)
        }
    }
    // Método para agregar un vehículo
    fun agregarVehiculo(vehicle: Vehicle): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MARCA, vehicle.marca)
            put(COLUMN_MODELO, vehicle.modelo)
            put(COLUMN_ANIO, vehicle.anio)
            put(COLUMN_IMAGE_PATH, vehicle.imagePath)
            put(COLUMN_PLACA, vehicle.placa)  // Agregar placa
            put(COLUMN_FECHA_COMPRA, vehicle.fechaCompra)  // Agregar fechaCompra
        }
        return db.insert(TABLE_VEHICLES, null, values)
    }

    // Método para obtener todos los vehículos
    fun obtenerVehiculos(): List<Vehicle> {
        val vehicleList = mutableListOf<Vehicle>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_VEHICLES", null)

        val columnIndexId = cursor.getColumnIndex(COLUMN_ID)
        val columnIndexMarca = cursor.getColumnIndex(COLUMN_MARCA)
        val columnIndexModelo = cursor.getColumnIndex(COLUMN_MODELO)
        val columnIndexAnio = cursor.getColumnIndex(COLUMN_ANIO)
        val columnIndexImagePath = cursor.getColumnIndex(COLUMN_IMAGE_PATH)
        val columnIndexPlaca = cursor.getColumnIndex(COLUMN_PLACA)
        val columnIndexFechaCompra = cursor.getColumnIndex(COLUMN_FECHA_COMPRA)

        if (cursor.moveToFirst()) {
            do {
                val vehicle = Vehicle(
                    id = cursor.getInt(columnIndexId),
                    marca = cursor.getString(columnIndexMarca) ?: "",  // Si es null, asigna un valor vacío
                    modelo = cursor.getString(columnIndexModelo) ?: "",  // Si es null, asigna un valor vacío
                    anio = cursor.getInt(columnIndexAnio),
                    imagePath = cursor.getString(columnIndexImagePath) ?: "",  // Si es null, asigna un valor vacío
                    placa = cursor.getString(columnIndexPlaca) ?: "",  // Si es null, asigna un valor vacío
                    fechaCompra = cursor.getString(columnIndexFechaCompra) ?: ""  // Si es null, asigna un valor vacío
                )
                vehicleList.add(vehicle)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return vehicleList
    }

    // Método para obtener un vehículo por ID
    fun obtenerVehiculoPorId(id: Int): Vehicle? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_VEHICLES WHERE $COLUMN_ID = ?", arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(COLUMN_ID)
            val marcaIndex = cursor.getColumnIndex(COLUMN_MARCA)
            val modeloIndex = cursor.getColumnIndex(COLUMN_MODELO)
            val anioIndex = cursor.getColumnIndex(COLUMN_ANIO)
            val imagePathIndex = cursor.getColumnIndex(COLUMN_IMAGE_PATH)
            val placaIndex = cursor.getColumnIndex(COLUMN_PLACA)
            val fechaCompraIndex = cursor.getColumnIndex(COLUMN_FECHA_COMPRA)

            if (idIndex == -1 || marcaIndex == -1 || modeloIndex == -1 || anioIndex == -1 || imagePathIndex == -1 || placaIndex == -1 || fechaCompraIndex == -1) {
                cursor.close()
                return null
            }

            val vehicle = Vehicle(
                id = cursor.getInt(idIndex),
                marca = cursor.getString(marcaIndex),
                modelo = cursor.getString(modeloIndex),
                anio = cursor.getInt(anioIndex),
                imagePath = cursor.getString(imagePathIndex),
                placa = cursor.getString(placaIndex),
                fechaCompra = cursor.getString(fechaCompraIndex)
            )

            cursor.close()
            return vehicle
        }

        cursor.close()
        return null
    }
    // Método para eliminar un vehículo por ID
    fun eliminarVehiculo(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_VEHICLES, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    // Método para actualizar un vehículo existente
    fun actualizarVehiculo(vehicle: Vehicle): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MARCA, vehicle.marca)
            put(COLUMN_MODELO, vehicle.modelo)
            put(COLUMN_ANIO, vehicle.anio)
            put(COLUMN_IMAGE_PATH, vehicle.imagePath)
            put(COLUMN_PLACA, vehicle.placa)
            put(COLUMN_FECHA_COMPRA, vehicle.fechaCompra)
        }

        // Actualiza el vehículo en la base de datos donde el ID coincide
        return db.update(TABLE_VEHICLES, values, "$COLUMN_ID = ?", arrayOf(vehicle.id.toString()))
    }
    fun eliminarTodosLosVehiculos() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_VEHICLES")
    }

    fun agregarReparacion(reparacion: Reparacion): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("vehicle_id", reparacion.vehicleId)
            put("fecha", reparacion.fecha)
            put("tipo", reparacion.tipo)
            put("repuestos", reparacion.repuestos)
            put("precio", reparacion.precio)
            put("observacion", reparacion.observacion)
        }
        return db.insert("repairs", null, values)
    }

    fun obtenerReparacionesPorVehiculo(vehicleId: Int): List<Reparacion> {
        val reparacionList = mutableListOf<Reparacion>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM repairs WHERE vehicle_id = ?", arrayOf(vehicleId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val idIndex = cursor.getColumnIndex("id")
                val vehicleIdIndex = cursor.getColumnIndex("vehicle_id")
                val fechaIndex = cursor.getColumnIndex("fecha")
                val tipoIndex = cursor.getColumnIndex("tipo")
                val repuestosIndex = cursor.getColumnIndex("repuestos")
                val precioIndex = cursor.getColumnIndex("precio")
                val observacionIndex = cursor.getColumnIndex("observacion")

                // Validación para evitar -1
                if (idIndex != -1 && vehicleIdIndex != -1 && fechaIndex != -1 &&
                    tipoIndex != -1 && repuestosIndex != -1 && precioIndex != -1 && observacionIndex != -1) {

                    val reparacion = Reparacion(
                        id = cursor.getInt(idIndex),
                        vehicleId = cursor.getInt(vehicleIdIndex),
                        fecha = cursor.getString(fechaIndex),
                        tipo = cursor.getString(tipoIndex),
                        repuestos = cursor.getString(repuestosIndex),
                        precio = cursor.getDouble(precioIndex),
                        observacion = cursor.getString(observacionIndex)
                    )
                    reparacionList.add(reparacion)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return reparacionList
    }

    // Método corregido para obtener repuestos
    fun obtenerRepuestos(): List<Repuesto> {
        val repuestosList = mutableListOf<Repuesto>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT imageResId, name, price, availability FROM repuestos", null)

        try {
            if (cursor.moveToFirst()) {
                do {
                    val imageResId = cursor.getInt(cursor.getColumnIndexOrThrow("imageResId"))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                    val price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"))
                    val availability = cursor.getString(cursor.getColumnIndexOrThrow("availability"))

                    Log.d("DB_TEST", "Cargando repuesto: $name, Image ID: $imageResId")

                    val repuesto = Repuesto(imageResId, name, price, availability)
                    repuestosList.add(repuesto)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor.close()
            db.close()
        }

        return repuestosList
    }


    fun obtenerTodosLosRepuestos(): List<String> {
        val repuestosList = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT name FROM Repuestos", null) // Suponiendo que "nombre" es la columna del nombre del repuesto

        if (cursor.moveToFirst()) {
            do {
                repuestosList.add(cursor.getString(0)) // Obtiene el valor de la columna "nombre"
            } while (cursor.moveToNext())
        }
        cursor.close()

        return repuestosList
    }



}
