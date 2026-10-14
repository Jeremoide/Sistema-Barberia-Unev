# MariaDB para el sistema de barbería

Servidor elegido por el estudiante el 13 de octubre de 2026: MariaDB. Se conserva la instalación existente sin sustituir paquetes por MySQL Server.

## Estado comprobado

- Cliente y servidor instalados: MariaDB 11.8.8.
- Servicio: `inactive`.
- Inicio automático: `disabled`.
- La aplicación JavaFX todavía no utiliza una base de datos: cuentas en archivo local y datos de negocio en memoria.

## Preparar y comprobar

Ejecutar en una terminal del equipo:

```bash
sudo systemctl enable --now mariadb
systemctl is-active mariadb
systemctl is-enabled mariadb
```

Los resultados esperados son `active` y `enabled`. Si el inicio falla, revisar `systemctl status mariadb` antes de continuar.

Para comprobar la conexión local administrativa:

```bash
sudo mariadb -e "SELECT VERSION() AS version_servidor;"
```

Este acceso depende de la configuración de autenticación existente. Si solicita una contraseña o informa acceso denegado, usar las credenciales ya configuradas; no restablecerlas ni cambiar la autenticación para sortear el error.

Guardar capturas del servicio activo y del resultado de la consulta en `docs/evidencias`.

## Alcance

No hace falta crear tablas ni conectar Java a MariaDB para demostrar Login y Registro en esta versión. En una etapa posterior se diseñará el esquema y un usuario propio de la aplicación. No se utilizará la cuenta root desde Java.

En la entrega indicar: “Servidor elegido: MariaDB”. El enunciado menciona MySQL Server; no se presume que el docente haya autorizado la alternativa.
