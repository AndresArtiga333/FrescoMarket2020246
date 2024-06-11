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
import org.andresartiga.bean.Compras;
import org.andresartiga.bean.DetalleCompra;
import org.andresartiga.bean.Productos;
import org.andresartiga.db.Conexion;
import org.andresartiga.system.Principal;

/**
 *
 * @author andre
 */
public class MenuDetalleCompraController implements Initializable{
        private Principal escenarioPrincipal;
    private ObservableList <DetalleCompra> listaDetalleC;
    private ObservableList <Compras> listaC;
    private ObservableList <Productos> listaP;
        private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    
        @FXML
    private Button btnAgregarDc;

    @FXML
    private Button btnEliminarDc;

    @FXML
    private Button btnEditarDc;

    @FXML
    private Button btnReportesDc;

    @FXML
    private TableView tblDetalleC;

    @FXML
    private TableColumn colCodDC;

    @FXML
    private TableColumn ColPuDc;

    @FXML
    private TableColumn colCaDc;

    @FXML
    private TableColumn colPoC;

    @FXML
    private TableColumn colCoDc;

    @FXML
    private TextField txtCodDf;

    @FXML
    private TextField txtCDf;

    @FXML
    private ComboBox cmbPoDc;

    @FXML
    private ComboBox cmbCoDc;

    @FXML
    private TextField txtCanDc;

    @FXML
    private Button btnHome;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    
        public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
            @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
       cmbCoDc.setItems(getCompras());
       cmbPoDc.setItems(getProductos());
    }
    
        public void cargarDatos(){
        tblDetalleC.setItems(getDetalleCompra());
        colCodDC.setCellValueFactory(new PropertyValueFactory<>("idDetalleCo"));
        ColPuDc.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colCaDc.setCellValueFactory(new PropertyValueFactory<>("cantidadCo"));
        colPoC.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colCoDc.setCellValueFactory(new PropertyValueFactory<>("numDoc"));
    }
        
        public void seleccionar(){
        txtCodDf.setText(String.valueOf(((DetalleCompra) tblDetalleC.getSelectionModel().getSelectedItem()).getIdDetalleCo()));
        txtCDf.setText(String.valueOf((((DetalleCompra) tblDetalleC.getSelectionModel().getSelectedItem()).getPrecioUnitario())));
        txtCanDc.setText(String.valueOf((((DetalleCompra) tblDetalleC.getSelectionModel().getSelectedItem()).getCantidadCo())));
        cmbPoDc.getSelectionModel().select(buscarProducto(((DetalleCompra) tblDetalleC.getSelectionModel().getSelectedItem()).getIdProducto()));
        cmbCoDc.getSelectionModel().select(buscarProducto(((DetalleCompra) tblDetalleC.getSelectionModel().getSelectedItem()).getNumDoc()));
    }
        
        public void activarControles(){
        txtCodDf.setEditable(true);
        txtCDf.setEditable(true);
        txtCanDc.setEditable(true);
        cmbPoDc.setDisable(false);
        cmbCoDc.setDisable(false);
    }
        
        public void desactivarControles(){
        txtCodDf.setEditable(false);
        txtCDf.setEditable(false);
        txtCanDc.setEditable(false);
        cmbPoDc.setDisable(true);
        cmbCoDc.setDisable(true);
    }
        
        public void limpiarControles(){
        txtCodDf.clear();
        txtCDf.clear();
        txtCanDc.clear();
        cmbPoDc.getSelectionModel().getSelectedItem();
        cmbCoDc.getSelectionModel().getSelectedItem();
    }
        
        public ObservableList<DetalleCompra> getDetalleCompra(){
        ArrayList<DetalleCompra> lista = new ArrayList <DetalleCompra>();
        ResultSet resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarDetalleCompra();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleCompra(resultado.getInt("idDetalleCo"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidadCo"),
                        resultado.getInt("idProducto"),
                        resultado.getInt("numDoc")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return listaDetalleC = FXCollections.observableList(lista);    
    }
        
        public ObservableList<Productos> getProductos(){
    ArrayList<Productos> lista = new ArrayList<Productos>();
    try{
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarProductos ()");
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
    return listaP = FXCollections.observableArrayList(lista);
        }
        
        public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCompras ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getString("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaC = FXCollections.observableList(lista);
    }
    
            public Productos buscarProducto (int codigoProducto ){
        Productos resultado = null;
        try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProducto(?)}");
         procedimiento.setInt(1, codigoProducto);
         ResultSet registro = procedimiento.executeQuery();
         while (registro.next()){
             resultado = new Productos(registro.getInt("codigoProducto"),
                                            registro.getString("descripcionProducto"),
                                            registro.getDouble("precioUnitario"),
                                            registro.getDouble("precioDocena"),
                                            registro.getDouble("precioMayor"),
                                            registro.getInt("existencia"),
                                            registro.getInt("idTipoProducto"),
                                            registro.getInt("codigoProveedor")
             );
         }
        }catch (Exception e)
        {
            e.printStackTrace();
        }    
    
        return resultado;
    }
            
        public Compras buscarCompra (int numeroDocumento ){
        Compras resultado = null;
        try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCompras(?)}");
         procedimiento.setInt(1, numeroDocumento);
         ResultSet registro = procedimiento.executeQuery();
         while (registro.next()){
             resultado = new Compras(registro.getInt("numeroDocumento"),
                                            registro.getString("fechaDocumento"),
                                            registro.getString("descripcion"),
                                            registro.getDouble("totalDocumento")
             );
         }
        }catch (Exception e)
        {
            e.printStackTrace();
        }    
    
        return resultado;
    }
        
                public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarDc.setText("Guardar");
                btnEliminarDc.setText("Cancelar");
                btnEditarDc.setDisable(true);
                btnReportesDc.setDisable(true);
                imgAgregar.setImage((new Image("/org/andresartiga/images/ImagenGuardar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenCancelar.png")));
                
                tipoDeOperaciones = MenuDetalleCompraController.operaciones.ACTUALIZAR;
                break;

            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarDc.setText("Agregar");
                btnEliminarDc.setText("Eliminar");
                btnEditarDc.setDisable(false);
                btnReportesDc.setDisable(false);
                imgAgregar.setImage(new Image("/org/andresartiga/images/ImagenAgregar.png"));
                imgEliminar.setImage(new Image("/org/andresartiga/images/ImagenEliminar.png"));
                cargarDatos();
                tipoDeOperaciones = MenuDetalleCompraController.operaciones.NINGUNO;
                break;
                
        }
    }
        
    public void guardar(){
        DetalleCompra registro = new DetalleCompra();
        registro.setIdDetalleCo(Integer.parseInt(txtCodDf.getText()));
        registro.setPrecioUnitario(Integer.parseInt(txtCDf.getText()));
        registro.setCantidadCo(Integer.parseInt(txtCanDc.getText()));
        registro.setIdProducto((((Productos)cmbPoDc.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        registro.setNumDoc((((Compras)cmbCoDc.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarDetalleCompra(?,?,?,?,?);");
            procedimiento.setInt(1, registro.getIdDetalleCo());
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidadCo());
            procedimiento.setInt(4, registro.getIdProducto());
            procedimiento.setInt(5, registro.getNumDoc());
            procedimiento.execute();
            listaDetalleC.add(registro);

      }catch(Exception e){
            e.printStackTrace();
        }
    }
    
               public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnHome){
            escenarioPrincipal.menuPrincipalView();
        }
               }
}



