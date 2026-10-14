package edu.unev.barberia.modelo;
import java.math.BigDecimal;
public class Servicio {
 private final int id; private String nombre; private BigDecimal precio; private int duracionMinutos;
 public Servicio(int id,String nombre,BigDecimal precio,int duracion){this.id=id;setNombre(nombre);setPrecio(precio);setDuracionMinutos(duracion);}
 public int getId(){return id;} public String getNombre(){return nombre;} public void setNombre(String v){nombre=Usuario.obligatorio(v,"Servicio");}
 public BigDecimal getPrecio(){return precio;} public void setPrecio(BigDecimal v){if(v==null||v.signum()<=0)throw new IllegalArgumentException("Precio debe ser positivo.");precio=v;}
 public int getDuracionMinutos(){return duracionMinutos;} public void setDuracionMinutos(int v){if(v<=0)throw new IllegalArgumentException("Duración debe ser positiva.");duracionMinutos=v;}
 @Override public String toString(){return nombre+" · RD$ "+precio;}
}
