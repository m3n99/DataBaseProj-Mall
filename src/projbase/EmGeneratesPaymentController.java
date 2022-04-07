


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
import javafx.scene.control.ListView;
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
public class EmGeneratesPaymentController implements Initializable {
    
    private ArrayList<EmGeneratesPayment> data;
    private ObservableList<EmGeneratesPayment> dataList;
    

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "BaladiCenter";
	private Connection con;
        
        
    @FXML private TableView<EmGeneratesPayment> tableView;
    @FXML private TableColumn<EmGeneratesPayment , Integer> EmSsn;
    @FXML private TableColumn<EmGeneratesPayment , Integer> PId;
    
    @FXML private TextField EmSsnTextField;
    @FXML private TextField PIdTextField;
   
   
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
        
        
        EmSsn.setCellValueFactory(new PropertyValueFactory<EmGeneratesPayment , Integer>("EmSsn"));
        PId.setCellValueFactory(new PropertyValueFactory<EmGeneratesPayment , Integer>("PId"));
    
        tableView.setItems(dataList);
        
        tableView.setEditable(true);
        EmSsn.setCellFactory(TextFieldTableCell.<EmGeneratesPayment,Integer>forTableColumn(new IntegerStringConverter()));
        PId.setCellFactory(TextFieldTableCell.<EmGeneratesPayment,Integer>forTableColumn(new IntegerStringConverter()));

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
    
    
    
    private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;
				
		connectDB();
		System.out.println("Connection established");

		SQL = "select * from emgeneratespayment";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);


		while ( rs.next() ) 
			data.add(new EmGeneratesPayment(
					Integer.parseInt(rs.getString(1)),
					Integer.parseInt(rs.getString(2))) );

		
		rs.close();
		stmt.close();

		con.close();
		System.out.println("Connection closed" + data.size());
		
		
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
    
     public void changeEmSsnCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        EmGeneratesPayment emGeneratesPayment =  tableView.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE EmGeneratesPayment set EmSsn = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE EmSsn = "+ emGeneratesPayment.getEmSsn()+";");
        con.close();
        emGeneratesPayment.setEmSsn(Integer.parseInt(e.getNewValue().toString()));  
    }

    public void changePIdCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        EmGeneratesPayment emGeneratesPayment =  tableView.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE EmGeneratesPayment set PId = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE EmSsn = "+ emGeneratesPayment.getEmSsn()+";");
        con.close();
        emGeneratesPayment.setPId(Integer.parseInt(e.getNewValue().toString()));      
    }
    
      @FXML
    public void addNewEmGeneratesPayment() throws ClassNotFoundException, SQLException{
        EmGeneratesPayment emGeneratesPayment =  new EmGeneratesPayment(Integer.parseInt(EmSsnTextField.getText()),
        Integer.parseInt(PIdTextField.getText()));
        
        tableView.getItems().add(emGeneratesPayment);
        connectDB();
	execute("Insert into emgeneratespayment (emssn, pid) values("+Integer.parseInt(EmSsnTextField.getText())+","+Integer.parseInt(PIdTextField.getText())+");");
        con.close();
    }
    
    @FXML
    public void deleteNewEmGeneratesPayment() throws SQLException, ClassNotFoundException{
        ObservableList<EmGeneratesPayment> selectedRows, allEmGeneratesPayment;
        allEmGeneratesPayment = tableView.getItems();
        selectedRows = tableView.getSelectionModel().getSelectedItems();
        
        connectDB();
        for(EmGeneratesPayment e : selectedRows){
            execute("delete from EmGeneratesPayment where emssn="+ e.EmSsn + ";");
            allEmGeneratesPayment.remove(e);
        }

	con.close();

    }
}