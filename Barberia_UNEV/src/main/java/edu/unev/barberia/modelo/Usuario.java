package edu.unev.barberia.modelo;
import java.util.UUID;
import java.security.*;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;
import java.util.Locale;
import java.util.Objects;
/** Cuenta que autentica al personal. Nunca guarda la contraseña en texto plano. */
public class Usuario {
 private final String id;
 private String nombre, apellido, usuario, correo;
 private Rol rol;
 private final String sal, hash;
 public Usuario(String nombre, String apellido, String usuario, String correo, String clave, Rol rol) {
  this.id=UUID.randomUUID().toString(); setNombre(nombre); setApellido(apellido); setUsuario(usuario); setCorreo(correo); setRol(rol);
  if(clave==null || clave.isBlank() || clave.length()<8) throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
  byte[] bytes=new byte[16]; new SecureRandom().nextBytes(bytes); sal=Base64.getEncoder().encodeToString(bytes); hash=derivar(clave,sal);
 }
 private Usuario(String id,String nombre,String apellido,String usuario,String correo,Rol rol,String sal,String hash) {
  this.id=id; setNombre(nombre); setApellido(apellido); setUsuario(usuario); setCorreo(correo); setRol(rol); this.sal=sal; this.hash=hash;
 }
 public static Usuario restaurar(String[] c) { if(c.length!=8) throw new IllegalArgumentException("Cuenta incompleta."); return new Usuario(c[0],c[1],c[2],c[3],c[4],Rol.valueOf(c[5]),c[6],c[7]); }
 public String[] datosPersistentes(){return new String[]{id,nombre,apellido,usuario,correo,rol.name(),sal,hash};}
 private static String derivar(String clave,String sal) {
  PBEKeySpec spec=new PBEKeySpec(clave.toCharArray(),Base64.getDecoder().decode(sal),120000,256);
  try {return Base64.getEncoder().encodeToString(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded());}
  catch(GeneralSecurityException ex){throw new IllegalStateException("No se pudo proteger la contraseña.",ex);} finally{spec.clearPassword();}
 }
 public boolean verificarClave(String clave){return clave!=null && MessageDigest.isEqual(Base64.getDecoder().decode(hash),Base64.getDecoder().decode(derivar(clave,sal)));}
 public static String obligatorio(String valor,String campo){if(valor==null||valor.isBlank())throw new IllegalArgumentException(campo+" es obligatorio."); return valor.trim();}
 public String getId(){return id;} public String getNombre(){return nombre;} public void setNombre(String v){nombre=obligatorio(v,"Nombre");}
 public String getApellido(){return apellido;} public void setApellido(String v){apellido=obligatorio(v,"Apellido");}
 public String getUsuario(){return usuario;} public void setUsuario(String v){String valor=obligatorio(v,"Usuario").toLowerCase(Locale.ROOT); if(!valor.matches("[a-z0-9_.-]{3,30}"))throw new IllegalArgumentException("Usuario: 3 a 30 letras, números, punto, guion o guion bajo.");usuario=valor;}
 public String getCorreo(){return correo;} public void setCorreo(String v){String valor=obligatorio(v,"Correo").toLowerCase(Locale.ROOT); if(!valor.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+"))throw new IllegalArgumentException("El correo electrónico no es válido.");correo=valor;}
 public Rol getRol(){return rol;} public void setRol(Rol v){rol=Objects.requireNonNull(v,"Seleccione un rol.");}
 public String getNombreCompleto(){return nombre+" "+apellido;}
}
