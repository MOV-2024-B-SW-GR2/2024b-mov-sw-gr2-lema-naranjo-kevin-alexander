package org.example

import org.example.data.Concesionario
import org.example.data.Vehiculo
import org.example.repository.ConcesionarioRepository
import org.example.repository.VehiculoRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main() {
    val concesionarioRepo = ConcesionarioRepository()

    while (true) {
        println(
            """
            ***********************************************
            ***           Menú Principal               ***
            ***********************************************
            1. CRUD Concesionario
            2. CRUD Vehículo
            3. Salir
            ***********************************************
        """.trimIndent()
        )

        try {
            when (readln().toInt()) {
                1 -> menuConcesionario(concesionarioRepo)
                2 -> {
                    println("Ingrese el ID del concesionario para gestionar sus vehículos:")
                    val concesionarioId = readln().toInt()
                    val concesionario = concesionarioRepo.buscarPorId(concesionarioId)
                    if (concesionario != null) {
                        val vehiculoRepo = VehiculoRepository(concesionarioRepo) // Pasamos el repositorio completo
                        menuVehiculo(vehiculoRepo, concesionarioId)
                    } else {
                        println("Concesionario no encontrado.")
                    }
                }
                3 -> {
                    println("Saliendo del programa.")
                    return
                }
                else -> println("Opción no válida.")
            }
        } catch (e: NumberFormatException) {
            println("Por favor, ingrese un número válido.")
        }
    }
}

fun menuVehiculo(vehiculoRepo: VehiculoRepository, concesionarioId: Int) {
    var seguirEnMenuVehiculo = true // Cambiar 'val' a 'var'
    while (seguirEnMenuVehiculo) {
        println(
            """
            ***********************************************
            ***           Menú Vehículo                ***
            ***********************************************
            1. Crear
            2. Leer
            3. Actualizar
            4. Eliminar
            5. Volver al menú principal
            ***********************************************
        """.trimIndent()
        )

        try {
            when (readln().toInt()) {
                1 -> {
                    println("Ingrese los datos del vehículo (ID, marca, modelo, año, precio, kilometraje, es nuevo, color):")
                    val vehiculo = Vehiculo(
                        id = readln().toInt(),
                        marca = readln(),
                        modelo = readln(),
                        anio = readln().toInt(),
                        precio = readln().toDouble(),
                        kilometraje = readln().toInt(),
                        esNuevo = readln().toBoolean(),
                        color = readln()
                    )
                    vehiculoRepo.crear(concesionarioId, vehiculo)
                }
                2 -> {
                    val vehiculos = vehiculoRepo.leer(concesionarioId)
                    vehiculos?.forEach { println(it) }
                }
                3 -> {
                    println("Ingrese el ID del vehículo a actualizar:")
                    val vehiculoId = readln().toInt()
                    println("""
                        ¿Qué deseas actualizar del vehículo?
                        1. Precio
                        2. Kilometraje
                        3. Es nuevo
                        4. Color
                    """.trimIndent())
                    val opcion = readln().toInt()

                    when (opcion) {
                        1 -> {
                            println("Ingrese el nuevo precio:")
                            val nuevoPrecio = readln().toDouble()
                            vehiculoRepo.actualizar(concesionarioId, vehiculoId, nuevoPrecio = nuevoPrecio)
                        }
                        2 -> {
                            println("Ingrese el nuevo kilometraje:")
                            val nuevoKilometraje = readln().toInt()
                            vehiculoRepo.actualizar(concesionarioId, vehiculoId, nuevoKilometraje = nuevoKilometraje)
                        }
                        3 -> {
                            println("¿Es nuevo? (true/false):")
                            val esNuevo = readln().toBoolean()
                            vehiculoRepo.actualizar(concesionarioId, vehiculoId, esNuevo = esNuevo)
                        }
                        4 -> {
                            println("Ingrese el nuevo color:")
                            val nuevoColor = readln()
                            vehiculoRepo.actualizar(concesionarioId, vehiculoId, nuevoColor = nuevoColor)
                        }
                        else -> println("Opción no válida.")
                    }
                }
                4 -> {
                    println("Ingrese el ID del vehículo a eliminar:")
                    val vehiculoId = readln().toInt()
                    vehiculoRepo.eliminar(concesionarioId, vehiculoId)
                }
                5 -> {
                    // El usuario selecciona salir del menú de vehículos
                    seguirEnMenuVehiculo = false
                    println("Volviendo al menú principal...")
                }
                else -> println("Opción no válida.")
            }
        } catch (e: NumberFormatException) {
            println("Por favor, ingrese un número válido.")
        }
    }
}

