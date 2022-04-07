/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projbase;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Msys
 */
public class ReportTController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    private ArrayList<Rtenant> data;
    private ObservableList<Rtenant> dataList;
    
    
    private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "BaladiCenter";
	private Connection con;
        
       @FXML private TableView <Rtenant> TvE;
       @FXML private TableColumn<Rtenant,String> TaName;

      @FXML private TextField typeTextField;
      @FXML private TextField dateTextField;
      
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
        private void getData() throws SQLException, ClassNotFoundException {
        
                String type = typeTextField.getText();
                String date = dateTextField.getText();
		// TODO Auto-generated method stub
		
		String SQL;
                connectDB();
       

		SQL =  "select distinct t.TaName from Tenant t where typeofshop = '"+type+"' and t.TaSsn in ( select r.tassn from Tenant t, roomre r where t.TaSsn = r.TaSsn and s_date = '"+date+"');";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);


		while ( rs.next() ) 
			data.add(new Rtenant(rs.getString(1)));


		rs.close();
		stmt.close();

		con.close();
	
	}
    
    
private void connectDB() throws ClassNotFoundException, SQLException {
		
		dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
		Properties p = new Properties();
		p.setProperty("user", dbUsername);
		p.setProperty("password", dbPassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.jdbc.Driver");
	
		con = DriverManager.getConnection (dbURL, p);

	}


         @FXML
    private void handleReportAction() {
            data = new ArrayList<>();
        
        try {
            // TODO
            getData();
            dataList = FXCollections.observableArrayList(data);

        } catch (SQLException ex) {
            Logger.getLogger(ViewEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        TaName.setCellValueFactory(new PropertyValueFactory<Rtenant,String>("TaName"));
        TvE.setItems(dataList);
         } 
    
    @FXML
    private void handleBackAction(ActionEvent event) throws IOException {
        Parent rootB = FXMLLoader.load(getClass().getResource("ViewTanent.fxml"));
        Scene sceneB = new Scene(rootB);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(sceneB);
        stage.show();
         }
    
}
