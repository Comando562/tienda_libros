![2024-08-1516-43-32-ezgif com-video-to-gif-converter (1)](https://github.com/user-attachments/assets/4c69f140-aff8-4810-a0c2-888a499ca51a)

# Proyecto: Tienda de Libros

Este proyecto es una aplicación web desarrollada con Spring Boot para gestionar una tienda de libros. La aplicación permite a los usuarios realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en libros, con la capacidad de agregar, editar, listar y eliminar libros desde una interfaz.

## Estructura del Proyecto

El proyecto está organizado en los siguientes módulos y componentes:

### 1. **Entidad Libro (Libro.java)**
- Contiene los atributos principales como ID, título, autor, precio y descripción. Está decorada con anotaciones de JPA para mapear la entidad con la base de datos.

### 2. **Repositorio (LibroRepositorio.java)**
- Interfaz que extiende `JpaRepository` para realizar operaciones CRUD de manera sencilla. Incluye métodos personalizados si es necesario para consultas específicas.

### 3. **Servicio (LibroServicio.java e ILibroServicio.java)**
- Define la lógica de negocio para la gestión de libros. La interfaz `ILibroServicio` define los métodos, y `LibroServicio` implementa esa lógica, encargándose de interactuar con el repositorio.

### 4. **Formulario y Validación (LibroForm.java y LibroForm.form)**
- El formulario captura los datos necesarios para crear o actualizar libros. Se aplican reglas de validación para asegurar la integridad de los datos antes de persistirlos en la base de datos.

### 5. **Configuración de la Aplicación (application.properties y logback-spring.xml)**
- En `application.properties` se definen los parámetros de configuración como la conexión a la base de datos, puerto del servidor, entre otros.
- En `logback-spring.xml` se configura el logging de la aplicación para monitorear y depurar mediante logs en la consola.

### 6. **Clase Principal (TiendaLibrosApplication.java)**
- Clase que contiene el método `main()` y sirve como punto de entrada para la ejecución de la aplicación. Está anotada con `@SpringBootApplication`.

### 7. **Pruebas (TiendaLibrosApplicationTests.java)**
- Contiene pruebas unitarias para verificar la funcionalidad del proyecto y garantizar que las operaciones CRUD se ejecutan correctamente.

## Características Principales

- **CRUD Completo:** La aplicación permite crear, leer, actualizar y eliminar libros.
- **Validación de Datos:** Se asegura que los datos ingresados sean válidos y consistentes.
- **Logging:** Configuración personalizada para manejar los logs, mostrando mensajes en la consola de manera ordenada y con distintos niveles de importancia (INFO, DEBUG, ERROR).
- **Configuración Flexible:** Parámetros como la base de datos, el puerto y otros aspectos de la configuración se manejan en el archivo `application.properties`.

## Requisitos

- Java 8 o superior
- Spring Boot 2.5+
- Maven 3.6+
- Base de datos MySQL

## Cómo Ejecutar el Proyecto

1. Clonar el repositorio.
2. Configurar la base de datos en `application.properties`.
3. Ejecutar la clase `TiendaLibrosApplication.java`.
4. Acceder a la aplicación en el navegador a través de `http://localhost:8080`.

## Mejoras Futuras

- Implementar un sistema de autenticación y roles para gestionar el acceso a las operaciones.
- Añadir paginación y búsqueda en el listado de libros.
- Integrar una capa de frontend utilizando Thymeleaf o React para mejorar la experiencia de usuario.

## 👤 Contacto
Si quieres compartir alguna observación, comentario o consulta acerca de dicho proyecto o algo relacionado a la programación, puedes escribirme a los siguientes medios: 
- [Linkedin](https://www.linkedin.com/in/leonardo562/)
- [Correo Electrónico](mailto:leo.moya562@gmail.com)
