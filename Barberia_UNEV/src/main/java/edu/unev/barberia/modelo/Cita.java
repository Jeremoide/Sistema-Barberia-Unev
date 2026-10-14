package edu.unev.barberia.modelo;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Objects;
/** Une un cliente, un barbero y un servicio en una fecha determinada. */
public class Cita {
 public enum Estado { PENDIENTE, COMPLETADA, CANCELADA }
 private final int id; private final Cliente cliente; private final Empleado empleado; private final Servicio servicio;
 private final LocalDateTime fecha; private final BigDecimal precioAcordado; private final int duracionAcordada; private Estado estado=Estado.PENDIENTE;
 public Cita(int id,Cliente cliente,Empleado empleado,Servicio servicio,LocalDateTime fecha){this.id=id;this.cliente=Objects.requireNonNull(cliente);this.empleado=Objects.requireNonNull(empleado);this.servicio=Objects.requireNonNull(servicio);this.fecha=Objects.requireNonNull(fecha);precioAcordado=servicio.getPrecio();duracionAcordada=servicio.getDuracionMinutos();}
 public int getId(){return id;} public Cliente getCliente(){return cliente;} public Empleado getEmpleado(){return empleado;} public Servicio getServicio(){return servicio;}
 public LocalDateTime getFecha(){return fecha;} public LocalDateTime getFin(){return fecha.plusMinutes(duracionAcordada);} public BigDecimal getPrecioAcordado(){return precioAcordado;} public Estado getEstado(){return estado;}
 public void completar(){if(estado!=Estado.PENDIENTE)throw new IllegalStateException("La cita ya no está pendiente.");estado=Estado.COMPLETADA;}
 public void cancelar(){if(estado!=Estado.PENDIENTE)throw new IllegalStateException("Solo puede cancelar citas pendientes.");estado=Estado.CANCELADA;}
}
