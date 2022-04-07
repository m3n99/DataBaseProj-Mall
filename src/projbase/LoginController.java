/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projbase;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Msys
 */
public class LoginController implements Initializable {
    
    //@FXML private TextField passwordTextField;
    @FXML private TextField usernameTextField;
    @FXML PasswordField passwordTextField;
    @FXML private Label message;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     @FXML
    private void handleMenuAction(ActionEvent event) throws IOException {
        if(checkNamePassword()){
        Parent rootVE = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene sceneVE = new Scene(rootVE);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(sceneVE);
        stage.show();
        }else
             System.out.println("Can't join to the database");
        message.setText("Password Or Username Is Incorrect..");
         }
    
    public boolean checkNamePassword(){
        String username = usernameTextField.getText().toString();
        String password = passwordTextField.getText().toString();
        if(password.equals("root") && username.equals("root"))
            return true;
        
        return false;
        
    }
    
}
