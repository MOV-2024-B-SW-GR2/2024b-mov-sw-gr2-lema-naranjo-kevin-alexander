package org.example.data

import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Serializable
data class Concesionario(
    val id: Int,                           // Tipo: Int
    var nombre: String,                    // Tipo: String
    var direccion: String,                 // Tipo: String
    var telefono: String,                  // Tipo: String
    var esFranquicia: Boolean,             // Tipo: Boolean
    @Serializable(with = LocalDateSerializer::class) val fechaApertura: LocalDate,            // Tipo: Date
    val vehiculos: MutableList<Vehiculo> = mutableListOf()  // Relación UNO a MUCHOS
)
object LocalDateSerializer : KSerializer<LocalDate> {
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")  // Definir el formato de la fecha

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(formatter))  // Serializa como cadena con el formato
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), formatter)  // Deserializa la cadena a LocalDate
    }
}
