package org.example.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class Vehiculo(
    val id: Int,                           // Tipo: Int
    var marca: String,                     // Tipo: String
    var modelo: String,                    // Tipo: String
    var anio: Int,                         // Tipo: Int
    var precio: Double,                    // Tipo: Double
    var kilometraje: Int,                  // Tipo: Int
    var esNuevo: Boolean,                  // Tipo: Boolean
    var color: String                      // Tipo: String
)
