package edu.unev.barberia.modelo;
import java.util.Objects;
/** Empleado relacionado con su cuenta de acceso. */
public class Empleado {
 private final int id; private final Usuario usuario; private String especialidad;
 public Empleado(int id,Usuario usuario,String especialidad){this.id=id;this.usuario=Objects.requireNonNull(usuario);setEspecialidad(especialidad);}
 public int getId(){return id;} public Usuario getUsuario(){return usuario;} public String getEspecialidad(){return especialidad;}
 public void setEspecialidad(String v){especialidad=Usuario.obligatorio(v,"Especialidad");}
 @Override public String toString(){return usuario.getNombreCompleto();}
}
