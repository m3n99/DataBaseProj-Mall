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
public class EmWorkRoController implements Initializable {

    private ArrayList<EmWorkRo> data;
     private ObservableList<EmWorkRo> dataList;
    

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "BaladiCenter";
	private Connection con;
        
   @FXML private TableView<EmWorkRo> TvW;
   @FXML private TableColumn<EmWorkRo,Integer> EnSsn;
   @FXML private TableColumn <EmWorkRo,Integer>  Rid;
        
   
   @FXML private TextField EnSsnTextField;
   @FXML private TextField RidTextField;

   
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
        
        EnSsn.setCellValueFactory(new PropertyValueFactory<EmWorkRo , Integer>("EnSsn"));
        Rid.setCellValueFactory(new PropertyValueFactory<EmWorkRo , Integer>("Rid"));
        
        TvW.setItems(dataList); 
        TvW.setEditable(true);
        
        EnSsn.setCellFactory(TextFieldTableCell.<EmWorkRo,Integer>forTableColumn(new IntegerStringConverter()));
        Rid.setCellFactory(TextFieldTableCell.<EmWorkRo,Integer>forTableColumn(new IntegerStringConverter()));
        
        TvW.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    
    
      private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;
				
		connectDB();
		System.out.println("Connection established");

		SQL = "select * from emworkro";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);


		while ( rs.next() ) 
			data.add(new EmWorkRo(Integer.parseInt(rs.getString(1)),
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
    
        public void changeEnSsnCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        EmWorkRo emWorkRo =  TvW.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE EmWorkRo set EnSsn = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE EmWorkRo = "+ emWorkRo.getEnSsn()+";");
        con.close();
        emWorkRo.setEnSsn(Integer.parseInt(e.getNewValue().toString()));  
    }
        
         public void changeRidCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        EmWorkRo emWorkRo =  TvW.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE EmWorkRo set Rid = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE EmWorkRo = "+ emWorkRo.getEnSsn()+";");
        con.close();
        emWorkRo.setRid(Integer.parseInt(e.getNewValue().toString()));  
    }
         
    @FXML
    public void addNewEmWorkRo() throws ClassNotFoundException, SQLException{
        EmWorkRo emWorkRo =  new EmWorkRo(Integer.parseInt(EnSsnTextField.getText()),
        Integer.parseInt(RidTextField.getText()));
         connectDB();
	execute("INSERT INTO EmWorkRo VALUES("+Integer.parseInt(EnSsnTextField.getText())+","+Integer.parseInt(RidTextField.getText())+");");
        con.close();
        TvW.getItems().add(emWorkRo);
    }
    
        @FXML
    public void deleteSelectedEmWorkRo() throws ClassNotFoundException, SQLException{
        ObservableList<EmWorkRo> selectedRows, allEmWorkRo;
        allEmWorkRo = TvW.getItems();
        selectedRows = TvW.getSelectionModel().getSelectedItems();
        
                connectDB();
        for(EmWorkRo e : selectedRows){
            execute("delete from EmWorkRo where emssn="+ e.getEnSsn() + ";");
            allEmWorkRo.remove(e);
        }

	con.close();
    }
    
}
