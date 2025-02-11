package com.example.proyecto
data class Vehicle(
    val id: Int,
    val marca: String,
    val modelo: String,
    val anio: Int,
    val imagePath: String,
    val placa: String,  // Nuevo atributo
    val fechaCompra: String  // Nuevo atributo
)