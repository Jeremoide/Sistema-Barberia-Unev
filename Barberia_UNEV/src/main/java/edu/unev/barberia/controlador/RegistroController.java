package edu.unev.barberia.controlador;
import edu.unev.barberia.App;
import edu.unev.barberia.modelo.Rol;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
public class RegistroController {
 @FXML private TextField nombre,apellido,usuario,correo;@FXML private PasswordField clave,confirmacion;@FXML private ComboBox<Rol> rol;@FXML private Label mensaje;
 @FXML private void initialize(){rol.getItems().setAll(Rol.values());}
 @FXML private void guardar(){try{App.usuarios().registrar(nombre.getText(),apellido.getText(),usuario.getText(),correo.getText(),clave.getText(),confirmacion.getText(),rol.getValue());limpiar();mensaje.setText("Registro guardado. Ya puede iniciar sesión con su nueva cuenta.");}catch(IllegalArgumentException ex){mensaje.setText(ex.getMessage());}catch(IOException ex){mensaje.setText("No se pudo guardar la cuenta en disco.");}}
 @FXML private void limpiar(){nombre.clear();apellido.clear();usuario.clear();correo.clear();clave.clear();confirmacion.clear();rol.getSelectionModel().clearSelection();mensaje.setText("");}
 @FXML private void cancelar(){try{App.mostrar("login");}catch(IOException ex){mensaje.setText("No se pudo abrir el Login.");}}
}
