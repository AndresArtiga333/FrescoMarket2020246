/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.andresartiga.bean.Clientes;
import org.andresartiga.bean.Empleados;
import org.andresartiga.bean.Factura;
import org.andresartiga.bean.Productos;
import org.andresartiga.db.Conexion;
import org.andresartiga.system.Principal;

/**
 *
 * @author andre
 */
public class MenuFacturaController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {ACTUALIZAR, ELIMINAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <Factura> listaF;
    private ObservableList <Clientes> listaC;
    private ObservableList <Empleados> listaE;
    
    @FXML
    private TableView tblFactura;

    @FXML
    private TableColumn colCodF;

    @FXML
    private TableColumn colEs;

    @FXML
    private TableColumn colTot;

    @FXML
    private TableColumn colFecha;

    @FXML
    private TableColumn colCodC;

    @FXML
    private TableColumn colE;

    @FXML
    private Button btnAgregarF;
    
    @FXML
    private Button btnRegresar;

    @FXML
    private ImageView imgAgregar;

    @FXML
    private Button btnEliminarF;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnEditarF;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnReportesF;

    @FXML
    private ImageView imgReportes;

    @FXML
    private TextField txtTot;

    @FXML
    private TextField txtEs;

    @FXML
    private TextField txtCodF;

    @FXML
    private TextField txtFe;

    @FXML
    private ComboBox cmbC;

