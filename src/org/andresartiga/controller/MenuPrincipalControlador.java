/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.andresartiga.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.andresartiga.system.Principal;

/**
 *
 * @author informatica
 */
public class MenuPrincipalControlador implements Initializable {
    private Principal escenarioPrincipal;
    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnProgramador;
    @FXML MenuItem btnMenuProveedores;
    @FXML MenuItem btnMenuCompras;
    @FXML MenuItem btnMenuTipoProducto;
    @FXML MenuItem btnCargoP;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML 
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnMenuClientes){
            escenarioPrincipal.menuClientes();
        }else if(event.getSource() == btnProgramador){
            escenarioPrincipal.programadorView();
        }else if(event.getSource() == btnMenuProveedores){
            escenarioPrincipal.menuProveedoresView();
        }else if(event.getSource() == btnMenuCompras){
            escenarioPrincipal.menuComprasView();
        }else if (event.getSource() == btnMenuTipoProducto){
            escenarioPrincipal.menuTipoView();
        }else if (event.getSource() == btnCargoP){
            escenarioPrincipal.menuCargoView();
        }
    }
}
