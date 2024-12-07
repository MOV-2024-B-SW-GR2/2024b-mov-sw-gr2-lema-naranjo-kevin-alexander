
package org.example.repository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.data.Concesionario
import org.example.data.Vehiculo
import java.io.File

class ConcesionarioRepository {
    private val archivo = File("concesionarios.json")
    private var concesionarios: MutableList<Concesionario> = cargarDeArchivo()

    fun crear(concesionario: Concesionario) {
        concesionarios.add(concesionario)
        guardarEnArchivo()
        println("Concesionario creado con éxito.")
    }

    fun leer(): List<Concesionario> = concesionarios

    fun actualizar(id: Int, actualizar: (Concesionario) -> Unit) {
        val concesionario = concesionarios.find { it.id == id }
        if (concesionario != null) {
            actualizar(concesionario)
            guardarEnArchivo()
            println("Concesionario actualizado con éxito.")
        } else {
            println("Concesionario no encontrado.")
        }
    }

    fun eliminar(id: Int) {
        if (concesionarios.removeIf { it.id == id }) {
            guardarEnArchivo()
            println("Concesionario eliminado con éxito.")
        } else {
            println("Concesionario no encontrado.")
        }
    }



    fun guardarEnArchivo() {
        val json = Json { prettyPrint = true }
        val jsonString = json.encodeToString(concesionarios)
        archivo.writeText(jsonString)
    }

    private fun cargarDeArchivo(): MutableList<Concesionario> {
        if (!archivo.exists()) return mutableListOf()

        val json = archivo.readText()
        return Json { ignoreUnknownKeys = true }.decodeFromString(json)
    }

    fun buscarPorId(id: Int): Concesionario? {
        return this.leer().find { it.id == id }
    }
}