package com.example.myapplication1.models

data class BVehiculo(
    val id: Int,
    var marca: String,
    var modelo: String,
    var anio: Int,
    var precio: Double,
    var kilometraje: Int,
    var esNuevo: Boolean,
    var color: String
)
