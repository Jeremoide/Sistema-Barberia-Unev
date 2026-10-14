package edu.unev.barberia.servicio;
import edu.unev.barberia.modelo.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.*;
/** Reglas de negocio independientes de JavaFX para reutilizar en web y móvil. */
public class ServicioBarberia {
 private final List<Cliente> clientes=new ArrayList<>();private final List<Empleado> empleados=new ArrayList<>();private final List<Servicio> servicios=new ArrayList<>();private final List<Cita> citas=new ArrayList<>();private final List<Pago> pagos=new ArrayList<>();
 public ServicioBarberia(){clientes.add(new Cliente(1,"Cliente de ejemplo","809-555-0100"));empleados.add(new Empleado(1,new Usuario("Carlos","Demo","barbero","barbero@barberia.local","Demo123!",Rol.BARBERO),"Corte y barba"));servicios.add(new Servicio(1,"Corte clásico",new BigDecimal("350.00"),30));servicios.add(new Servicio(2,"Corte y barba",new BigDecimal("550.00"),45));}
 public List<Cliente> getClientes(){return List.copyOf(clientes);}public List<Empleado> getEmpleados(){return List.copyOf(empleados);}public List<Servicio> getServicios(){return List.copyOf(servicios);}public List<Cita> getCitas(){return List.copyOf(citas);}public List<Pago> getPagos(){return List.copyOf(pagos);}
 public Cita agendar(Cliente cliente,Empleado empleado,Servicio servicio,LocalDateTime fecha){
  if(!clientes.contains(cliente)||!empleados.contains(empleado)||!servicios.contains(servicio))throw new IllegalArgumentException("Seleccione cliente, empleado y servicio del catálogo.");
  if(fecha==null||!fecha.isAfter(LocalDateTime.now()))throw new IllegalArgumentException("Seleccione una fecha y hora futuras.");
  LocalDateTime fin=fecha.plusMinutes(servicio.getDuracionMinutos());
  if(citas.stream().anyMatch(c->c.getEstado()!=Cita.Estado.CANCELADA&&(c.getEmpleado()==empleado||c.getCliente()==cliente)&&fecha.isBefore(c.getFin())&&fin.isAfter(c.getFecha())))throw new IllegalArgumentException("El cliente o barbero ya tiene una cita en ese horario.");
  Cita cita=new Cita(citas.size()+1,cliente,empleado,servicio,fecha);citas.add(cita);return cita;
 }
 public Pago cobrar(Cita cita){if(!citas.contains(cita))throw new IllegalArgumentException("Seleccione una cita del sistema.");if(pagos.stream().anyMatch(p->p.getCita()==cita))throw new IllegalArgumentException("La cita ya fue pagada.");Pago pago=new Pago(pagos.size()+1,cita,cita.getPrecioAcordado());pagos.add(pago);return pago;}
 public BigDecimal getTotalCobrado(){return pagos.stream().map(Pago::getMonto).reduce(BigDecimal.ZERO,BigDecimal::add);}
}
