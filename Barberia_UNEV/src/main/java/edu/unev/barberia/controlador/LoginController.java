package edu.unev.barberia.controlador;
import edu.unev.barberia.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.application.Platform;
import java.io.IOException;
public class LoginController {
 @FXML private TextField usuario;@FXML private PasswordField clave;@FXML private Label mensaje;
 @FXML private void iniciar(){try{var cuenta=App.usuarios().autenticar(usuario.getText(),clave.getText());if(cuenta.isEmpty()){mensaje.setText("Usuario o contraseña incorrectos.");clave.clear();return;}App.setSesion(cuenta.get());App.mostrar("panel");}catch(IllegalArgumentException|IOException ex){mensaje.setText(ex.getMessage());}}
 @FXML private void registrar(){try{App.mostrar("registro");}catch(IOException ex){mensaje.setText("No se pudo abrir el registro.");}}
 @FXML private void salir(){Platform.exit();}
}
