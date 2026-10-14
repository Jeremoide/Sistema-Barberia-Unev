package edu.unev.barberia.controlador;
import edu.unev.barberia.App;
import edu.unev.barberia.modelo.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
public class PanelController {
 @FXML private Label bienvenida,mensaje,total;@FXML private ComboBox<Cliente> cliente;@FXML private ComboBox<Empleado> empleado;@FXML private ComboBox<Servicio> servicio;@FXML private DatePicker fecha;@FXML private TextField hora;
 @FXML private TableView<Cita> tabla;@FXML private TableColumn<Cita,String> colCliente,colEmpleado,colServicio,colFecha,colEstado;
 @FXML private void initialize(){bienvenida.setText("Acceso correcto · "+App.sesion().getNombreCompleto()+" · "+App.sesion().getRol());cliente.getItems().setAll(App.barberia().getClientes());empleado.getItems().setAll(App.barberia().getEmpleados());servicio.getItems().setAll(App.barberia().getServicios());fecha.setValue(LocalDate.now().plusDays(1));hora.setText("09:00");colCliente.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getCliente().getNombre()));colEmpleado.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getEmpleado().toString()));colServicio.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getServicio().getNombre()));colFecha.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));colEstado.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getEstado().name()));actualizar();}
 @FXML private void agendar(){try{if(fecha.getValue()==null)throw new IllegalArgumentException("Seleccione la fecha.");LocalTime tiempo;try{tiempo=LocalTime.parse(hora.getText().trim(),DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(java.time.format.ResolverStyle.STRICT));}catch(java.time.format.DateTimeParseException ex){throw new IllegalArgumentException("Hora inválida. Use HH:mm, por ejemplo 09:30.");}App.barberia().agendar(cliente.getValue(),empleado.getValue(),servicio.getValue(),LocalDateTime.of(fecha.getValue(),tiempo));actualizar();mensaje.setText("Cita agendada correctamente.");}catch(IllegalArgumentException ex){mensaje.setText(ex.getMessage());}}
 private Cita seleccion(){Cita cita=tabla.getSelectionModel().getSelectedItem();if(cita==null)throw new IllegalArgumentException("Seleccione una cita en la tabla.");return cita;}
 @FXML private void completar(){try{seleccion().completar();actualizar();mensaje.setText("Servicio completado. Puede registrar el pago.");}catch(IllegalArgumentException|IllegalStateException ex){mensaje.setText(ex.getMessage());}}
 @FXML private void cancelar(){try{seleccion().cancelar();actualizar();mensaje.setText("Cita cancelada.");}catch(IllegalArgumentException|IllegalStateException ex){mensaje.setText(ex.getMessage());}}
 @FXML private void cobrar(){try{Pago pago=App.barberia().cobrar(seleccion());actualizar();mensaje.setText("Pago registrado: RD$ "+pago.getMonto());}catch(IllegalArgumentException ex){mensaje.setText(ex.getMessage());}}
 @FXML private void cerrarSesion(){try{App.setSesion(null);App.mostrar("login");}catch(IOException ex){mensaje.setText("No se pudo abrir el Login.");}}
 private void actualizar(){tabla.getItems().setAll(App.barberia().getCitas());tabla.refresh();total.setText("Total cobrado: RD$ "+App.barberia().getTotalCobrado());}
}
