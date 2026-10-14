package edu.unev.barberia.modelo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
public class Pago {
 private final int id; private final Cita cita; private final BigDecimal monto; private final LocalDateTime fecha;
 public Pago(int id,Cita cita,BigDecimal monto){this.id=id;this.cita=Objects.requireNonNull(cita);if(cita.getEstado()!=Cita.Estado.COMPLETADA)throw new IllegalArgumentException("Complete la cita antes de cobrar.");if(monto==null||monto.compareTo(cita.getPrecioAcordado())!=0)throw new IllegalArgumentException("El monto debe coincidir con el precio acordado.");this.monto=monto;fecha=LocalDateTime.now();}
 public int getId(){return id;} public Cita getCita(){return cita;} public BigDecimal getMonto(){return monto;} public LocalDateTime getFecha(){return fecha;}
}
