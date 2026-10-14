package edu.unev.barberia;
import edu.unev.barberia.servicio.*;
import edu.unev.barberia.modelo.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.nio.file.Path;
import java.io.IOException;
public class App extends Application {
 private static Stage ventana; private static ServicioUsuarios usuarios;private static final ServicioBarberia barberia=new ServicioBarberia();private static Usuario sesion;
 @Override public void start(Stage stage)throws IOException{ventana=stage;usuarios=new ServicioUsuarios(Path.of(System.getProperty("barberia.datos", "datos"),"usuarios.tsv"));ventana.setTitle("Barbería UNEV | Gestión de citas");mostrar("login");ventana.show();}
 public static void mostrar(String vista)throws IOException{FXMLLoader loader=new FXMLLoader(App.class.getResource("/vistas/"+vista+".fxml"));Scene scene=new Scene(loader.load());scene.getStylesheets().add(App.class.getResource("/vistas/estilos.css").toExternalForm());ventana.setScene(scene);ventana.sizeToScene();ventana.centerOnScreen();}
 public static ServicioUsuarios usuarios(){return usuarios;} public static ServicioBarberia barberia(){return barberia;}public static Usuario sesion(){return sesion;}public static void setSesion(Usuario usuario){sesion=usuario;}
 public static void main(String[] args){launch(args);}
}
