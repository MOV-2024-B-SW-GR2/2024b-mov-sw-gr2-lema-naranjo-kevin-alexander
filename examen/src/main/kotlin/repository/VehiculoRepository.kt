package org.example.repository
import org.example.data.Vehiculo
import org.example.data.Concesionario

class VehiculoRepository(private val concesionarioRepository: ConcesionarioRepository) {

    fun crear(concesionarioId: Int, vehiculo: Vehiculo) {
        val concesionario = concesionarioRepository.leer().find { it.id == concesionarioId }
        concesionario?.vehiculos?.add(vehiculo) ?: println("Concesionario no encontrado.")
        concesionarioRepository.guardarEnArchivo() // Asegura que se guarde después de agregar el vehículo
    }

    fun leer(concesionarioId: Int): List<Vehiculo>? {
        return concesionarioRepository.leer().find { it.id == concesionarioId }?.vehiculos
    }

    fun actualizar(concesionarioId: Int, vehiculoId: Int, nuevoPrecio: Double? = null, nuevoKilometraje: Int? = null, esNuevo: Boolean? = null, nuevoColor: String? = null) {
        val concesionario = concesionarioRepository.leer().find { it.id == concesionarioId }
        val vehiculo = concesionario?.vehiculos?.find { it.id == vehiculoId }
        if (vehiculo != null) {
            nuevoPrecio?.let { vehiculo.precio = it }
            nuevoKilometraje?.let { vehiculo.kilometraje = it }
            esNuevo?.let { vehiculo.esNuevo = it }
            nuevoColor?.let { vehiculo.color = it }

            concesionarioRepository.guardarEnArchivo() // Asegura que se guarde después de actualizar el vehículo
            println("Vehículo actualizado con éxito.")
        } else {
            println("Vehículo no encontrado.")
        }
    }
    fun eliminar(concesionarioId: Int, vehiculoId: Int) {
        val concesionario = concesionarioRepository.leer().find { it.id == concesionarioId }
        if (concesionario != null) {
            if (concesionario.vehiculos.removeIf { it.id == vehiculoId }) {
                concesionarioRepository.guardarEnArchivo() // Asegura que se guarde después de eliminar el vehículo
                println("Vehículo eliminado con éxito.")
            } else {
                println("Vehículo no encontrado.")
            }
        } else {
            println("Concesionario no encontrado.")
        }
    }
}