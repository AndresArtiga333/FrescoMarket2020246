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
import org.andresartiga.bean.TipoProducto;
import org.andresartiga.db.Conexion;
import org.andresartiga.system.Principal;


public class MenuTipoProductoController implements Initializable {
    private Principal escenarioPrincipal;
    private ObservableList<TipoProducto> listaTipo;
    
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    
     @FXML
    private TableView tblTipoProducto;

    @FXML
    private TableColumn colIdTi;

    @FXML
    private TableColumn colDescTi;

    @FXML
    private Button btnAgregarTi;

    @FXML
    private ImageView imgAgregar;

    @FXML
    private Button btnEliminarTi;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnEditarTi;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnReportesTi;

    @FXML
    private ImageView imgReportes;

    @FXML
    private TextField txtIdTipo;

    @FXML
    private TextField txtDescTi;

    @FXML
    private Button btnRegresarTi;
    
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
        tblTipoProducto.setItems(getTipo());
        colIdTi.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("idTipoProducto"));
        colDescTi.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("descripcion"));
    }
    
    public void seleccionar() {
        txtIdTipo.setText(String.valueOf(((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getIdTipoProducto()));
        txtDescTi.setText(String.valueOf(((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getDescripcion()));
    }
    
    public ObservableList<TipoProducto> getTipo() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarTipoProducto ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("idTipoProducto"),
                        resultado.getString("descripcion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTipo = FXCollections.observableList(lista);
    }
    
    public void desactivarControles() {
        txtIdTipo.setEditable(false);
        txtDescTi.setEditable(false);
    }

    public void activarControles() {
        txtIdTipo.setEditable(true);
        txtDescTi.setEditable(true);
    }

    public void limpiarControles() {
        txtIdTipo.clear();
        txtDescTi.clear();
    }
    
    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarTi.setText("Guardar");
                btnEliminarTi.setText("Cancelar");
                btnEditarTi.setDisable(true);
                btnReportesTi.setDisable(true);
                imgAgregar.setImage((new Image("/org/andresartiga/images/ImagenGuardar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenCancelar.png")));
                
                tipoDeOperaciones = MenuTipoProductoController.operaciones.ACTUALIZAR;
                break;

            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarTi.setText("Agregar");
                btnEliminarTi.setText("Eliminar");
                btnEditarTi.setDisable(false);
                btnReportesTi.setDisable(false);
                imgAgregar.setImage(new Image("/org/andresartiga/images/ImagenAgregar.png"));
                imgEliminar.setImage(new Image("/org/andresartiga/images/ImagenEliminar.png"));
                cargarDatos();
                tipoDeOperaciones = MenuTipoProductoController.operaciones.NINGUNO;
                break;
                
        }
    }

    public void guardar() {
        TipoProducto registro = new TipoProducto();
        registro.setIdTipoProducto(Integer.parseInt(txtIdTipo.getText()));
        registro.setDescripcion(txtDescTi.getText());
        try {
            // Se puede usar el mismo metodo porque la base de datos es local
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarTipoProducto(?, ?)}");
            procedimiento.setInt(1, registro.getIdTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
            listaTipo.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
        public void eliminar() {
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarTi.setText("Agregar");
                btnEliminarTi.setText("Eliminar");
                btnEditarTi.setDisable(false);
                btnReportesTi.setDisable(false);
                imgAgregar.setImage((new Image("/org/andresartiga/images/ImagenAgregar.png")));
                imgEliminar.setImage((new Image("/org/andresartiga/images/ImagenEliminar.png")));
                tipoDeOperaciones = MenuTipoProductoController.operaciones.NINGUNO;
                break;
            default:
                if(tblTipoProducto.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro",
                            "Eliminar Tipo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem()).getIdTipoProducto());
                            procedimiento.execute();
                            listaTipo.remove(tblTipoProducto.getSelectionModel().getSelectedItem());
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
                if(tblTipoProducto.getSelectionModel().getSelectedItem() != null){
                btnEditarTi.setText("Actualizar");
                btnReportesTi.setText("Cancelar");
                btnAgregarTi.setDisable(true);
                btnEliminarTi.setDisable(true);
                imgEditar.setImage((new Image("/org/andresartiga/images/ImagenGuardar.png")));
                imgReportes.setImage((new Image("/org/andresartiga/images/ImagenCancelar.png")));
                activarControles();
                txtIdTipo.setEditable(false);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un tipo para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarTi.setText("Editar");
                btnReportesTi.setText("Reporte");
                btnAgregarTi.setDisable(false);
                btnEliminarTi.setDisable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarTipoProducto (?, ?)}");
            TipoProducto registro = (TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescTi.getText());
            procedimiento.setInt(1, registro.getIdTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
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
                btnEditarTi.setText("Editar");
                btnReportesTi.setText("Reporte");
                btnAgregarTi.setDisable(false);
                btnEliminarTi.setDisable(false);
                imgEditar.setImage(new Image("/org/andresartiga/images/ImagenActualizar.png"));
                imgReportes.setImage(new Image("/org/andresartiga/images/ImagenReporte.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if(event.getSource()== btnRegresarTi){
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
