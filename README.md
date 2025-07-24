# Barber Reservation API

¡Hola! 👋 Soy un desarrollador backend junior y este es mi proyecto para gestionar reservas en una barbería. Aquí te cuento cómo funciona y cómo puedes ponerlo en marcha si quieres probarlo, aprender o contribuir.

## ¿Qué hace este proyecto?

Esta API permite:
- Registrar usuarios y barberos
- Reservar servicios de barbería
- Confirmar y cancelar reservas
- Generar facturas en PDF automáticamente
- Enviar la factura al cliente por correo electrónico cuando se confirma la reserva

Todo está hecho con **Spring Boot** y una base de datos **MySQL**.

## Requisitos

- Java 17 o superior
- Maven
- MySQL
- (Opcional) Cuenta de Gmail o Mailtrap para pruebas de correo

## Configuración rápida

1. **Clona el repositorio**

```bash
 git clone <url-del-repo>
 cd barber-reservation-api
```

2. **Configura la base de datos**

Crea una base de datos llamada `barber_booking_db` en tu MySQL y ajusta el usuario/contraseña en `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/barber_booking_db
spring.datasource.username=root
spring.datasource.password=root
```

3. **Configura el correo**

En el mismo archivo `application.properties`, puedes usar Gmail o Mailtrap. Ejemplo para Gmail:

```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tuemail@gmail.com
spring.mail.password=tucontraseña
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

> **Consejo:** Si usas Gmail, activa el acceso a apps menos seguras o usa una contraseña de aplicación.

Para pruebas, puedes usar Mailtrap (https://mailtrap.io) y pegar los datos SMTP que te da.

4. **Instala dependencias y ejecuta**

```bash
./mvnw spring-boot:run
```

La API estará corriendo en [http://localhost:8080](http://localhost:8080)

## ¿Cómo funciona el envío de facturas?

Cuando un cliente hace una reserva, el sistema:
1. Guarda la reserva en la base de datos
2. Genera un PDF con los datos de la reserva
3. Envía ese PDF al correo del cliente automáticamente

Todo esto lo puedes ver en el servicio `ReservationService`.

## Consejos para pruebas

- Puedes usar herramientas como Postman para probar los endpoints.
- Si tienes problemas con el correo, revisa bien los datos SMTP y prueba primero con Mailtrap.
- Si algo falla, revisa la consola: los errores suelen ser claros.

## ¿Quieres contribuir o aprender más?

¡Genial! El código está comentado y trato de mantenerlo lo más claro posible. Si tienes dudas, puedes abrir un issue o contactarme.

---

## 📚 Documentación de la API

La API sigue principios REST y utiliza JSON para la comunicación. Aquí tienes los endpoints principales:

### Autenticación

#### POST /api/auth/login
Inicia sesión y obtiene un token JWT.

**Body:**
```json
{
  "email": "usuario@email.com",
  "password": "tucontraseña"
}
```
**Respuesta:**
```json
{
  "token": "jwt-token"
}
```

### Reservas

#### POST /api/reservations
Crea una nueva reserva y envía la factura por correo.

**Body:**
```json
{
  "clientId": 1,
  "barberId": 2,
  "serviceId": 3,
  "date": "2024-06-01",
  "time": "15:00"
}
```
**Respuesta:**
```json
{
  "id": 10,
  "clientName": "Juan",
  "barberName": "Pedro",
  "serviceName": "Corte de cabello",
  "date": "2024-06-01",
  "time": "15:00",
  "status": "CONFIRMED"
}
```

#### GET /api/reservations/client/{clientId}
Obtiene todas las reservas de un cliente.

#### GET /api/reservations/barber/{barberId}
Obtiene todas las reservas de un barbero.

#### POST /api/reservations/cancel/{reservationId}
Cancela una reserva.

### Servicios y Barberos

#### GET /api/services
Lista todos los servicios disponibles.

#### GET /api/barbers
Lista todos los barberos registrados.

---

> **Nota:** Todos los endpoints que modifican datos requieren autenticación JWT en el header `Authorization: Bearer <token>`.

Para más detalles, revisa los controladores en el código fuente. Si tienes dudas, ¡puedes contactarme!

---

¡Gracias por leer! Espero que este proyecto te ayude a aprender o a resolver lo que necesitas 🚀 