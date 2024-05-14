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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.andresartiga.bean.Compras;
import org.andresartiga.db.Conexion;
import org.andresartiga.system.Principal;

/**
 *
 * @author andre
 */
public class MenuComprasController implements Initializable {
    private Principal escenarioPrincipal;
    private ObservableList<Compras> listaCompras;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    
    @FXML
    private Button btnHome;
    @FXML
    private TableView tblCompras;

    @FXML
    private TableColumn colNumDoc;

    @FXML
    private TableColumn colFechaDoc;

    @FXML
    private TableColumn colDescCo;

    @FXML
    private TableColumn colTotDoc;

    @FXML
    private Button btnAgregarCo;

    @FXML
    private ImageView imgAgregarCo;

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
    private TextField txtNumDoc;

    @FXML
    private TextField txtFechaDoc;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtTotDoc;
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
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
        return listaCompras = FXCollections.observableList(lista);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }
    
    public void cargarDatos() {
        tblCompras.setItems(getCompras());
        colNumDoc.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFechaDoc.setCellValueFactory(new PropertyValueFactory<Compras, String>("fechaDocumento"));
        colDescCo.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotDoc.setCellValueFactory(new PropertyValueFactory<Compras, String>("totalDocumento"));
    }
    
    public void seleccionar() {
        txtNumDoc.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        txtFechaDoc.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getFechaDocumento()));
        txtDesc.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion()));
        txtTotDoc.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));
    }
    
    public void desactivarControles() {
        txtNumDoc.setEditable(false);
        txtFechaDoc.setEditable(false);
        txtDesc.setEditable(false);
        txtTotDoc.setEditable(false); 
    }
    
    public void activarControles() {
        txtNumDoc.setEditable(true);
        txtFechaDoc.setEditable(true);
        txtDesc.setEditable(true);
        txtTotDoc.setEditable(true); 
    }
    
    public void limpiarControles() {
        txtNumDoc.clear();
        txtFechaDoc.clear();
        txtDesc.clear();
        txtTotDoc.clear();
    }
    
            public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarCo.setText("Guardar");
                btnEliminarCo.setText("Cancelar");
                btnEditarCo.setDisable(true);
                btnReportesCo.setDisable(true);
                imgAgregarCo.setImage((new Image("/org/andresartiga/images/ImagenGuardar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenCancelar.png")));
                
                tipoDeOperaciones = MenuComprasController.operaciones.ACTUALIZAR;
                break;

            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarCo.setText("Agregar");
                btnEliminarCo.setText("Eliminar");
                btnEditarCo.setDisable(false);
                btnReportesCo.setDisable(false);
                imgAgregarCo.setImage(new Image("/org/andresartiga/images/ImagenAgregar.png"));
                imgEliminar.setImage(new Image("/org/andresartiga/images/ImagenEliminar.png"));
                cargarDatos();
                tipoDeOperaciones = MenuComprasController.operaciones.NINGUNO;
                break;
                
        }
    }
            
    public void guardar() {
        Compras registro = new Compras();
        registro.setNumeroDocumento(Integer.parseInt(txtNumDoc.getText()));
        registro.setFechaDocumento(txtFechaDoc.getText());
        registro.setDescripcion(txtDesc.getText());
        registro.setTotalDocumento(Double.parseDouble(txtTotDoc.getText()));
        try {
            // Se puede usar el mismo metodo porque la base de datos es local
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCompras(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
            procedimiento.execute();
            listaCompras.add(registro);
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
                imgAgregarCo.setImage((new Image("/org/andresartiga/images/ImagenAgregar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenEliminar.png")));
                tipoDeOperaciones = MenuComprasController.operaciones.NINGUNO;
                break;
            default:
                if(tblCompras.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro",
                            "Eliminar Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCompras(?)}");
                            procedimiento.setInt(1, ((Compras)tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            listaCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar una compra para eliminar");
                }
                break;
        }
            }
        
        public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblCompras.getSelectionModel().getSelectedItem() != null){
                btnEditarCo.setText("Actualizar");
                btnReportesCo.setText("Cancelar");
                btnAgregarCo.setDisable(true);
                btnEliminarCo.setDisable(true);
                imgEditar.setImage(new Image("/org/andresartiga/images/ImagenGuardar.png"));
                imgReportes.setImage(new Image("/org/andresartiga/images/ImagenCancelar.png"));
                activarControles();
                txtNumDoc.setEditable(false);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarCo.setText("Editar");
                btnReportesCo.setText("Reporte");
                btnAgregarCo.setDisable(false);
                btnEliminarCo.setDisable(false);
                imgEditar.setImage(new Image("/org/andresartiga/images/ImagenActualizar.png"));
                imgReportes.setImage(new Image("/org/andresartiga/images/ImagenReportes.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCompras (?, ?, ?, ?)}");
            Compras registro = (Compras)tblCompras.getSelectionModel().getSelectedItem();
            registro.setFechaDocumento(txtFechaDoc.getText());
            registro.setDescripcion(txtDesc.getText());
            registro.setTotalDocumento(Double.parseDouble(txtTotDoc.getText()));
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
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
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
    
        @FXML
    public void handleButtonAction(ActionEvent event) {
        if(event.getSource()== btnHome){
            escenarioPrincipal.menuPrincipalView();
        }
    }
}


