import com.example.myapplication1.models.BConcesionario
import com.example.myapplication1.models.BVehiculo

class BBaseDatosMemoria {
    companion object {
        val concesionarios = mutableListOf(
            BConcesionario(
                id = 1,
                nombre = "Concesionario Quito",
                direccion = "Av. Amazonas y Naciones Unidas",
                telefono = "022345678",
                esFranquicia = true,
                vehiculos = mutableListOf(
                    BVehiculo(1, "Toyota", "Corolla", 2020, 25000.0, 15000, false, "Rojo"),
                    BVehiculo(2, "Honda", "Civic", 2019, 23000.0, 20000, false, "Negro")
                )
            ),
            BConcesionario(
                id = 2,
                nombre = "Concesionario Guayaquil",
                direccion = "Av. 9 de Octubre",
                telefono = "042345678",
                esFranquicia = false,
                vehiculos = mutableListOf(
                    BVehiculo(3, "Ford", "Focus", 2021, 28000.0, 0, true, "Azul"),
                    BVehiculo(4, "Chevrolet", "Cruze", 2020, 26000.0, 10000, false, "Blanco")
                )
            )
        )
    }
}
