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
import org.andresartiga.bean.Proveedores;
import org.andresartiga.db.Conexion;
import org.andresartiga.system.Principal;


/**
 *
 * @author andre
 */
public class MenuProveedoresController implements Initializable {
    private Principal escenarioPrincipal;
    private ObservableList<Proveedores> listaProveedores;
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    
    @FXML
    private Button btnRegresarP;
    
    @FXML
    private TableView tblProveedores;

    @FXML
    private TableColumn colCodP;

    @FXML
    private TableColumn colNitP;

    @FXML
    private TableColumn colNomP;

    @FXML
    private TableColumn colApeP;

    @FXML
    private TableColumn colDirP;

    @FXML
    private TableColumn colRazon;

    @FXML
    private TableColumn colContacP;

    @FXML
    private TableColumn colPagP;

    @FXML
    private Button btnAgregarP;

    @FXML
    private ImageView imgAgregar;

    @FXML
    private Button btnEliminarP;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnEditarP;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnReportesP;

    @FXML
    private ImageView imgReportes;

    @FXML
    private TextField txtCodigoP;

    @FXML
    private TextField txtNitP;

    @FXML
    private TextField txtNomP;

    @FXML
    private TextField txtApeP;

    @FXML
    private TextField txtDirP;

    @FXML
    private TextField txtRazon;

    @FXML
    private TextField txtConP;

    @FXML
    private TextField txtPaginaWeb;
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProveedores ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
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
        return listaProveedores = FXCollections.observableList(lista);
    }
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }
    
    public void cargarDatos() {
        tblProveedores.setItems(getProveedores());
        colCodP.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNitP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nitProveedor"));
        colNomP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
        colApeP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidoProveedor"));
        colDirP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazon.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContacP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colPagP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }
    
    public void seleccionar() {
        txtCodigoP.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNitP.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNitProveedor()));
        txtNomP.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNombreProveedor()));
        txtApeP.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getApellidoProveedor()));
        txtDirP.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor()));
        txtRazon.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial()));
        txtConP.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal()));
        txtPaginaWeb.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb()));
    }
    
        public void desactivarControles() {
        txtCodigoP.setEditable(false);
        txtNitP.setEditable(false);
        txtNomP.setEditable(false);
        txtApeP.setEditable(false);
        txtDirP.setEditable(false);
        txtRazon.setEditable(false);
        txtConP.setEditable(false);
        txtPaginaWeb.setEditable(false);
    }
        public void activarControles() {
        txtCodigoP.setEditable(true);
        txtNitP.setEditable(true);
        txtNomP.setEditable(true);
        txtApeP.setEditable(true);
        txtDirP.setEditable(true);
        txtRazon.setEditable(true);
        txtConP.setEditable(true);
        txtPaginaWeb.setEditable(true);
    }
        
        public void limpiarControles() {
        txtCodigoP.clear();
        txtNitP.clear();
        txtNomP.clear();
        txtApeP.clear();
        txtDirP.clear();
        txtRazon.clear();
        txtConP.clear();
        txtPaginaWeb.clear();
    }
        
        public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarP.setText("Guardar");
                btnEliminarP.setText("Cancelar");
                btnEditarP.setDisable(true);
                btnReportesP.setDisable(true);
                imgAgregar.setImage((new Image("/org/andresartiga/images/ImagenGuardar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenCancelar.png")));
                
                tipoDeOperaciones = MenuProveedoresController.operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarP.setText("Agregar");
                btnEliminarP.setText("Eliminar");
                btnEditarP.setDisable(false);
                btnReportesP.setDisable(false);
                imgAgregar.setImage(new Image("/org/andresartiga/images/ImagenAgregar.png"));
                imgEliminar.setImage(new Image("/org/andresartiga/images/ImagenEliminar.png"));
                cargarDatos();
                tipoDeOperaciones = MenuProveedoresController.operaciones.NINGUNO;
                break;
                
        }
    }
        public void guardar() {
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoP.getText()));
        registro.setNitProveedor(txtNitP.getText());
        registro.setNombreProveedor(txtNomP.getText());
        registro.setApellidoProveedor(txtApeP.getText());
        registro.setDireccionProveedor(txtDirP.getText());
        registro.setRazonSocial(txtRazon.getText());
        registro.setContactoPrincipal(txtConP.getText());
        registro.setPaginaWeb(txtPaginaWeb.getText());
        try {
            // Se puede usar el mismo metodo porque la base de datos es local
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarProveedor(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNitProveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
            listaProveedores.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
        public void eliminar() {
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarP.setText("Agregar");
                btnEliminarP.setText("Eliminar");
                btnEditarP.setDisable(false);
                btnReportesP.setDisable(false);
                imgAgregar.setImage((new Image("/org/andresartiga/images/ImagenAgregar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenEliminar.png")));
                tipoDeOperaciones = MenuProveedoresController.operaciones.NINGUNO;
                break;
            default:
                if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro",
                            "Eliminar Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarProveedor(?)}");
                            procedimiento.setInt(1, ((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para eliminar");
                }
                break;
        }
    }

        
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                btnEditarP.setText("Actualizar");
                btnReportesP.setText("Cancelar");
                btnAgregarP.setDisable(true);
                btnEliminarP.setDisable(true);
                imgEditar.setImage(new Image("/org/andresartiga/images/ImagenGuardar.png"));
                imgReportes.setImage(new Image("/org/andresartiga/images/ImagenCancelar.png"));
                activarControles();
                txtCodigoP.setEditable(false);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarP.setText("Editar");
                btnReportesP.setText("Reporte");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarProveedor (?, ?, ?, ?, ?, ?, ?, ?)}");
            Proveedores registro = (Proveedores)tblProveedores.getSelectionModel().getSelectedItem();
            registro.setNitProveedor(txtNitP.getText());
            registro.setNombreProveedor(txtNomP.getText());
            registro.setApellidoProveedor(txtApeP.getText());
            registro.setDireccionProveedor(txtDirP.getText());
            registro.setRazonSocial(txtRazon.getText());
            registro.setContactoPrincipal(txtConP.getText());
            registro.setPaginaWeb(txtPaginaWeb.getText());
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNitProveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
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
                btnEditarP.setText("Editar");
                btnReportesP.setText("Reporte");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                imgEditar.setImage(new Image("/org/andresartiga/images/ImagenActualizar.png"));
                imgReportes.setImage(new Image("/org/andresartiga/images/ImagenReporte.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
        @FXML
    public void handleButtonAction(ActionEvent event) {
        if(event.getSource()== btnRegresarP){
            escenarioPrincipal.menuPrincipalView();
        }
    }
}

