# Historias de Usuario para la Mecánica

---

## **HU01: Registrar empleados**

**Como** administrador de la mecánica,  
**quiero** controlar la información de mis empleados,  
**para** establecer métricas de mejora y realizar análisis acerca de los gastos en sueldos.

### Criterios de Aceptación:
- **Escenario: Registrar un empleado exitosamente**  
  **Dado** que tengo los datos del empleado:  
  Nombre: "Pepe García", Identificación: "1234567890", Rol: "Mecánico", Contacto: "0999999999"  
  **cuando** ingreso estos datos en el sistema  
  **entonces** el sistema guarda al empleado y muestra "Empleado registrado exitosamente".

- **Escenario: Intentar registrar un empleado con identificación duplicada**  
  **Dado** que la identificación "1234567890" ya está registrada  
  **cuando** intento registrar un empleado con esa identificación  
  **entonces** el sistema muestra un error: "La identificación ya está registrada".

- **Escenario: Editar los datos de un empleado existente**  
  **Dado** que selecciono al empleado "Pepe García" y cambio el contacto a "0888888888"  
  **cuando** realizo la modificación en el sistema  
  **entonces** el sistema actualiza los datos y muestra "Empleado actualizado correctamente".

- **Escenario: Eliminar un empleado**  
  **Dado** que selecciono al empleado "Pepe García" y elijo la opción "Eliminar"  
  **cuando** ejecuto la eliminación  
  **entonces** el sistema elimina al empleado y muestra "Empleado eliminado exitosamente".

---

## **HU02: Registrar reparaciones**

**Como** mecánico,  
**quiero** conocer las reparaciones realizadas a un automóvil,  
**para** mantener un historial de las reparaciones previas y poder identificar problemas recurrentes.

### Criterios de Aceptación:
- **Escenario: Registrar una reparación con datos válidos**  
  **Dado** que tengo los datos de la reparación:  
  Fecha: "2024-12-04", Descripción: "Cambio de aceite", Repuestos usados: "Filtro de aceite, Aceite 5W30", Costo: "40.00", Automóvil: "ABC1234"  
  **cuando** registro esta reparación en el sistema  
  **entonces** el sistema guarda la reparación y muestra "Reparación registrada exitosamente".

---

## **HU03: Registrar clientes**

**Como** empleado de la mecánica,  
**quiero** registrar a mis clientes teniendo su información a la mano,  
**para** generar relaciones de confianza y ofrecer un servicio más personalizado que incremente la satisfacción.

### Criterios de Aceptación:
- **Escenario: Registrar un cliente con información completa**  
  **Dado** que tengo los datos del cliente:  
  Nombre: "Juan Pérez", Contacto: "0987654321", Correo: "juan.perez@example.com"  
  **cuando** ingreso estos datos en el sistema  
  **entonces** el sistema guarda al cliente y muestra "Cliente registrado exitosamente".

- **Escenario: Personalización en el servicio**  
  **Dado** que tengo los registros previos del cliente "Juan Pérez", que tiene varias reparaciones realizadas en el pasado  
  **cuando** el cliente solicita el servicio  
  **entonces** el sistema recomienda un mantenimiento preventivo basado en su historial, mejorando la experiencia.

---

## **HU04: Registrar automóviles**

**Como** empleado de la mecánica,  
**quiero** gestionar los vehículos de los clientes,  
**para** ofrecer servicios más rápidos y eficientes basados en las necesidades individuales de cada automóvil.

### Criterios de Aceptación:
- **Escenario: Registrar un automóvil para un cliente con opciones personalizadas**  
  **Dado** que tengo los datos del automóvil:  
  Matrícula: "ABC1234", Marca: "Toyota", Modelo: "Corolla", Año: "2018", Color: "Blanco", Cliente: "Juan Pérez"  
  **cuando** ingreso estos datos en el sistema  
  **entonces** el sistema guarda el automóvil y muestra "Automóvil registrado exitosamente".

- **Escenario: Personalización de servicios para un automóvil**  
  **Dado** que el automóvil "ABC1234" pertenece al cliente "Juan Pérez" y ha tenido múltiples reparaciones de frenos  
  **cuando** se solicita una nueva reparación  
  **entonces** el sistema recomienda cambiar los frenos antes de realizar cualquier otra reparación para optimizar la seguridad y el rendimiento del vehículo.

---

## **HU05: Gestionar inventario de repuestos**

**Como** administrador de la mecánica,  
**quiero** gestionar el inventario de repuestos de forma dinámica,  
**para** optimizar el suministro de los mismos y evitar retrasos en los trabajos, mejorando la satisfacción de los clientes.

### Criterios de Aceptación:
- **Escenario: Registrar un nuevo repuesto con análisis de demanda**  
  **Dado** que tengo los datos del repuesto:  
  Nombre: "Filtro de aceite", Cantidad: "20", Precio: "15.00", Proveedor: "Repuestos ABC"  
  **cuando** ingreso estos datos en el sistema  
  **entonces** el sistema guarda el repuesto y muestra "Repuesto registrado exitosamente".

- **Escenario: Optimización del stock**  
  **Dado** que el repuesto "Filtro de aceite" tiene un stock bajo y se acerca un periodo de alta demanda (por ejemplo, una promoción de cambio de aceite),  
  **cuando** el sistema detecta esta situación,  
  **entonces** me notifica la necesidad de aumentar el stock para asegurar el abastecimiento durante la promoción y evitar faltantes que afecten el servicio al cliente.

---
