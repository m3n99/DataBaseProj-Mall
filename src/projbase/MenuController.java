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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Msys
 */
public class MenuController implements Initializable {

    @FXML
    private void handleEmployeeAction(ActionEvent event) throws IOException {
        Parent rootVE = FXMLLoader.load(getClass().getResource("ViewEmployee.fxml"));
        Scene sceneVE = new Scene(rootVE);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(sceneVE);
        stage.show();
         }
    
    
    @FXML
    private void handleTanentAction(ActionEvent event) throws IOException {
        Parent rootVT = FXMLLoader.load(getClass().getResource("ViewTanent.fxml"));
        Scene sceneVT = new Scene(rootVT);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(sceneVT);
        stage.show();
    }
    //EmGeneratesPayment
    @FXML
    private void handleEmGeneratesPaymentControllerAction(ActionEvent event) throws IOException {
        Parent rootVTT = FXMLLoader.load(getClass().getResource("EmGeneratesPayment.fxml"));
        Scene sceneVTT = new Scene(rootVTT);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(sceneVTT);
        stage.show();
    }
    
      @FXML
    private void handlePaymentAction(ActionEvent event) throws IOException {
        Parent pay = FXMLLoader.load(getClass().getResource("PaymentView.fxml"));
        Scene scene = new Scene(pay);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    
    @FXML
    private void handleRecivablePaymentsAction(ActionEvent event) throws IOException {
        Parent pay = FXMLLoader.load(getClass().getResource("RecivablePayments.fxml"));
        Scene scene = new Scene(pay);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleBillAction(ActionEvent event) throws IOException {
        Parent bILL = FXMLLoader.load(getClass().getResource("BILL.fxml"));
        Scene scene = new Scene(bILL);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleBillLineAction(ActionEvent event) throws IOException {
        Parent bl = FXMLLoader.load(getClass().getResource("Bill_LineView.fxml"));
        Scene scene = new Scene(bl);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handlePayAction(ActionEvent event) throws IOException {
        Parent bl = FXMLLoader.load(getClass().getResource("Pay.fxml"));
        Scene scene = new Scene(bl);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    

    @FXML
    private void handleWorkAction(ActionEvent event) throws IOException {
        Parent bl = FXMLLoader.load(getClass().getResource("EmWorkRo.fxml"));
        Scene scene = new Scene(bl);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleRentAction(ActionEvent event) throws IOException {  
        Parent pay = FXMLLoader.load(getClass().getResource("RoomReView.fxml"));
        Scene scene = new Scene(pay);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleGenerateBillAction(ActionEvent event) throws IOException {
        Parent pay = FXMLLoader.load(getClass().getResource("GenerateB.fxml"));
        Scene scene = new Scene(pay);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleExitAction(ActionEvent event) throws IOException {
        Parent pay = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(pay);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
