
package org.andresartiga.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.andresartiga.system.Principal;

/**
 *
 * @author informatica
 */
public class ProgramadorController  implements Initializable{
     private Principal escenarioPrincipal;
    @FXML
    private Button btnRegresarPr;
    
    public Principal getEscenarioPrincipal() {
    return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if(event.getSource()== btnRegresarPr){
            escenarioPrincipal.menuPrincipalView();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
