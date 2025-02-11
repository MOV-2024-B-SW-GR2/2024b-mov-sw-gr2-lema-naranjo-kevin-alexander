package com.example.proyecto

data class Reparacion(
    val id: Int = 0,
    val vehicleId: Int,
    val fecha: String,
    val tipo: String,
    val repuestos: String,
    val precio: Double,
    val observacion: String
)
