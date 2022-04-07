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


public class BILLController implements Initializable {

    private ArrayList<BILL> data;
     private ObservableList<BILL> dataList;
    

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "BaladiCenter";
	private Connection con;
        
   @FXML private TableView<BILL> TvB;
   @FXML private TableColumn<BILL,Integer> id;
   @FXML private TableColumn<BILL,String> Start_Date;
   @FXML private TableColumn<BILL,String> End_Date;
   
   @FXML private TextField idTextField;
   @FXML private TextField Start_DateTextField;
   @FXML private TextField End_DateTextField;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        data = new ArrayList<>();
        
        try {				
            getData();
            dataList = FXCollections.observableArrayList(data);
        } catch (SQLException ex) {
            Logger.getLogger(EmGeneratesPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmGeneratesPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        id.setCellValueFactory(new PropertyValueFactory<BILL , Integer>("id"));
        Start_Date.setCellValueFactory(new PropertyValueFactory<BILL , String>("Start_Date"));
        End_Date.setCellValueFactory(new PropertyValueFactory<BILL , String>("End_Date"));
        
        
        TvB.setItems(dataList); 
        TvB.setEditable(true);
        
        id.setCellFactory(TextFieldTableCell.<BILL,Integer>forTableColumn(new IntegerStringConverter()));
        Start_Date.setCellFactory(TextFieldTableCell.<BILL>forTableColumn());
        End_Date.setCellFactory(TextFieldTableCell.<BILL>forTableColumn());

        
        TvB.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    
    
    
     private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;
				
		connectDB();
		System.out.println("Connection established");

		SQL = "select * from Bill";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);


		while ( rs.next() ) 
			data.add(new BILL(Integer.parseInt(rs.getString(1)),
                              rs.getString(2),
                              rs.getString(3)));

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
    
      public void execute(String SQL) throws SQLException {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();
	 
		}
		catch(SQLException s) {
			s.printStackTrace();			  
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
    
    public void changeIdCellEvent(TableColumn.CellEditEvent e) throws SQLException, ClassNotFoundException {
        BILL bILL =  TvB.getSelectionModel().getSelectedItem();  
        connectDB();
	execute("UPDATE BILL set BiId = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE BiId = "+ bILL.getId()+";");
        con.close();
        bILL.setId(Integer.parseInt(e.getNewValue().toString())); 
    }
    
       public void changeEnd_DateCellEvent(TableColumn.CellEditEvent e) throws SQLException, ClassNotFoundException {
        BILL bILL =  TvB.getSelectionModel().getSelectedItem();  
        bILL.setEnd_Date(e.getNewValue().toString()); 
        String a = bILL.getEnd_Date();
        connectDB();
	execute("UPDATE BILL set EndDate = '" + a+ "' WHERE BiId = "+ bILL.getId()+";");
        con.close();
        bILL.setEnd_Date(e.getNewValue().toString()); 
    }
       
       
          public void changeStart_DateCellEvent(TableColumn.CellEditEvent e) throws SQLException, ClassNotFoundException {
        BILL bILL =  TvB.getSelectionModel().getSelectedItem();  
        bILL.setStart_Date(e.getNewValue().toString()); 
        String a = bILL.getStart_Date();
        connectDB();
	execute("UPDATE BILL set StartDate = '" + a + "' WHERE BiId = "+ bILL.getId()+";");
        con.close();
        
    }
    
           
           @FXML
    public void addNewBill() throws ClassNotFoundException, SQLException{
        BILL bILL =  new BILL(Integer.parseInt(idTextField.getText()),
                Start_DateTextField.getText(),
                End_DateTextField.getText());
        
        connectDB();
	execute("INSERT INTO Bill VALUES("+Integer.parseInt(idTextField.getText())+",'"+Start_DateTextField.getText()+"','"+End_DateTextField.getText()+"');");
        con.close();

        TvB.getItems().add(bILL);
    }

    @FXML
    public void deleteSelectedBill() throws ClassNotFoundException, SQLException{
        ObservableList<BILL> selectedRows, allBILLs;            
        allBILLs = TvB.getItems();
        selectedRows = TvB.getSelectionModel().getSelectedItems(); 
        
        
        connectDB();
        for(BILL b : selectedRows){
            execute("delete from BILL where BiId="+ b.getId()+ ";");
            allBILLs.remove(b);
        }
	con.close();
        
        
        
    }
}
