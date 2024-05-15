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
import org.andresartiga.bean.Productos;
import org.andresartiga.bean.Proveedores;
import org.andresartiga.bean.TipoProducto;
import org.andresartiga.db.Conexion;
import org.andresartiga.system.Principal;

/**
 *
 * @author informatica
 */
public class MenuProductosController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {ACTUALIZAR, ELIMINAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <Productos> listaProductos;
    private ObservableList <Proveedores> listaProveedores;
    private ObservableList <TipoProducto> listaTipoProducto;
    @FXML
    private Button btnAgregar;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnReportes;
    @FXML
    private ImageView imgBuscar;
    @FXML
    private Button btnRegresar;
    @FXML
    private TableView tblProductos;
    @FXML
    private TableColumn colCodigoPro;
    @FXML
    private TableColumn colDescripcionPro;
    @FXML
    private TableColumn colPrecioUPro;
    @FXML
    private TableColumn colPrecioDoPro;
    @FXML
    private TableColumn colPrecioMPro;
    @FXML
    private TableColumn colExistenciaPro;
    @FXML
    private TableColumn colTipoProducto;
    @FXML
    private TableColumn colProveedores;
    @FXML
    private TextField txtDescripcionPro;
    @FXML
    private TextField txtPrecioUPro;
    @FXML
    private TextField txtPrecioDoPro;
    @FXML
    private TextField txtCodigoPro;
    @FXML
    private TextField txtExistenciaPro;
    @FXML
    private TextField txtPrecioMPro;
    @FXML
    private ComboBox cmbTipoProducto;
    @FXML
    private ComboBox cmbProveedores;
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cargarDatos();
       cmbTipoProducto.setItems(getTipoProducto());
       cmbProveedores.setItems(getProveedores());
    }  
    
    
    public void cargarDatos(){
        tblProductos.setItems(getProductos());
        colCodigoPro.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        colDescripcionPro.setCellValueFactory(new PropertyValueFactory<>("descripcionProducto"));
        colPrecioUPro.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colPrecioDoPro.setCellValueFactory(new PropertyValueFactory<>("precioDocena"));
        colPrecioMPro.setCellValueFactory(new PropertyValueFactory<>("precioMayor"));
        colExistenciaPro.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        colTipoProducto.setCellValueFactory(new PropertyValueFactory<>("idTipoProducto"));
        colProveedores.setCellValueFactory(new PropertyValueFactory<>("codigoProveedor"));
    }
    
    public void seleccionar(){
        txtCodigoPro.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        txtDescripcionPro.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto()));
        txtPrecioUPro.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecioDoPro.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecioMPro.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        cmbTipoProducto.getSelectionModel().select(buscarTipoProducto(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getIdTipoProducto()));
        cmbProveedores.getSelectionModel().select(this);
    }
    
        public TipoProducto buscarTipoProducto (int idTipoProducto ){
        TipoProducto resultado = null;
        try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarTipoProducto(?)}");
         procedimiento.setInt(1, idTipoProducto);
         ResultSet registro = procedimiento.executeQuery();
         while (registro.next()){
             resultado = new TipoProducto(registro.getInt("idTipoProducto"),
                                            registro.getString("descripcion")
             );
         }
        }catch (Exception e)
        {
            e.printStackTrace();
        }    
    
        return resultado;
    }
        
        public Proveedores buscarProveedor(int codigoProveedor){
            Proveedores resultado = null;
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_}")
            }catch(Exception e){
                e.printStackTrace();
            }
            return resultado;
        }
        
    public ObservableList<TipoProducto> getTipoProducto() {

        ArrayList<TipoProducto> listaTip = new ArrayList<>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarTipoProducto;");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaTip.add(new TipoProducto(resultado.getInt("idTipoProducto"),
                        resultado.getString("descripcion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaTipoProducto = FXCollections.observableList(listaTip);
    }
    
    public ObservableList<Productos> getProductos(){
    ArrayList<Productos> lista = new ArrayList<Productos>();
    try{
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProductos()}");
        ResultSet resultado = procedimiento.executeQuery();
        while(resultado.next()){
            lista.add(new Productos (resultado.getInt("codigoProducto"),
                                    resultado.getString("descripcionProducto"),
                                    resultado.getDouble("precioUnitario"),
                                    resultado.getDouble("precioDocena"),
                                    resultado.getDouble("precioMayor"),
                                    resultado.getInt("existencia"),
                                    resultado.getInt("idTipoProducto"),
                                    resultado.getInt("codigoProveedor")            
            ));
        }
    }catch (Exception e){
        e.printStackTrace();
    }
    
    
        return listaProductos = FXCollections.observableArrayList(lista);
        
    }
   
    public ObservableList<Proveedores> getProveedores() {

        ArrayList<Proveedores> listaPro = new ArrayList<>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarProveedores;");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaPro.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("nitProveedor"),
                        resultado.getString("nombreProveedor"),
                        resultado.getString("apellidoProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProveedores = FXCollections.observableList(listaPro);
    }
    
        public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage((new Image("/org/andresartiga/images/ImagenGuardar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenCancelar.png")));
                
                tipoDeOperaciones = MenuProductosController.operaciones.ACTUALIZAR;
                break;

            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/andresartiga/images/ImagenAgregar.png"));
                imgEliminar.setImage(new Image("/org/andresartiga/images/ImagenEliminar.png"));
                cargarDatos();
                tipoDeOperaciones = MenuProductosController.operaciones.NINGUNO;
                break;
                
        }
    }
        
    public void guardar(){
        Productos registro = new Productos();
        registro.setCodigoProducto(Integer.parseInt(txtCodigoPro.getText()));
        registro.setCodigoProveedor((((Proveedores)cmbProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        registro.setIdTipoProducto((((TipoProducto)cmbTipoProducto.getSelectionModel().getSelectedItem()).getIdTipoProducto()));
        registro.setDescripcionProducto(txtDescripcionPro.getText());
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioUPro.getText()));
        registro.setPrecioDocena(Double.parseDouble(txtPrecioDoPro.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtPrecioMPro.getText()));
        registro.setExistencia(Integer.parseInt(txtExistenciaPro.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarProducto(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setInt(6, registro.getExistencia());
            procedimiento.setInt(7, registro.getIdTipoProducto());
            procedimiento.setInt(8, registro.getCodigoProveedor());
            procedimiento.execute();
            
            listaProductos.add(registro);
      }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void activarControles(){
        txtCodigoPro.setEditable(true);
        txtDescripcionPro.setEditable(true);
        txtPrecioUPro.setEditable(true);
        txtPrecioDoPro.setEditable(true);
        txtPrecioMPro.setEditable(true);
        txtExistenciaPro.setEditable(true);
        cmbTipoProducto.setEditable(true);
        cmbProveedores.setEditable(true);
    }
    
    public void desactivarControles(){
        txtCodigoPro.setEditable(false);
        txtDescripcionPro.setEditable(false);
        txtPrecioUPro.setEditable(false);
        txtPrecioDoPro.setEditable(false);
        txtPrecioMPro.setEditable(false);
        txtExistenciaPro.setEditable(false);
        cmbTipoProducto.setEditable(false);
        cmbProveedores.setEditable(false);
    }
    
    public void limpiarControles(){
        txtCodigoPro.clear();
        txtDescripcionPro.clear();
        txtPrecioUPro.clear();
        txtPrecioDoPro.clear();
        txtPrecioMPro.clear();
        txtExistenciaPro.clear();
        tblProductos.getSelectionModel().getSelectedItem();
        cmbTipoProducto.getSelectionModel().getSelectedItem();
        cmbProveedores.getSelectionModel().getSelectedItem();
    }
    
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }
    }
    
      
     
     
}
