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
    private enum operaciones {AGREGAR, ELIMINAR, CANCELAR, NINGUNO}
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
    private TableColumn colImagenPro;
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
    private TextField txtImagenPro;
    @FXML
    private ComboBox cmbTipoProducto;
    @FXML
    private ComboBox cmbProveedores;
    
    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
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
        colImagenPro.setCellValueFactory(new PropertyValueFactory<>("imagenProducto"));
        colExistenciaPro.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        colTipoProducto.setCellValueFactory(new PropertyValueFactory<>("codigoTipoProducto"));
        colProveedores.setCellValueFactory(new PropertyValueFactory<>("codigoProveedor"));
    }
    
    
    
    public ObservableList<Productos> getProductos(){
        ArrayList<Productos> lista = new ArrayList<Productos>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarProveedores;");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Productos(resultado.getString("codigoProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getString("imagenProducto"),
                        resultado.getInt("existencia"),
                        resultado.getInt("codigoTipoProducto"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos = FXCollections.observableList(lista);
        
        
    }
    
    public ObservableList<Proveedores> getProveedores() {

        ArrayList<Proveedores> listaPro = new ArrayList<>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarProveedores;");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaPro.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
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
    
    public ObservableList<TipoProducto> getTipoProducto() {

        ArrayList<TipoProducto> listaTip = new ArrayList<>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarTipoProducto;");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaTip.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaTipoProducto = FXCollections.observableList(listaTip);
    }
    
    
    
    
    
    public void activarControlers(){
        txtCodigoPro.setEditable(true);
        txtDescripcionPro.setEditable(true);
        txtPrecioUPro.setEditable(true);
        txtPrecioDoPro.setEditable(true);
        txtPrecioMPro.setEditable(true);
        txtImagenPro.setEditable(true);
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
        txtImagenPro.setEditable(false);
        txtExistenciaPro.setEditable(false);
        cmbTipoProducto.setEditable(false);
        cmbProveedores.setEditable(false);
    }
    
    public void limpíarControles(){
        txtCodigoPro.clear();
        txtDescripcionPro.clear();
        txtPrecioUPro.clear();
        txtPrecioDoPro.clear();
        txtPrecioMPro.clear();
        txtImagenPro.clear();
        txtExistenciaPro.clear();
        tblProductos.getSelectionModel().getSelectedItem();
        cmbTipoProducto.getSelectionModel().getSelectedItem();
        cmbProveedores.getSelectionModel().getSelectedItem();
    }
    
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            escenarioPrincipal.MenuPrincipalView();
        }
    }
    
      
     
     
}
