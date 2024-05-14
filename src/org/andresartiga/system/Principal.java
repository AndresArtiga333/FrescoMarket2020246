/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.andresartiga.system;


import java.io.InputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.andresartiga.controller.MenuCargoEmpleadoController;
import org.andresartiga.controller.MenuClientesController;
import org.andresartiga.controller.MenuComprasController;
import org.andresartiga.controller.MenuPrincipalControlador;
import org.andresartiga.controller.MenuProveedoresController;
import org.andresartiga.controller.MenuTipoProductoController;
import org.andresartiga.controller.ProgramadorController;

/**
 *
 * Andres Gilberto Artiga Perez
 * 2020246
 * IN5BM
 * Creacion: 10/04/2024
 */
public class Principal extends Application {
    private Stage escenarioPrincipal;
    private Scene escena;
    


        
    public static void main(String[] args) {
        launch(args);
    }
        @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Fresco Market");
        escenarioPrincipal.getIcons().add(new Image("org/andresartiga/images/Logo.png"));
        menuPrincipalView();
        //Parent root = FXMLLoader.load(getClass().getResource("/org/andresartiga/view/MenuPrincipalView.fxml"));
        //Scene escena = new Scene(root);
        //escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }
    
    public Initializable cambiarEscena(String fxmlname, int width, int heigth) throws Exception{
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        InputStream file = Principal.class.getResourceAsStream("/org/andresartiga/view/" + fxmlname);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Principal.class.getResource("/org/andresartiga/view/" + fxmlname));
        
        escena = new Scene((AnchorPane)loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        
        resultado = (Initializable)loader.getController();
        
        return resultado;
    }
    public void menuPrincipalView(){
        try{
            MenuPrincipalControlador menuPrincipalView = (MenuPrincipalControlador)cambiarEscena
         ("MenuPrincipalView.fxml" , 1020, 580);
            menuPrincipalView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuClientes(){
        try{
            MenuClientesController menuClientes = (MenuClientesController)cambiarEscena("MenuClientes.fxml", 1065, 600);
            menuClientes.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void programadorView(){
        try{
            ProgramadorController programador = (ProgramadorController)cambiarEscena("ProgramadorView.fxml", 600, 400);
            programador.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuProveedoresView(){
        try{
            MenuProveedoresController vistaProveedores = (MenuProveedoresController)cambiarEscena("MenuProveedoresView.fxml", 1207, 707);
            vistaProveedores.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuComprasView(){
        try{
            MenuComprasController vistaCompras = (MenuComprasController)cambiarEscena("MenuComprasView.fxml", 1019, 540);
            vistaCompras.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuTipoView(){
        try{
        MenuTipoProductoController vistaTipo = (MenuTipoProductoController)cambiarEscena("MenuTipoProductoView.fxml", 821, 525);
        vistaTipo.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuCargoView(){
        try{
            MenuCargoEmpleadoController vistaCargo = (MenuCargoEmpleadoController)cambiarEscena("MenuCargoEmpleado.fxml", 1037, 759);
            vistaCargo.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

//        escenarioPrincipal.getIcons().add(new Image ("org/andresartiga/images/Logo.png"));