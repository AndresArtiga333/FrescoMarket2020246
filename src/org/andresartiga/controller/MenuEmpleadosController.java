/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.andresartiga.controller;

import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.andresartiga.bean.CargoEmpleado;
import org.andresartiga.bean.Empleados;
import org.andresartiga.db.Conexion;
import org.andresartiga.system.Principal;

/**
 *
 * @author andre
 */
public class MenuEmpleadosController implements Initializable{
    
    private Principal escenarioPrincipal;
    private enum operaciones {ACTUALIZAR, ELIMINAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <Empleados> listaE;
    private ObservableList <CargoEmpleado> listaCargo;
    
     @FXML
    private TableView tblEmpleados;

    @FXML
    private TableColumn colCodigoE;

    @FXML
    private TableColumn colNombreE;

    @FXML
    private TableColumn colApellidoE;

    @FXML
    private TableColumn colSueldo;

    @FXML
    private TableColumn colDir;

    @FXML
    private TableColumn colTurno;

    @FXML
    private TableColumn colCargo;
    
    @FXML
    private Button btnCar;

    @FXML
    private Button btnAgregarCar;

    @FXML
    private ImageView imgAgregar;

    @FXML
    private Button btnEliminarCar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnEditarCar;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnReportesCar;
    
    @FXML
    private Button btnRegresar;
     
    @FXML
    private ImageView imgReportes;

    @FXML
    private TextField txtApe;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtIdE;

    @FXML
    private TextField txtTurno;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtSueldo;

    @FXML
    private ComboBox cmbCargo;
    
     public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cargarDatos();
       cmbCargo.setItems(getCargoEmpleado());
    }  
    
