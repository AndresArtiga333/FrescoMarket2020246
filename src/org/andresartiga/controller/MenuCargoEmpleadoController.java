
package org.andresartiga.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.andresartiga.bean.CargoEmpleado;
import org.andresartiga.db.Conexion;
import org.andresartiga.system.Principal;

public class MenuCargoEmpleadoController implements Initializable {
    private Principal escenarioPrincipal;
    private ObservableList<CargoEmpleado> listaCargo;
    
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    
    @FXML
    private Button btnHomeCa;
    
    @FXML
    private Button btnAgregarCo;

    @FXML
    private ImageView imgAgregar;

    @FXML
    private Button btnEliminarCo;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnEditarCo;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnReportesCo;

    @FXML
    private ImageView imgReportes;

    @FXML
    private TableView tblCargo;

    @FXML
    private TableColumn colIdCargo;

    @FXML
    private TableColumn colNomCar;

    @FXML
    private TableColumn colDescCar;

    @FXML
    private TextField txtIdCargo;

    @FXML
    private TextField txtNomCar;

    @FXML
    private TextField txtDescCar;
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }
    
    public void cargarDatos() {
        tblCargo.setItems(getCargo());
        colIdCargo.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("idCargoEmpleado"));
        colNomCar.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("nombreCargo"));
        colDescCar.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));
    }
    
    public void seleccionar() {
        txtIdCargo.setText(String.valueOf(((CargoEmpleado) tblCargo.getSelectionModel().getSelectedItem()).getIdCargoEmpleado()));
        txtNomCar.setText(String.valueOf(((CargoEmpleado) tblCargo.getSelectionModel().getSelectedItem()).getNombreCargo()));
        txtDescCar.setText(String.valueOf(((CargoEmpleado) tblCargo.getSelectionModel().getSelectedItem()).getDescripcionCargo()));
    }
    
    public ObservableList<CargoEmpleado> getCargo() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCargoEmpleado ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("idCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCargo = FXCollections.observableList(lista);
    }
    
        public void desactivarControles() {
        txtIdCargo.setEditable(false);
        txtNomCar.setEditable(false);
        txtDescCar.setEditable(false);
    }

    public void activarControles() {
        txtIdCargo.setEditable(true);
        txtNomCar.setEditable(true);
        txtDescCar.setEditable(true);
    }

    public void limpiarControles() {
        txtIdCargo.clear();
        txtNomCar.clear();
        txtDescCar.setEditable(false);
    }
    
        public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarCo.setText("Guardar");
                btnEliminarCo.setText("Cancelar");
                btnEditarCo.setDisable(true);
                btnReportesCo.setDisable(true);
                imgAgregar.setImage((new Image("/org/andresartiga/images/ImagenGuardar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenCancelar.png")));
                
                tipoDeOperaciones = MenuCargoEmpleadoController.operaciones.ACTUALIZAR;
                break;

            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarCo.setText("Agregar");
                btnEliminarCo.setText("Eliminar");
                btnEditarCo.setDisable(false);
                btnReportesCo.setDisable(false);
                imgAgregar.setImage(new Image("/org/andresartiga/images/ImagenAgregar.png"));
                imgEliminar.setImage(new Image("/org/andresartiga/images/ImagenEliminar.png"));
                cargarDatos();
                tipoDeOperaciones = MenuCargoEmpleadoController.operaciones.NINGUNO;
                break;
                
        }
    }

    public void guardar() {
        CargoEmpleado registro = new CargoEmpleado();
        registro.setIdCargoEmpleado(Integer.parseInt(txtIdCargo.getText()));
        registro.setNombreCargo(txtNomCar.getText());
        registro.setDescripcionCargo(txtDescCar.getText());
        try {
            // Se puede usar el mismo metodo porque la base de datos es local
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCargoEmpleado(?, ?, ?)}");
            procedimiento.setInt(1, registro.getIdCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
            listaCargo.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void eliminar() {
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarCo.setText("Agregar");
                btnEliminarCo.setText("Eliminar");
                btnEditarCo.setDisable(false);
                btnReportesCo.setDisable(false);
                imgAgregar.setImage((new Image("/org/andresartiga/images/ImagenAgregar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenEliminar.png")));
                tipoDeOperaciones = MenuCargoEmpleadoController.operaciones.NINGUNO;
                break;
            default:
                if(tblCargo.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro",
                            "Eliminar Cargo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCargoEmpleado(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado)tblCargo.getSelectionModel().getSelectedItem()).getIdCargoEmpleado());
                            procedimiento.execute();
                            listaCargo.remove(tblCargo.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }   
                }else{
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Cargo para eliminar");
                }
                break;
        }
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblCargo.getSelectionModel().getSelectedItem() != null){
                btnEditarCo.setText("Actualizar");
                btnReportesCo.setText("Cancelar");
                btnAgregarCo.setDisable(true);
                btnEliminarCo.setDisable(true);
                imgEditar.setImage((new Image("/org/andresartiga/images/ImagenGuardar.png")));
                imgReportes.setImage((new Image("/org/andresartiga/images/ImagenCancelar.png")));
                activarControles();
                txtIdCargo.setEditable(false);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un tipo para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarCo.setText("Editar");
                btnReportesCo.setText("Reporte");
                btnAgregarCo.setDisable(false);
                btnEliminarCo.setDisable(false);
                imgEditar.setImage(new Image("/org/andresartiga/images/ImagenActualizar.png"));
                imgReportes.setImage(new Image("/org/andresartiga/images/ImagenReporte.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCargoEmpleado (?, ?, ?)}");
            CargoEmpleado registro = (CargoEmpleado)tblCargo.getSelectionModel().getSelectedItem();
            registro.setNombreCargo(txtNomCar.getText());
            registro.setDescripcionCargo(txtDescCar.getText());
            procedimiento.setInt(1, registro.getIdCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void reporte(){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditarCo.setText("Editar");
                btnReportesCo.setText("Reporte");
                btnAgregarCo.setDisable(false);
                btnEliminarCo.setDisable(false);
                imgEditar.setImage(new Image("/org/andresartiga/images/ImagenActualizar.png"));
                imgReportes.setImage(new Image("/org/andresartiga/images/ImagenReporte.png"));
                tipoDeOperaciones = MenuCargoEmpleadoController.operaciones.NINGUNO;
                break;
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if(event.getSource()== btnHomeCa){
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
