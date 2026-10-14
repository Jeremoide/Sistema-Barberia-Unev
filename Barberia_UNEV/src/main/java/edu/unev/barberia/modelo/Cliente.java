package edu.unev.barberia.modelo;
/** Persona que solicita los servicios de la barbería. */
public class Cliente {
 private final int id; private String nombre, telefono;
 public Cliente(int id,String nombre,String telefono){this.id=id;setNombre(nombre);setTelefono(telefono);}
 public int getId(){return id;} public String getNombre(){return nombre;} public void setNombre(String v){nombre=Usuario.obligatorio(v,"Nombre del cliente");}
 public String getTelefono(){return telefono;} public void setTelefono(String v){telefono=Usuario.obligatorio(v,"Teléfono");}
 @Override public String toString(){return nombre;}
}
