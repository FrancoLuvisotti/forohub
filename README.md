# Foro Hub

**Foro Hub** es un desafío de desarrollo backend cuyo objetivo es gestionar los tópicos de un foro mediante una API REST construida con **Spring Boot**.  
Permite a los usuarios crear, visualizar, actualizar y eliminar tópicos fácilmente.

## Tecnologías y herramientas utilizadas

- **Java JDK** (versión 17+)
- **Spring Boot** (versión 3+)
- **Maven** (versión 4+)
- **MySQL** (versión 8+)
- **Spring Data JPA**
- **Spring Web**
- **Spring Boot DevTools**
- **Flyway Migration**
- **MySQL Driver**
- **Validation**
- **Spring Security**
- **Lombok**
- **SpringFox Swagger** (documentación de API)
- **IDE recomendado (opcional): IntelliJ IDEA**

---

## ¿Cómo descargar o clonar el repositorio?

Puedes obtener el proyecto ejecutando el siguiente comando en tu terminal:

```bash
git clone https://github.com/FrancoLuvisotti/forohub.git
```

Luego accede a la carpeta del proyecto:

```bash
cd forohub
```

---

## Instalación y ejecución

1. **Instala las dependencias**  
   Ejecuta:
   ```bash
   ./mvnw install
   ```

2. **Configura la base de datos**  
   Asegúrate de tener MySQL instalado y funcionando. Configura los datos de conexión en el archivo `application.properties` o `application.yml` según tu entorno.

3. **Ejecuta la aplicación**  
   Inicia el servidor con:
   ```bash
   ./mvnw spring-boot:run
   ```

Por defecto, la API estará disponible en `http://localhost:8080`.

---

## Diagrama de Base de Datos

La siguiente imagen muestra el diseño de la base de datos para el proyecto, con las entidades principales y sus relaciones:

<img src="assets/diagrama.png" alt="Diagrama de base de datos" width="150"/>

### Estructura de las tablas principales

- **Tópico:** id, título, mensaje, fecha de creación, status, autor, curso, respuestas
- **Respuesta:** id, mensaje, tópico, fecha de creación, autor, solución
- **Usuario:** id, nombre, correo electrónico, contraseña, perfiles
- **Curso:** id, nombre, categoría
- **Perfil:** id, nombre

---

## Uso de la aplicación

### 1. Registro, login y obtención de token

Antes de poder crear o gestionar tópicos, primero debes registrar un usuario y luego iniciar sesión para obtener el token de autenticación.

#### Registrar usuario

Haz una petición POST a:

```
POST http://localhost:8080/usuarios
```

Con el siguiente JSON de ejemplo:

```json
{
  "nombre": "Juan Pérez",
  "email": "juan@example.com",
  "contrasena": "12345656"
}
```

#### Login y obtención de token

Luego de registrar el usuario, inicia sesión para obtener el token:

```
POST http://localhost:8080/login
```

Con el siguiente JSON:

```json
{
  "email": "juan@example.com",
  "contrasena": "12345656"
}
```

La respuesta incluirá el **token** de autenticación. Este token debes incluirlo en la cabecera Authorization para las peticiones protegidas:

```
Authorization: Bearer TU_TOKEN_AQUI
```

---

### 2. Endpoints principales para Tópicos

- `POST http://localhost:8080/topicos`: Crea un nuevo tópico.
- `GET http://localhost:8080/topicos`: Lista todos los tópicos.
- `GET http://localhost:8080/topicos/{id}`: Muestra un tópico específico.
- `PUT http://localhost:8080/topicos/{id}`: Actualiza un tópico.
- `DELETE http://localhost:8080/topicos/{id}`: Elimina un tópico.

#### Ejemplo de creación de tópico

```json
POST /topicos
{
  "titulo": "Pruebas",
  "mensaje": "¿Cómo?",
  "autorId": 1,
  "cursoId": 1
}
```

#### Ejemplo de actualización de tópico

```
PUT http://localhost:8080/topicos/3
```

```json
{
  "id": 4,
  "titulo": "Nuevo título",
  "mensaje": "Mensaje editado"
}
```

#### Ejemplo para listar todos los tópicos

```
GET http://localhost:8080/topicos
```

#### Ejemplo para obtener el detalle de un tópico específico

```
GET http://localhost:8080/topicos/2
```

#### Ejemplo para eliminar un tópico

```
DELETE http://localhost:8080/topicos/2
```

---

## Dependencias esenciales (`pom.xml`)

Si al crear el proyecto no agregaste todas las dependencias desde Spring Initializr, asegúrate de incluir las siguientes en tu archivo `pom.xml`:

```xml
<!-- Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
<!-- MySQL Driver -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<!-- Flyway Migration -->
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
</dependency>
<!-- Spring Boot DevTools -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
</dependency>
<!-- Spring Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<!-- SpringFox Swagger -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

---

## Pruebas y documentación automática con Swagger

Para facilitar el uso y la documentación de tu API, se recomienda incorporar la dependencia **SpringFox Swagger**.  
Swagger te brinda una interfaz gráfica para probar todos los endpoints y genera documentación automática a medida que desarrollas tu API.


## Próximas mejoras a implementar

1. **Implementar otras rutas en tu aplicación:**  
   Para que el foro esté completo, se recomienda agregar endpoints para crear, listar, actualizar y eliminar otras informaciones además de los tópicos, por ejemplo:
   - `/usuarios`
   - `/respuestas`
     

2. **Pruebas y documentación automática con Swagger**
    Para facilitar el uso y la documentación de la API, se incorpora la dependencia **SpringFox Swagger**.  
Swagger te brinda una interfaz gráfica para probar todos los endpoints y genera documentación automática a medida que desarrollas tu API.


3. **Respuestas a los tópicos:**  
  Implementar los endpoints y la lógica para poder crear, listar, actualizar y eliminar respuestas asociadas a los tópicos, permitiendo así la interacción completa en los hilos del foro.


4. **Paginación:**  
  Incorporar paginación en los endpoints de listado de tópicos y respuestas, para facilitar la navegación y el rendimiento cuando exista una gran cantidad de registros.

---
---

## Contribuciones

¡Las contribuciones son bienvenidas! Si deseas colaborar, abre un issue o un pull request.


## Contacto

Desarrollado por FrancoLuvisotti.  
Para dudas o sugerencias, contacta a [FrancoLuvisotti](https://github.com/FrancoLuvisotti).