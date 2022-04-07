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


public class PaymentViewController implements Initializable {

        private ArrayList<Payment> data;
        private ObservableList<Payment> dataList;
    

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "BaladiCenter";
	private Connection con;
        
        
        @FXML private TableView<Payment> TvP;
        @FXML private TableColumn<Payment,Integer> PId;
        @FXML private TableColumn <Payment,String>  PAdate;
        @FXML private TableColumn <Payment,String>  typeOFpa;

   
      @FXML private TextField PIdTextField;
      @FXML private TextField PAdateTextField;
      @FXML private TextField typeOFpaTextField;

  


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
      
        PId.setCellValueFactory(new PropertyValueFactory<Payment , Integer>("PId"));
        PAdate.setCellValueFactory(new PropertyValueFactory<Payment , String>("PAdate"));
        typeOFpa.setCellValueFactory(new PropertyValueFactory<Payment , String>("typeOFpa"));
        
        TvP.setItems(dataList); 
        TvP.setEditable(true);
        
        PId.setCellFactory(TextFieldTableCell.<Payment,Integer>forTableColumn(new IntegerStringConverter()));
        PAdate.setCellFactory(TextFieldTableCell.<Payment>forTableColumn());
        typeOFpa.setCellFactory(TextFieldTableCell.<Payment>forTableColumn());

        TvP.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }   
    
    
     private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;
				
		connectDB();
		System.out.println("Connection established");

		SQL = "select * from payment";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);


		while ( rs.next() ) 
			data.add(new Payment(Integer.parseInt(rs.getString(1)),
                              rs.getString(2),
                              rs.getString(3)));

	
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
    
        public void changePIdCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Payment payment =  TvP.getSelectionModel().getSelectedItem();
          
        connectDB();
	execute("UPDATE Payment set PId = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE PId = "+ payment.getPId()+";");
        con.close(); 
        payment.setPId(Integer.parseInt(e.getNewValue().toString()));
    }
        
        public void changePAdateCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Payment payment =  TvP.getSelectionModel().getSelectedItem();
        payment.setPAdate(e.getNewValue().toString()); 
        String a = payment.getPAdate();
        connectDB();
	execute("UPDATE Payment set PAdate = '" + a + "' WHERE PId = "+ payment.getPId()+";");
        con.close();
       
         
    }
        
    
                
        public void changetypeOFpaCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Payment payment =  TvP.getSelectionModel().getSelectedItem();
         payment.setTypeOFpa(e.getNewValue().toString());
        String a = payment.getTypeOFpa();
        connectDB();
	execute("UPDATE Payment set typeOFpa = '" + a + "' WHERE PId = "+ payment.getPId()+";");
        con.close(); 
    }
        
        
        @FXML
    public void addNewPayment() throws ClassNotFoundException, SQLException{
        Payment payment =  new Payment(Integer.parseInt(PIdTextField.getText()),
                PAdateTextField.getText(),
                typeOFpaTextField.getText());
        
        connectDB();
	execute("INSERT INTO Payment VALUES("+Integer.parseInt(PIdTextField.getText())+",'"+PAdateTextField.getText()+"','"+typeOFpaTextField.getText()+"');");
        con.close();
        
        TvP.getItems().add(payment);
    }
    

    @FXML
    public void deleteSelectedPayment() throws ClassNotFoundException, SQLException{
        ObservableList<Payment> selectedRows, allPayment;
        allPayment = TvP.getItems();
        selectedRows = TvP.getSelectionModel().getSelectedItems();
        
        connectDB();
        for(Payment p : selectedRows){
            execute("delete from Payment where PId="+ p.getPId()+ ";");
            allPayment.remove(p);
        }

	con.close();
        
    }   
}
