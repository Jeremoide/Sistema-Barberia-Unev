# Barbería UNEV

Primera versión de escritorio para la Tarea No. 1 de Programación II. Java + JavaFX + FXML + Maven. Resuelve la organización de citas, servicios y pagos de una barbería.

## Ejecutar en Visual Studio Code

1. Abrir esta carpeta en VS Code y esperar la importación de Maven.
2. Usar JDK 25, la versión LTS más reciente. El proyecto compila para Java 25 y usa JavaFX 21.0.6. El PDF no especifica una versión concreta: confirmar con el docente si exige otra.
3. En la terminal de la carpeta ejecutar:

```bash
mvn test
mvn javafx:run
```

Maven descarga dependencias la primera vez. Para las comprobaciones de esta sesión se usó una caché temporal: `mvn -Dmaven.repo.local=/tmp/barberia-m2 test`.

**Cuenta de demostración:** usuario `admin`, contraseña `Admin123!`. Se crea al iniciar por primera vez. En “Crear cuenta” se registra un usuario que podrá iniciar sesión incluso después de reiniciar.

## Funciones implementadas

- Login con mensajes para campos vacíos, credenciales incorrectas y acceso correcto.
- Registro con nombre, apellido, usuario, correo, contraseña, confirmación y rol.
- Validación de correo, usuario, contraseña de ocho caracteres, duplicados y coincidencia de contraseñas.
- Guardar, limpiar y cancelar el formulario de Registro.
- Agenda con cliente, empleado, servicio, fecha y hora; rechaza citas pasadas y cruces de horario del cliente o barbero.
- Completar o cancelar citas; registrar un pago único y consultar el total cobrado.

## Organización y POO

`modelo`: Usuario, Cliente, Empleado, Servicio, Cita, Pago y Rol.
`servicio`: reglas de autenticación, almacenamiento de cuentas, agenda y cobros.
`controlador`: LoginController, RegistroController y PanelController.
`src/main/resources/vistas`: login.fxml, registro.fxml, panel.fxml y estilos.css.

Las clases tienen atributos privados, constructores, getters, setters donde corresponde y métodos propios. Los identificadores y relaciones no tienen setters porque no deben cambiar después de crear las entidades. Cita relaciona Cliente, Empleado y Servicio; Empleado relaciona Usuario; Pago relaciona Cita. El precio y la duración se fijan al agendar para que cambiar el catálogo no altere citas existentes.

## Scene Builder

Está instalado como Flatpak (`com.gluonhq.SceneBuilder`, versión 26.0.0). Abrirlo con:

```bash
flatpak run com.gluonhq.SceneBuilder
```

En File > Open seleccionar un FXML de `src/main/resources/vistas`. Los diseños usan controles estándar y declaran su controlador y eventos en FXML. Los archivos fueron escritos desde el código; queda pendiente abrirlos en Scene Builder y mostrar el diseñador en el video.

## Persistencia y alcance

Las cuentas se guardan en `datos/usuarios.tsv`, excluido de Git. Cada campo se codifica en Base64 para evitar separadores accidentales; Base64 no es cifrado. Las contraseñas se protegen con PBKDF2-HMAC-SHA256, sal aleatoria y 120 000 iteraciones. No se almacena la contraseña original.

Clientes, empleados, servicios, citas y pagos usan datos de muestra en memoria. Al cerrar la aplicación se pierden citas y pagos. Se utilizará MariaDB para la persistencia en una fase posterior. MariaDB Server 11.8.8 está instalado; el 13 de octubre de 2026 se comprobó que el servicio está inactivo y no está habilitado al arranque. Consultar `docs/MARIADB.md` para prepararlo. El rol es descriptivo: esta práctica permite seleccionar cualquier rol y no implementa autorización por permisos. Antes de uso real se debe restringir la creación de administradores, sustituir cuentas demo y agregar persistencia de negocio.

## Validación

`ReglasTest` verifica registro, autenticación, persistencia, datos inválidos, duplicados, archivo dañado, cruces de horarios y pagos. `InterfazTest` comprueba FXML y acciones reales de formularios, y genera capturas; requiere sesión gráfica:

```bash
BARBERIA_UI=true mvn test
```

Consultar [descripción del sistema](docs/DESCRIPCION_SISTEMA.md), [verificación del entorno](docs/VERIFICACION_ENTORNO.md) y [guion y entrega](docs/GUIA_ENTREGA.md).
