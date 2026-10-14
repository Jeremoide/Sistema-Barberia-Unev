# Guion del video y lista de entrega

## Video sugerido de 7 a 9 minutos

| Tiempo aproximado | Qué mostrar y explicar |
|---|---|
| 0:00 - 0:45 | Presentarte con tu nombre completo y matrícula, y presentar Barbería UNEV. |
| 0:45 - 1:30 | Problema, solución, usuarios y funciones; usar DESCRIPCION_SISTEMA.md. |
| 1:30 - 2:30 | VS Code, `java -version`, `javac -version`, `mvn -version` y extensiones obligatorias. |
| 2:30 - 3:15 | Abrir login.fxml en Scene Builder y mostrar los controles, fx:id y eventos. |
| 3:15 - 4:00 | Explicar pom.xml, JavaFX y el uso de Codex para proponer y verificar validaciones. |
| 4:00 - 5:15 | Mostrar las seis entidades, atributos privados, constructores y las relaciones de Cita y Pago. |
| 5:15 - 6:15 | Ejecutar `mvn test` y `mvn javafx:run`; probar Login vacío, contraseña incorrecta y acceso correcto. |
| 6:15 - 7:15 | Registro: error de correo, contraseñas diferentes, guardar datos válidos, limpiar y cancelar; ingresar con la nueva cuenta. |
| 7:15 - 8:15 | Programar una cita, demostrar cruce de horarios, completar servicio y registrar pago. |
| 8:15 - 9:00 | Mostrar el repositorio, explicar límites de esta versión y evolución a MariaDB, web y móvil. |

Practicar antes de grabar y explicar con tus palabras. El documento exige comprender todo el código utilizado. No se ha grabado ni publicado un video en esta sesión.

## Evidencias de entrega

- [x] Descripción del sistema: `docs/DESCRIPCION_SISTEMA.md`.
- [x] Código fuente Java y mínimo cinco clases relacionadas.
- [x] Login y Registro funcionales.
- [x] Archivos FXML y controladores.
- [x] README con ejecución y alcance.
- [x] Pruebas automatizadas y capturas de la aplicación.
- [ ] Capturas reales de VS Code, extensiones y Scene Builder.
- [ ] Repositorio de GitHub con el código publicado.
- [ ] Video demostrativo de 5 a 10 minutos y enlace.
- [x] Elegir servidor: MariaDB, por decisión del estudiante el 13 de octubre de 2026.
- [ ] Activar MariaDB y comprobar una consulta. Ver `docs/MARIADB.md`.
- [ ] Indicar en la entrega que se eligió MariaDB como alternativa al MySQL Server mencionado en el enunciado.

## Publicación en GitHub

Cuenta verificada: Jeremoide. Falta elegir el repositorio de destino. No hay repositorio publicado ni enlace inventado.

Crear un repositorio vacío en GitHub, subir los archivos del proyecto (excluyendo `datos/` y `target/`) y comprobar que estén incluidos README, pom.xml, src y docs. Esta carpeta tiene un directorio `.git` reservado y de solo lectura del entorno; no se inicializó ni se alteró. Para usar Git local se puede trabajar desde una copia normal del proyecto fuera de ese directorio reservado.

## Capturas pendientes

Tomar capturas del editor con el proyecto abierto, `java -version` y `mvn -version` en terminal, Extensions mostrando Java, JavaFX y el asistente de IA, y Scene Builder con el formulario abierto. Guardarlas junto a las imágenes de Login, Registro y agenda en `docs/evidencias`.
