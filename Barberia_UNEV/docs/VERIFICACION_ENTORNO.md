# Verificación del entorno

Fecha de revisión: 10 de octubre de 2026. Equipo Linux Nobara/Fedora x86_64. La revisión cubre los requisitos de la Tarea No. 1 de Programación II.

| Requisito | Resultado y evidencia |
|---|---|
| Visual Studio Code | Instalado; comando `/usr/bin/code`. |
| JDK / Java | OpenJDK 25.0.4.1; `java -version` y `javac -version` funcionan. |
| Compatibilidad del JDK | Proyecto configurado para compilar con JDK 25 y `release=25`. El PDF no fija versión; confirmar con el profesor. |
| Maven | Apache Maven 3.9.11; `/usr/bin/mvn`. |
| JavaFX | Dependencias Maven JavaFX Controls y FXML 21.0.6; interfaz cargada y probada. |
| Scene Builder | Instalado mediante Flatpak: `com.gluonhq.SceneBuilder`, versión 26.0.0. |
| Git | Versión 2.55.0, `/usr/bin/git`. |
| Cuenta de GitHub | Cuenta conectada verificada mediante el conector: `Jeremoide`. |
| MySQL Server | No encontrado: `rpm -q mysql-community-server` informa que no está instalado. Hay MariaDB Server 11.8.8 y componentes cliente/bibliotecas MySQL Community 9.7.2. No equivale a comprobar MySQL Server. |
| MySQL Workbench | Instalado: paquete `mysql-workbench-26.7.0-1.x86_64`, comando `/usr/bin/mysql-workbench`. |
| Asistente de IA | Codex disponible en esta sesión; extensiones Codex, Claude y Gemini instaladas. Falta confirmar autorización del docente. |

## Extensiones obligatorias

Todas están presentes en la lista de extensiones de VS Code:

| Extensión | Identificador verificado |
|---|---|
| Extension Pack for Java | `vscjava.vscode-java-pack` |
| Maven for Java | `vscjava.vscode-maven` |
| Debugger for Java | `vscjava.vscode-java-debug` |
| Test Runner for Java | `vscjava.vscode-java-test` |
| Soporte JavaFX / FXML | `shrey150.javafx-support` |
| IA | `openai.chatgpt`, `anthropic.claude-code`, `google.geminicodeassist`, entre otras |
| Soporte del lenguaje Java | `redhat.java` |

VS Code devolvió un aviso al intentar crear su carpeta de logs dentro del entorno restringido; la lista de extensiones sí se pudo obtener y se contrastó con sus carpetas instaladas.

## Pendientes del entorno

1. Se eligió MariaDB el 13 de octubre de 2026. No es necesario sustituir el servidor instalado. Queda activarlo y comprobar una consulta; ver `docs/MARIADB.md`.
2. Abrir Scene Builder y MySQL Workbench para mostrar sus ventanas en el video. La instalación se verificó, pero no se comprobó su funcionamiento interactivo.
3. Confirmar la versión de JDK y el asistente de IA autorizados por el profesor.
4. Agregar capturas reales de VS Code y su panel de extensiones.

Actualización del 13 de octubre de 2026: `systemctl is-active mariadb` devolvió `inactive` y `systemctl is-enabled mariadb` devolvió `disabled`. El servidor está instalado pero detenido y sin inicio automático. La elección de MariaDB fue del estudiante; no se ha confirmado una autorización del docente.

## Comprobaciones reproducibles

```bash
java -version
javac -version
mvn -version
git --version
code --list-extensions
flatpak info com.gluonhq.SceneBuilder
rpm -q mysql-community-server mariadb-server mysql-workbench
```

## Pruebas del proyecto

Se ejecutaron cinco pruebas de reglas de negocio y una prueba gráfica JavaFX: seis pruebas, cero fallos y cero errores. La prueba gráfica valida campos vacíos, credenciales incorrectas, acceso correcto, registro, limpieza y cancelación, carga de los tres FXML, programación de cita y pago. Las capturas en `docs/evidencias` son imágenes de las escenas reales generadas por JavaFX, no capturas del entorno de VS Code o Scene Builder.

También se completaron el empaquetado Maven (`package`) y la ejecución mediante `javafx:run`, ambos con BUILD SUCCESS. Ver `docs/evidencias/verificacion-maven.txt` y `docs/evidencias/ejecucion-javafx.txt`.