// Luego en el menú de concesionario, aseguramos que 'val' sea cambiado por 'var':
fun menuConcesionario(concesionarioRepo: ConcesionarioRepository) {
    var seguirEnMenuConcesionario = true // Cambiar 'val' a 'var'
    while (seguirEnMenuConcesionario) {
        println(
            """
            ***********************************************
            ***           Menú Concesionario            ***
            ***********************************************
            1. Crear
            2. Leer
            3. Actualizar
            4. Eliminar
            5. Volver al menú principal
            ***********************************************
        """.trimIndent()
        )

        try {
            when (readln().toInt()) {
                1 -> {
                    println("Ingrese los datos del concesionario (ID, nombre, dirección, teléfono, franquicia, fecha de apertura):")

                    // Solicitar la fecha de apertura
                    println("Ingrese la fecha de apertura (formato dd/MM/yyyy):")
                    val fechaStr = readln()
                    val fechaApertura = try {
                        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        LocalDate.parse(fechaStr, formatter) // Convertimos el String en LocalDate
                    } catch (e: Exception) {
                        println("Formato de fecha incorrecto. Usaremos la fecha actual.")
                        LocalDate.now() // Si la fecha es incorrecta, usamos la fecha actual por defecto
                    }

                    // Crear el concesionario
                    val concesionario = Concesionario(
                        id = readln().toInt(),
                        nombre = readln(),
                        direccion = readln(),
                        telefono = readln(),
                        esFranquicia = readln().toBoolean(),
                        fechaApertura = fechaApertura
                    )
                    concesionarioRepo.crear(concesionario)
                }
                2 -> concesionarioRepo.leer().forEach { println(it) }
                3 -> {
                    println("Ingrese el ID del concesionario a actualizar:")
                    val id = readln().toInt()

                    // Menú para seleccionar qué atributo se desea modificar
                    println("""
                        ¿Qué deseas actualizar del concesionario?
                        1. Nombre
                        2. Dirección
                        3. Teléfono
                    """.trimIndent())
                    val opcion = readln().toInt()

                    when (opcion) {
                        1 -> {
                            println("Ingrese el nuevo nombre:")
                            val nuevoNombre = readln()
                            concesionarioRepo.actualizar(id) {
                                it.nombre = nuevoNombre
                            }
                        }
                        2 -> {
                            println("Ingrese la nueva dirección:")
                            val nuevaDireccion = readln()
                            concesionarioRepo.actualizar(id) {
                                it.direccion = nuevaDireccion
                            }
                        }
                        3 -> {
                            println("Ingrese el nuevo teléfono:")
                            val nuevoTelefono = readln()
                            concesionarioRepo.actualizar(id) {
                                it.telefono = nuevoTelefono
                            }
                        }
                        else -> println("Opción no válida.")
                    }
                }
                4 -> {
                    println("Ingrese el ID del concesionario a eliminar:")
                    val id = readln().toInt()
                    concesionarioRepo.eliminar(id)
                }
                5 -> {
                    // El usuario selecciona salir del menú de concesionarios
                    seguirEnMenuConcesionario = false
                    println("Volviendo al menú principal...")
                }
                else -> println("Opción no válida.")
            }
        } catch (e: NumberFormatException) {
            println("Por favor, ingrese un número válido.")
        }
    }
}