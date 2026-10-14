package edu.unev.barberia.servicio;
import edu.unev.barberia.modelo.*;
import java.nio.file.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
/** Repositorio local de cuentas; permite usar el registro después de reiniciar. */
public class ServicioUsuarios {
 private final Path archivo; private final List<Usuario> usuarios=new ArrayList<>();
 public ServicioUsuarios(Path archivo)throws IOException {
  this.archivo=archivo;
  if(Files.exists(archivo)) for(String linea:Files.readAllLines(archivo,StandardCharsets.UTF_8)) {
   if(linea.isBlank())continue;
   try{String[] c=Arrays.stream(linea.split("\\t",-1)).map(v->new String(Base64.getDecoder().decode(v),StandardCharsets.UTF_8)).toArray(String[]::new);usuarios.add(Usuario.restaurar(c));}
   catch(RuntimeException ex){throw new IOException("El archivo de usuarios está dañado. No se sobrescribió.",ex);}
  }
  if(usuarios.isEmpty()) registrar("Administrador","Demo","admin","admin@barberia.local","Admin123!","Admin123!",Rol.ADMINISTRADOR);
 }
 public Usuario registrar(String nombre,String apellido,String usuario,String correo,String clave,String confirmacion,Rol rol)throws IOException {
  if(rol==null)throw new IllegalArgumentException("Seleccione un rol.");
  if(clave==null||!clave.equals(confirmacion))throw new IllegalArgumentException("Las contraseñas no coinciden.");
  Usuario nuevo=new Usuario(nombre,apellido,usuario,correo,clave,rol);
  if(usuarios.stream().anyMatch(u->u.getUsuario().equals(nuevo.getUsuario())))throw new IllegalArgumentException("Ese usuario ya existe.");
  if(usuarios.stream().anyMatch(u->u.getCorreo().equals(nuevo.getCorreo())))throw new IllegalArgumentException("Ese correo ya está registrado.");
  usuarios.add(nuevo);try{guardar();}catch(IOException ex){usuarios.remove(nuevo);throw ex;}return nuevo;
 }
 private void guardar()throws IOException {
  Path destino=archivo.toAbsolutePath();Files.createDirectories(destino.getParent());Path temporal=Files.createTempFile(destino.getParent(),"usuarios-",".tmp");
  try{List<String> lineas=usuarios.stream().map(u->String.join("\t",Arrays.stream(u.datosPersistentes()).map(v->Base64.getEncoder().encodeToString(v.getBytes(StandardCharsets.UTF_8))).toList())).toList();Files.write(temporal,lineas,StandardCharsets.UTF_8);try{Files.move(temporal,destino,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);}catch(AtomicMoveNotSupportedException ex){Files.move(temporal,destino,StandardCopyOption.REPLACE_EXISTING);}}finally{Files.deleteIfExists(temporal);}
 }
 public Optional<Usuario> autenticar(String nombre,String clave){if(nombre==null||nombre.isBlank()||clave==null||clave.isBlank())throw new IllegalArgumentException("Complete usuario y contraseña.");return usuarios.stream().filter(u->u.getUsuario().equals(nombre.trim().toLowerCase(Locale.ROOT))&&u.verificarClave(clave)).findFirst();}
}
