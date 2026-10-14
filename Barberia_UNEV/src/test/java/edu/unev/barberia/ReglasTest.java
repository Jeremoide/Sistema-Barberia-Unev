package edu.unev.barberia;
import edu.unev.barberia.modelo.*;
import edu.unev.barberia.servicio.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
class ReglasTest {
 @TempDir Path temporal;
 @Test void registroAutenticacionYPersistencia()throws Exception {
  Path archivo=temporal.resolve("usuarios.tsv");var usuarios=new ServicioUsuarios(archivo);
  assertTrue(usuarios.autenticar("admin","Admin123!").isPresent());
  usuarios.registrar("Jeremy","Prueba","jeremy","jeremy@example.com","Secreto123!","Secreto123!",Rol.RECEPCIONISTA);
  assertTrue(new ServicioUsuarios(archivo).autenticar(" JEREMY ","Secreto123!").isPresent());
  assertTrue(usuarios.autenticar("jeremy","incorrecta").isEmpty());
  assertFalse(Files.readString(archivo).contains("Secreto123!"));
 }
 @Test void rechazaDatosInvalidosYDuplicados()throws Exception {
  var u=new ServicioUsuarios(temporal.resolve("u.tsv"));
  assertThrows(IllegalArgumentException.class,()->u.autenticar("",""));
  assertThrows(IllegalArgumentException.class,()->u.registrar("","Apellido","nuevo","n@example.com","Clave123!","Clave123!",Rol.BARBERO));
  assertThrows(IllegalArgumentException.class,()->u.registrar("Nombre","Apellido","nuevo","incorrecto","Clave123!","Clave123!",Rol.BARBERO));
  assertThrows(IllegalArgumentException.class,()->u.registrar("Nombre","Apellido","nuevo","n@example.com","corta","corta",Rol.BARBERO));
  assertThrows(IllegalArgumentException.class,()->u.registrar("Nombre","Apellido","nuevo","n@example.com","Clave123!","Otra123!",Rol.BARBERO));
  assertThrows(IllegalArgumentException.class,()->u.registrar("Nombre","Apellido","admin","n@example.com","Clave123!","Clave123!",Rol.BARBERO));
  assertThrows(IllegalArgumentException.class,()->u.registrar("Nombre","Apellido","nuevo","admin@barberia.local","Clave123!","Clave123!",Rol.BARBERO));
  assertThrows(IllegalArgumentException.class,()->u.registrar("Nombre","Apellido","nuevo","n@example.com","Clave123!","Clave123!",null));
 }
 @Test void archivoDanadoNoSeSobrescribe()throws Exception {
  Path archivo=temporal.resolve("u.tsv");Files.writeString(archivo,"datos inválidos");
  assertThrows(java.io.IOException.class,()->new ServicioUsuarios(archivo));assertEquals("datos inválidos",Files.readString(archivo));
 }
 @Test void citasEvitanCrucesYPermitenHorarioContiguo(){
  var b=new ServicioBarberia();var cliente=b.getClientes().getFirst();var empleado=b.getEmpleados().getFirst();var servicio=b.getServicios().getFirst();var fecha=LocalDateTime.now().plusDays(1);
  var cita=b.agendar(cliente,empleado,servicio,fecha);
  assertThrows(IllegalArgumentException.class,()->b.agendar(cliente,empleado,servicio,fecha.plusMinutes(10)));
  assertDoesNotThrow(()->b.agendar(cliente,empleado,servicio,fecha.plusMinutes(30)));
  cita.cancelar();assertDoesNotThrow(()->b.agendar(cliente,empleado,servicio,fecha));
  assertThrows(IllegalStateException.class,cita::completar);
  assertThrows(IllegalArgumentException.class,()->b.agendar(cliente,empleado,servicio,fecha.minusDays(2)));
 }
 @Test void pagoExigeCompletarYNoSeDuplica(){
  var b=new ServicioBarberia();var servicio=b.getServicios().getFirst();var cita=b.agendar(b.getClientes().getFirst(),b.getEmpleados().getFirst(),servicio,LocalDateTime.now().plusDays(1));
  assertThrows(IllegalArgumentException.class,()->b.cobrar(cita));
  servicio.setPrecio(new BigDecimal("900.00"));cita.completar();
  assertEquals(new BigDecimal("350.00"),b.cobrar(cita).getMonto());
  assertThrows(IllegalArgumentException.class,()->b.cobrar(cita));assertThrows(IllegalStateException.class,cita::cancelar);
  assertEquals(new BigDecimal("350.00"),b.getTotalCobrado());
 }
}
