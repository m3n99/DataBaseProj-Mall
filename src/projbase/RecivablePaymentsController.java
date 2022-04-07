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
public class RecivablePaymentsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private ArrayList<RecivablePayments> data;
     private ObservableList<RecivablePayments> dataList;
    

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "BaladiCenter";
	private Connection con;
   
      @FXML private TableView<RecivablePayments> TvR;
      @FXML private TableColumn<RecivablePayments,Integer> EmSsn;
      @FXML private TableColumn<RecivablePayments,Integer> PId;
      
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
        
        EmSsn.setCellValueFactory(new PropertyValueFactory<RecivablePayments , Integer>("EmSsn"));
        PId.setCellValueFactory(new PropertyValueFactory<RecivablePayments , Integer>("PId"));
        
        TvR.setItems(dataList); 
        TvR.setEditable(true);
        
        EmSsn.setCellFactory(TextFieldTableCell.<RecivablePayments,Integer>forTableColumn(new IntegerStringConverter()));
        PId.setCellFactory(TextFieldTableCell.<RecivablePayments,Integer>forTableColumn(new IntegerStringConverter()));
        
        TvR.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }    
    
     private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;
				
		connectDB();
		System.out.println("Connection established");

		SQL = "select * from emRecivablesPayment";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);


		while ( rs.next() ) 
			data.add(new RecivablePayments(Integer.parseInt(rs.getString(1)),
                              Integer.parseInt(rs.getString(2))));

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
			System.out.println("SQL statement is not executed!");
			  
		}
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
    private void handleBackAction(ActionEvent event) throws IOException {
        Parent rootB = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene sceneB = new Scene(rootB);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(sceneB);
        stage.show();
         }
    
        public void changeEmSsnCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        RecivablePayments recivablePayments =  TvR.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE EmRecivablesPayment set EmSsn = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE EmSsn = "+ recivablePayments.getEmSsn()+";");
        con.close();
        recivablePayments.setEmSsn(Integer.parseInt(e.getNewValue().toString()));  
    }
        
        public void changePIdCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        RecivablePayments recivablePayments =  TvR.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE EmRecivablesPayment set PId = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE EmSsn = "+ recivablePayments.getEmSsn()+";");
        con.close();
        recivablePayments.setPId(Integer.parseInt(e.getNewValue().toString()));  
    }
        
            @FXML
    public void addNewRecivablePayments() throws ClassNotFoundException, SQLException{
        RecivablePayments recivablePayments =  new RecivablePayments(Integer.parseInt(EmSsnTextField.getText()),
                Integer.parseInt(PIdTextField.getText()));
        
        connectDB();
	execute("Insert into emrecivablespayment (EmSsn, PId) values("+Integer.parseInt(EmSsnTextField.getText())
                +","+Integer.parseInt(PIdTextField.getText())+");");
        con.close();
        
        TvR.getItems().add(recivablePayments);
    }
    
    @FXML
    public void deleteSelectedRecivablePayments() throws ClassNotFoundException, SQLException{
        ObservableList<RecivablePayments> selectedRows, allRecivablePayments;
        allRecivablePayments = TvR.getItems();
        selectedRows = TvR.getSelectionModel().getSelectedItems();
        
        connectDB();
        for(RecivablePayments r : selectedRows){
            execute("delete from emrecivablespayment where EmSsn="+ r.getEmSsn() + ";");
            allRecivablePayments.remove(r);
        }

	con.close();
        
    }
}
