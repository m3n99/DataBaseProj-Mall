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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author Msys
 */
public class GenerateBController implements Initializable {

   
     private ArrayList<GenerateB> data;
     private ObservableList<GenerateB> dataList;
    

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "BaladiCenter";
	private Connection con;
        
        @FXML private TableView<GenerateB> TvGB;
        @FXML private TableColumn<GenerateB,Integer> Bill_id;
        @FXML private TableColumn<GenerateB,Integer> BiId;
        
        @FXML private TextField Bill_idTextField;
        @FXML private TextField BiIdTextField;
        
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = new ArrayList<>();
        
        try {				
            getData();
            dataList = FXCollections.observableArrayList(data);
        } catch (SQLException ex) {
            Logger.getLogger(EmGeneratesPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmGeneratesPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Bill_id.setCellValueFactory(new PropertyValueFactory<GenerateB , Integer>("Bill_id"));
        BiId.setCellValueFactory(new PropertyValueFactory<GenerateB , Integer>("BiId"));
                
        TvGB.setItems(dataList); 
        TvGB.setEditable(true);
        
        Bill_id.setCellFactory(TextFieldTableCell.<GenerateB,Integer>forTableColumn(new IntegerStringConverter()));
        BiId.setCellFactory(TextFieldTableCell.<GenerateB,Integer>forTableColumn(new IntegerStringConverter()));
        
        TvGB.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    
    
    
    private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;
				
		connectDB();
		System.out.println("Connection established");

		SQL = "select * from generateb";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);


		while ( rs.next() ) 
			data.add(new GenerateB(Integer.parseInt(rs.getString(1)),
                              Integer.parseInt(rs.getString(2))));
		
		rs.close();
		stmt.close();

		con.close();
		System.out.println("Connection closed" + data.size());
		
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
       
       
              public void execute(String SQL) throws SQLException {

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();
		
			 
		}
		catch(SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");
			  
		}
	}
              
              
             @FXML
        private void handleBackAction(ActionEvent event) throws IOException {
        Parent rootB = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene sceneB = new Scene(rootB);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(sceneB);
        stage.show();
         }
    
        public void changeBill_IdCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        GenerateB generateB =  TvGB.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE generateB set Bill_Id = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE bill_id = "+ generateB.getBill_id()+";");
        con.close();
        generateB.setBill_id( Integer.parseInt(e.getNewValue().toString()));  
    }
    
    public void changeBiIdCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        GenerateB generateB =  TvGB.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE generateB set BiId = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE bill_id = "+ generateB.getBill_id()+";");
        con.close();
        generateB.setBiId(Integer.parseInt(e.getNewValue().toString()));  
    }
    
        @FXML
    public void addNewGenerateB() throws ClassNotFoundException, SQLException{
        GenerateB generateB =  new GenerateB(Integer.parseInt(Bill_idTextField.getText()),
                Integer.parseInt(BiIdTextField.getText()));
        
        connectDB();
	execute("INSERT INTO GenerateB VALUES ("+Integer.parseInt(Bill_idTextField.getText())+","+Integer.parseInt(BiIdTextField.getText())+");");
        con.close();
        
        TvGB.getItems().add(generateB);
    }
    
        @FXML
    public void deleteSelectedGenerateB() throws ClassNotFoundException, SQLException{
        ObservableList<GenerateB> selectedRows, allGenerateB;
        allGenerateB = TvGB.getItems();
        selectedRows = TvGB.getSelectionModel().getSelectedItems();
        
        connectDB();
        for(GenerateB b : selectedRows){
            execute("delete from GenerateB where Bill_id="+ b.getBill_id() + ";");
            allGenerateB.remove(b);
        }

	con.close();
        

    }
}
