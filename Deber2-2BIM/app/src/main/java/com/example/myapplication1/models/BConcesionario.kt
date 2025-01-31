package com.example.myapplication1.models

data class BConcesionario(
    val id: Int,
    var nombre: String,
    var direccion: String,
    var telefono: String,
    var esFranquicia: Boolean,
    val vehiculos: MutableList<BVehiculo>
)