    public void cargarDatos(){
        tblEmpleados.setItems(getEmpleados());
        colCodigoE.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        colNombreE.setCellValueFactory(new PropertyValueFactory<>("nombresEmpleado"));
        colApellidoE.setCellValueFactory(new PropertyValueFactory<>("apellidosEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<>("sueldo"));
        colDir.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("idCargoEmpleado"));
    }
    
        public void seleccionar(){
        txtIdE.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getIdCargoEmpleado()));
        txtNom.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
        txtApe.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado()));
        txtSueldo.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        txtDireccion.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getDireccion()));
        txtTurno.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getTurno());
        cmbCargo.getSelectionModel().select(buscarCargo(((Empleados )tblEmpleados.getSelectionModel().getSelectedItem()).getIdCargoEmpleado()));
    }
        
 public ObservableList<Empleados> getEmpleados(){
        ArrayList<Empleados> lista = new ArrayList <Empleados>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarEmpleados();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("idEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("idCargoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return listaE = FXCollections.observableList(lista);    
    }

                
    public ObservableList <CargoEmpleado> getCargoEmpleado() {

        ArrayList<CargoEmpleado> listaCar = new ArrayList<>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarCargoEmpleado;");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaCar.add(new CargoEmpleado(resultado.getInt("idCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         return listaCargo = FXCollections.observableList(listaCar);
        }
    
        public CargoEmpleado buscarCargo(int idCargoEmpleado) {
            CargoEmpleado resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCargoEmpleado(?)}");
            procedimiento.setInt(1, idCargoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new CargoEmpleado(registro.getInt("idCargoEmpleado"),
                        registro.getString("nombreCargo"),
                        registro.getString("descripcionCargo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
        }
        
                public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarCar.setText("Guardar");
                btnEliminarCar.setText("Cancelar");
                btnEditarCar.setDisable(true);
                btnReportesCar.setDisable(true);
                imgAgregar.setImage((new Image("/org/andresartiga/images/ImagenGuardar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenCancelar.png")));
                
                tipoDeOperaciones = MenuEmpleadosController.operaciones.ACTUALIZAR;
                break;

            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarCar.setText("Agregar");
                btnEliminarCar.setText("Eliminar");
                btnEditarCar.setDisable(false);
                btnReportesCar.setDisable(false);
                imgAgregar.setImage(new Image("/org/andresartiga/images/ImagenAgregar.png"));
                imgEliminar.setImage(new Image("/org/andresartiga/images/ImagenEliminar.png"));
                cargarDatos();
                tipoDeOperaciones = MenuEmpleadosController.operaciones.NINGUNO;
                break;
                
        }
    }
                
      public void guardar(){
        Empleados registro = new Empleados();
        registro.setIdEmpleado(Integer.parseInt(txtIdE.getText()));
        registro.setNombresEmpleado(txtNom.getText());
        registro.setApellidosEmpleado(txtApe.getText());
        registro.setDireccion(txtDireccion.getText());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setTurno(txtTurno.getText());
        registro.setIdCargoEmpleado((((CargoEmpleado)cmbCargo.getSelectionModel().getSelectedItem()).getIdCargoEmpleado()));       
        try {

            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarEmpleado(?,?,?,?,?,?,?);");
            procedimiento.setInt(1, registro.getIdEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getIdCargoEmpleado());
            procedimiento.execute();
            listaE.add(registro);

      }catch(Exception e){
            e.printStackTrace();
        }
    }
      
      public void eliminar() {

        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarCar.setText("Agregar");
                btnEliminarCar.setText("Eliminar");
                btnEditarCar.setDisable(false);
                btnReportesCar.setDisable(false);
                imgAgregar.setImage((new Image("/org/andresartiga/images/ImagenAgregar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenEliminar.png")));
                tipoDeOperaciones = MenuEmpleadosController.operaciones.NINGUNO;
                break;
            default: 
                if(tblEmpleados.getSelectionModel().getSelectedItem()  != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estas seguro?", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta  == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarEmpleado(?);");
                            procedimiento.setInt(1, ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getIdEmpleado());
                            procedimiento.execute();
                            listaE.remove(tblEmpleados.getSelectionModel().getSelectedItem());
                            limpiarControles(); 
                        }catch(Exception e){
                            e.printStackTrace();
                        } 
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Empleado para eliminar");
                }
                break;
                
        }
    }
      
      public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
                    btnEditarCar.setText("Actualizar");
                    btnReportesCar.setText("Cancelar");
                    btnAgregarCar.setDisable(true);
                    btnEliminarCar.setDisable(true);
                 imgEditar.setImage((new Image("/org/andresartiga/images/ImagenGuardar.png")));
                  imgReportes.setImage((new Image("/org/andresartiga/images/ImagenCancelar.png")));
                    activarControles();
                    txtIdE.setEditable(false);
                    tipoDeOperaciones = MenuEmpleadosController.operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Empleado para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarCar.setText("Editar");
                btnReportesCar.setText("Reportes");
                btnAgregarCar.setDisable(false);
                btnEliminarCar.setDisable(false);
                imgEditar.setImage(new Image("/org/andresartiga/images/ImagenActualizar.png"));
                imgReportes.setImage(new Image("/org/andresartiga/images/ImagenReporte.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = MenuEmpleadosController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_actualizarEmpleado(?,?,?,?,?,?,?);");
            Empleados registro = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
            
            registro.setNombresEmpleado(txtNom.getText());
            registro.setApellidosEmpleado(txtApe.getText());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            registro.setDireccion(txtDireccion.getText());
            registro.setTurno(txtTurno.getText());
            registro.setIdCargoEmpleado(((CargoEmpleado)cmbCargo.getSelectionModel().getSelectedItem()).getIdCargoEmpleado());
            procedimiento.setInt(1, registro.getIdEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getIdCargoEmpleado());

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
                btnEditarCar.setText("Editar");
                btnReportesCar.setText("Reporte");
                btnAgregarCar.setDisable(false);
                btnEliminarCar.setDisable(false);
                imgEditar.setImage(new Image("/org/andresartiga/images/ImagenActualizar.png"));
                imgReportes.setImage(new Image("/org/andresartiga/images/ImagenReporte.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
        public void activarControles(){
        txtIdE.setEditable(true);
        txtNom.setEditable(true);
        txtApe.setEditable(true);
        txtDireccion.setEditable(true);
        txtSueldo.setEditable(true);
        txtTurno.setEditable(true);
        cmbCargo.setDisable(false);
    }
    
    public void desactivarControles(){
        txtIdE.setEditable(false);
        txtNom.setEditable(false);
        txtApe.setEditable(false);
        txtDireccion.setEditable(false);
        txtSueldo.setEditable(false);
        txtTurno.setEditable(false);
        cmbCargo.setDisable(true);
    }
    
    public void limpiarControles(){
        txtIdE.clear();
        txtNom.clear();
        txtApe.clear();
        txtDireccion.clear();
        txtSueldo.clear();
        txtTurno.clear();
        cmbCargo.getSelectionModel().getSelectedItem();
    }
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }else if(event.getSource() == btnCar){
            escenarioPrincipal.menuCargoView();
        }
    }
}


 