    @FXML
    private ComboBox cmbE;
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cargarDatos();
       cmbC.setItems(getClientes());
       cmbE.setItems(getEmpleados());
    }  
    
    public void cargarDatos(){
        tblFactura.setItems(getFactura());
        colCodF.setCellValueFactory(new PropertyValueFactory<>("numeroFactura"));
        colEs.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colTot.setCellValueFactory(new PropertyValueFactory<>("totalFactura"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaFactura"));
        colCodC.setCellValueFactory(new PropertyValueFactory<>("codigoCliente"));
        colE.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
    }
    
        public void seleccionar(){
        txtCodF.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        txtEs.setText((((Factura) tblFactura.getSelectionModel().getSelectedItem()).getEstado()));
        txtTot.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getTotalFactura()));
        txtFe.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getFechaFactura()));
        cmbC.getSelectionModel().select(buscarClientes(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        cmbE.getSelectionModel().select(buscarEmpleado(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getIdEmpleado()));
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
        
        public ObservableList<Clientes> getClientes(){
        ArrayList<Clientes> lista = new ArrayList <Clientes>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarClientes();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                        resultado.getString("nitCliente"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return listaC = FXCollections.observableList(lista);    
    }

        public ObservableList<Factura> getFactura(){
        ArrayList<Factura> lista = new ArrayList <Factura>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarFactura();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Factura(resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("idEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return listaF = FXCollections.observableList(lista);    
    }

        public Clientes buscarClientes (int codigoCliente ){
        Clientes resultado = null;
        try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarClientes(?)}");
         procedimiento.setInt(1, codigoCliente);
         ResultSet registro = procedimiento.executeQuery();
         while (registro.next()){
             resultado = new Clientes(registro.getInt("codigoCliente"),
                                            registro.getString("nitCliente"),
                                            registro.getString("nombreCliente"),
                                            registro.getString("apellidoCliente"),
                                            registro.getString("direccionCliente"),
                                            registro.getString("telefonoCliente"),
                                            registro.getString("correoCliente")
             );
         }
        }catch (Exception e)
        {
            e.printStackTrace();
        }    
    
        return resultado;
    }
        
        public Empleados buscarEmpleado (int idEmpleado){
            Empleados resultado = null;
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarEmpleado(?)}");
                procedimiento.setInt(1, idEmpleado);
                ResultSet registro = procedimiento.executeQuery();
                while(registro.next()){
                    resultado = new Empleados(registro.getInt("idEmpleado"),
                                              registro.getString("nombresEmpleado"),
                                              registro.getString("apellidosEmpleado"),
                                              registro.getDouble("sueldo"),
                                              registro.getString("direccion"),
                                              registro.getString("turno"),
                                              registro.getInt("idCargoEmpleado"));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
            return resultado;
        }
        
        public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                txtTot.setEditable(false);
                btnAgregarF.setText("Guardar");
                btnEliminarF.setText("Cancelar");
                btnEditarF.setDisable(true);
                btnReportesF.setDisable(true);
                imgAgregar.setImage((new Image("/org/andresartiga/images/ImagenGuardar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenCancelar.png")));
                
                tipoDeOperaciones = MenuFacturaController.operaciones.ACTUALIZAR;
                break;

            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarF.setText("Agregar");
                btnEliminarF.setText("Eliminar");
                btnEditarF.setDisable(false);
                btnReportesF.setDisable(false);
                imgAgregar.setImage(new Image("/org/andresartiga/images/ImagenAgregar.png"));
                imgEliminar.setImage(new Image("/org/andresartiga/images/ImagenEliminar.png"));
                cargarDatos();
                tipoDeOperaciones = MenuFacturaController.operaciones.NINGUNO;
                break;
                
        }
    }
        
    public void guardar(){
        Factura registro = new Factura();
        registro.setNumeroFactura(Integer.parseInt(txtCodF.getText()));
        registro.setEstado(txtEs.getText());
        registro.setFechaFactura(txtFe.getText());
        registro.setCodigoCliente((((Clientes)cmbC.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        registro.setIdEmpleado(((Empleados)cmbE.getSelectionModel().getSelectedItem()).getIdEmpleado());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarFactura(?,?,?,?,?);");
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setString(3, registro.getFechaFactura());
            procedimiento.setInt(4, registro.getCodigoCliente());
            procedimiento.setInt(5, registro.getIdEmpleado());
            procedimiento.execute();
            listaF.add(registro);

      }catch(Exception e){
            e.printStackTrace();
        }
    }
    
          public void eliminar() {

        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarF.setText("Agregar");
                btnEliminarF.setText("Eliminar");
                btnEditarF.setDisable(false);
                btnReportesF.setDisable(false);
                imgAgregar.setImage((new Image("/org/andresartiga/images/ImagenAgregar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenEliminar.png")));
                tipoDeOperaciones = MenuFacturaController.operaciones.NINGUNO;
                break;
            default: 
                if(tblFactura.getSelectionModel().getSelectedItem()  != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estas seguro?", "Eliminar Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta  == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarFactura(?);");
                            procedimiento.setInt(1, ((Factura)tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
                            procedimiento.execute();
                            listaF.remove(tblFactura.getSelectionModel().getSelectedItem());
                            limpiarControles(); 
                        }catch(Exception e){
                            e.printStackTrace();
                        } 
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una Factura para eliminar");
                }
                break;
                
        }
    }
          public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblFactura.getSelectionModel().getSelectedItem() != null){
                    btnEditarF.setText("Actualizar");
                    btnReportesF.setText("Cancelar");
                    btnAgregarF.setDisable(true);
                    btnEliminarF.setDisable(true);
                 imgEditar.setImage((new Image("/org/andresartiga/images/ImagenGuardar.png")));
                  imgReportes.setImage((new Image("/org/andresartiga/images/ImagenCancelar.png")));
                    activarControles();
                    txtCodF.setEditable(false);
                    tipoDeOperaciones = MenuFacturaController.operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar una factura para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarF.setText("Editar");
                btnReportesF.setText("Reportes");
                btnAgregarF.setDisable(false);
                btnEliminarF.setDisable(false);
                imgEditar.setImage(new Image("/org/andresartiga/images/ImagenActualizar.png"));
                imgReportes.setImage(new Image("/org/andresartiga/images/ImagenReporte.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = MenuFacturaController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_actualizarFactura(?,?,?,?,?,?);");
            Factura registro = (Factura)tblFactura.getSelectionModel().getSelectedItem();
            
            registro.setEstado(txtEs.getText());
            registro.setTotalFactura(Double.parseDouble(txtTot.getText()));
            registro.setFechaFactura(txtFe.getText());
            registro.setCodigoCliente(((Clientes)cmbC.getSelectionModel().getSelectedItem()).getCodigoCliente());
            registro.setIdEmpleado(((Empleados)cmbE.getSelectionModel().getSelectedItem()).getIdEmpleado());
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getIdEmpleado());

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
                btnEditarF.setText("Editar");
                btnReportesF.setText("Reporte");
                btnAgregarF.setDisable(false);
                btnEliminarF.setDisable(false);
                imgEditar.setImage(new Image("/org/andresartiga/images/ImagenActualizar.png"));
                imgReportes.setImage(new Image("/org/andresartiga/images/ImagenReporte.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
    
        public void activarControles(){
        txtCodF.setEditable(true);
        txtEs.setEditable(true);
        txtTot.setEditable(true);
        txtFe.setEditable(true);
        cmbC.setDisable(false);
        cmbE.setDisable(false);
    }
    
    public void desactivarControles(){
        txtCodF.setEditable(false);
        txtEs.setEditable(false);
        txtTot.setEditable(false);
        txtFe.setEditable(false);
        cmbC.setDisable(true);
        cmbE.setDisable(true);
    }
    
    public void limpiarControles(){
        txtCodF.clear();
        txtEs.clear();
        txtTot.clear();
        txtFe.clear();
        tblFactura.getSelectionModel().getSelectedItem();
        cmbC.getSelectionModel().getSelectedItem();
        cmbE.getSelectionModel().getSelectedItem();
    }
    
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }

}
}
