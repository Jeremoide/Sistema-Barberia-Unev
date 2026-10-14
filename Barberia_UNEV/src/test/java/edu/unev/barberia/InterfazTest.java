package edu.unev.barberia;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
/** Prueba real de FXML. Ejecutar con BARBERIA_UI=true en una sesión gráfica. */
@EnabledIfEnvironmentVariable(named="BARBERIA_UI",matches="true")
class InterfazTest {
 @TempDir Path datos;
 @Test void flujoLoginRegistroAgenda()throws Exception {
  CountDownLatch inicio=new CountDownLatch(1);Platform.startup(inicio::countDown);assertTrue(inicio.await(10,TimeUnit.SECONDS));
  FutureTask<Void> tarea=new FutureTask<>(()->{
   System.setProperty("barberia.datos",datos.toString());Stage stage=new Stage();new App().start(stage);
   try {
    Parent login=stage.getScene().getRoot();button(login,"Iniciar sesión").fire();assertTrue(((Label)login.lookup("#mensaje")).getText().contains("Complete"));
    ((TextField)login.lookup("#usuario")).setText("admin");((PasswordField)login.lookup("#clave")).setText("incorrecta");button(login,"Iniciar sesión").fire();assertTrue(((Label)login.lookup("#mensaje")).getText().contains("incorrectos"));
    ((PasswordField)login.lookup("#clave")).setText("Admin123!");captura(stage,"login");button(login,"Crear cuenta").fire();
    Parent registro=stage.getScene().getRoot();button(registro,"Guardar").fire();assertFalse(((Label)registro.lookup("#mensaje")).getText().isBlank());
    ((TextField)registro.lookup("#nombre")).setText("Prueba");button(registro,"Limpiar").fire();assertEquals("",((TextField)registro.lookup("#nombre")).getText());
    ((TextField)registro.lookup("#nombre")).setText("Jeremy");((TextField)registro.lookup("#apellido")).setText("Prueba");((TextField)registro.lookup("#usuario")).setText("jeremy");((TextField)registro.lookup("#correo")).setText("jeremy@example.com");((PasswordField)registro.lookup("#clave")).setText("Registro123!");((PasswordField)registro.lookup("#confirmacion")).setText("Registro123!");
    ((ComboBox<?>)registro.lookup("#rol")).getSelectionModel().select(1);button(registro,"Guardar").fire();assertTrue(((Label)registro.lookup("#mensaje")).getText().contains("Registro guardado"));
    assertTrue(App.usuarios().autenticar("jeremy","Registro123!").isPresent());captura(stage,"registro");button(registro,"Cancelar").fire();
    login=stage.getScene().getRoot();((TextField)login.lookup("#usuario")).setText("admin");((PasswordField)login.lookup("#clave")).setText("Admin123!");button(login,"Iniciar sesión").fire();
    Parent panel=stage.getScene().getRoot();assertTrue(((Label)panel.lookup("#bienvenida")).getText().contains("Acceso correcto"));
    for(String id:new String[]{"cliente","empleado","servicio"})((ComboBox<?>)panel.lookup("#"+id)).getSelectionModel().selectFirst();
    button(panel,"Agendar cita").fire();assertEquals(1,((TableView<?>)panel.lookup("#tabla")).getItems().size());
    ((TableView<?>)panel.lookup("#tabla")).getSelectionModel().selectFirst();button(panel,"Completar servicio").fire();
    ((TableView<?>)panel.lookup("#tabla")).getSelectionModel().selectFirst();button(panel,"Registrar pago").fire();assertTrue(((Label)panel.lookup("#total")).getText().contains("350"));captura(stage,"agenda");
    button(panel,"Cerrar sesión").fire();assertNull(App.sesion());
   } finally {stage.close();Platform.exit();System.clearProperty("barberia.datos");}
   return null;
  });Platform.runLater(tarea);tarea.get(30,TimeUnit.SECONDS);
 }
 private static Button button(Parent root,String text){return root.lookupAll(".button").stream().filter(n->n instanceof Button b&&b.getText().equals(text)).map(n->(Button)n).findFirst().orElseThrow();}
 private static void captura(Stage stage,String nombre)throws Exception {
  stage.getScene().getRoot().applyCss();stage.getScene().getRoot().layout();WritableImage img=stage.getScene().snapshot(null);BufferedImage out=new BufferedImage((int)img.getWidth(),(int)img.getHeight(),BufferedImage.TYPE_INT_ARGB);
  for(int y=0;y<out.getHeight();y++)for(int x=0;x<out.getWidth();x++)out.setRGB(x,y,img.getPixelReader().getArgb(x,y));
  Path dir=Path.of("docs","evidencias");Files.createDirectories(dir);ImageIO.write(out,"png",dir.resolve(nombre+".png").toFile());
 }
}
