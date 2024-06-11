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
import org.andresartiga.bean.DetalleFactura;
import org.andresartiga.bean.Factura;
import org.andresartiga.bean.Productos;
import org.andresartiga.db.Conexion;
import org.andresartiga.system.Principal;

/**
 *
 * @author andre
 */
public class MenuDetalleFacturaController implements Initializable{
    
    private Principal escenarioPrincipal;
    private ObservableList <DetalleFactura> listaDetalleF;
    private ObservableList <Factura> listaF;
    private ObservableList <Productos> listaP;
        private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
        
    @FXML
    private Button btnAgregarDf;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnEliminarDf;

    @FXML
    private Button btnEditarDf;

    @FXML
    private Button btnReportesDf;

    @FXML
    private TableView tblDetalleF;

    @FXML
    private TableColumn colCodDf;

    @FXML
    private TableColumn ColPuDf;

    @FXML
    private TableColumn colCaDf;

    @FXML
    private TableColumn colFaDf;

    @FXML
    private TableColumn colPoDf;

    @FXML
    private TextField txtCodDf;

    @FXML
    private TextField txtCDf;

    @FXML
    private ComboBox cmbFaDf;

    @FXML
    private ComboBox cmbPoDf;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReportes;
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
       cmbFaDf.setItems(getFactura());
       cmbPoDf.setItems(getProductos());
    }
    
        public void cargarDatos(){
        tblDetalleF.setItems(getDetalleFactura());
        colCodDf.setCellValueFactory(new PropertyValueFactory<>("idDetalleFa"));
        ColPuDf.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colCaDf.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colFaDf.setCellValueFactory(new PropertyValueFactory<>("numeroFac"));
        colPoDf.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
    }
        
        public void seleccionar(){
        txtCodDf.setText(String.valueOf(((DetalleFactura) tblDetalleF.getSelectionModel().getSelectedItem()).getIdDetalleFa()));
        txtCDf.setText(String.valueOf((((DetalleFactura) tblDetalleF.getSelectionModel().getSelectedItem()).getCantidad())));
        cmbFaDf.getSelectionModel().select(buscarFactura(((DetalleFactura) tblDetalleF.getSelectionModel().getSelectedItem()).getNumeroFac()));
        cmbPoDf.getSelectionModel().select(buscarProducto(((DetalleFactura) tblDetalleF.getSelectionModel().getSelectedItem()).getCodigoProducto()));
    }
        
        public ObservableList<DetalleFactura> getDetalleFactura(){
        ArrayList<DetalleFactura> lista = new ArrayList <DetalleFactura>();
        ResultSet resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarDetalleFactura();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleFactura(resultado.getInt("idDetalleFa"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("numeroFac"),
                        resultado.getInt("codigoProducto")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return listaDetalleF = FXCollections.observableList(lista);    
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
        
        public Factura buscarFactura (int numeroFactura ){
        Factura resultado = null;
        try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_buscarFactura(?)");
         procedimiento.setInt(1, numeroFactura);
         ResultSet registro = procedimiento.executeQuery();
         while (registro.next()){
             resultado = new Factura(registro.getInt("numeroFactura"),
                                            registro.getString("estado"),
                                            registro.getDouble("totalFactura"),
                                            registro.getString("fechaFactura"),
                                            registro.getInt("codigoCliente"),
                                            registro.getInt("idEmpleado")
             );
         }
        }catch (Exception e)
        {
            e.printStackTrace();
        }    
    
        return resultado;
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
        
        public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarDf.setText("Guardar");
                btnEliminarDf.setText("Cancelar");
                btnEditarDf.setDisable(true);
                btnReportesDf.setDisable(true);
                imgAgregar.setImage((new Image("/org/andresartiga/images/ImagenGuardar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenCancelar.png")));
                
                tipoDeOperaciones = MenuDetalleFacturaController.operaciones.ACTUALIZAR;
                break;

            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarDf.setText("Agregar");
                btnEliminarDf.setText("Eliminar");
                btnEditarDf.setDisable(false);
                btnReportesDf.setDisable(false);
                imgAgregar.setImage(new Image("/org/andresartiga/images/ImagenAgregar.png"));
                imgEliminar.setImage(new Image("/org/andresartiga/images/ImagenEliminar.png"));
                cargarDatos();
                tipoDeOperaciones = MenuDetalleFacturaController.operaciones.NINGUNO;
                break;
                
        }
    }
        
    public void guardar(){
        DetalleFactura registro = new DetalleFactura();
        registro.setIdDetalleFa(Integer.parseInt(txtCodDf.getText()));
        registro.setCantidad(Integer.parseInt(txtCDf.getText()));
        registro.setNumeroFac((((Factura)cmbFaDf.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        registro.setCodigoProducto(((Productos)cmbPoDf.getSelectionModel().getSelectedItem()).getCodigoProducto());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarDetalleFactura(?,?,?,?);");
            procedimiento.setInt(1, registro.getIdDetalleFa());
            procedimiento.setInt(2, registro.getCantidad());
            procedimiento.setInt(3, registro.getNumeroFac());
            procedimiento.setInt(4, registro.getCodigoProducto());
            procedimiento.execute();
            listaDetalleF.add(registro);

      }catch(Exception e){
            e.printStackTrace();
        }
    }
    
              public void eliminar() {

        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarDf.setText("Agregar");
                btnEliminarDf.setText("Eliminar");
                btnEditarDf.setDisable(false);
                btnReportesDf.setDisable(false);
                imgAgregar.setImage((new Image("/org/andresartiga/images/ImagenAgregar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenEliminar.png")));
                tipoDeOperaciones = MenuDetalleFacturaController.operaciones.NINGUNO;
                break;
            default: 
                if(tblDetalleF.getSelectionModel().getSelectedItem()  != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estas seguro?", "Eliminar Detalle", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta  == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarDetalleFactura(?);");
                            procedimiento.setInt(1, ((DetalleFactura)tblDetalleF.getSelectionModel().getSelectedItem()).getIdDetalleFa());
                            procedimiento.execute();
                            listaDetalleF.remove(tblDetalleF.getSelectionModel().getSelectedItem());
                            limpiarControles(); 
                        }catch(Exception e){
                            e.printStackTrace();
                        } 
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Detalle para eliminar");
                }
                break;
                
        }
    }
    
        public void activarControles(){
        txtCodDf.setEditable(true);
        txtCDf.setEditable(true);
        cmbFaDf.setDisable(false);
        cmbPoDf.setDisable(false);
    }
    
        public void desactivarControles(){
        txtCodDf.setEditable(false);
        txtCDf.setEditable(false);
        cmbFaDf.setDisable(true);
        cmbPoDf.setDisable(true);
    }
    
        public void limpiarControles(){
        txtCodDf.clear();
        txtCDf.clear();
        cmbFaDf.getSelectionModel().getSelectedItem();
        cmbPoDf.getSelectionModel().getSelectedItem();
    }
        
           public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnHome){
            escenarioPrincipal.menuPrincipalView();
        }

}
}
