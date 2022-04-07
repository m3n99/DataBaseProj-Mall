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
public class PayController implements Initializable {

        private ArrayList<Pay> data;
        private ObservableList<Pay> dataList;
    

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "BaladiCenter";
	private Connection con;
        
        @FXML private TableView<Pay> TvP;
        @FXML private TableColumn<Pay,Integer> PId;
        @FXML private TableColumn<Pay,Integer> TaSsn;
        @FXML private TableColumn<Pay,Integer> BiId;
        
        @FXML private TextField PIdTextField;
        @FXML private TextField TaSsnTextField;
        @FXML private TextField BiIdTextField;
        
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
        
        PId.setCellValueFactory(new PropertyValueFactory<Pay , Integer>("PId"));
        TaSsn.setCellValueFactory(new PropertyValueFactory<Pay , Integer>("TaSsn"));
        BiId.setCellValueFactory(new PropertyValueFactory<Pay , Integer>("BiId"));

        TvP.setItems(dataList); 
        TvP.setEditable(true);
        
        PId.setCellFactory(TextFieldTableCell.<Pay,Integer>forTableColumn(new IntegerStringConverter()));
        TaSsn.setCellFactory(TextFieldTableCell.<Pay,Integer>forTableColumn(new IntegerStringConverter()));
        BiId.setCellFactory(TextFieldTableCell.<Pay,Integer>forTableColumn(new IntegerStringConverter()));
        
        TvP.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }    
     private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;
				
		connectDB();
		System.out.println("Connection established");

		SQL = "select * from pay";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);


		while ( rs.next() ) 
			data.add(new Pay(Integer.parseInt(rs.getString(1)),
                              Integer.parseInt(rs.getString(2)),
                              Integer.parseInt(rs.getString(3))));

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
        Pay pay =  TvP.getSelectionModel().getSelectedItem();
        pay.setPId(Integer.parseInt(e.getNewValue().toString()));
        connectDB();
	execute("UPDATE Pay set PId = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE PId = "+ pay.getPId()+";");
        con.close();
        
    }
        
        public void changeTaSsnCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Pay pay =  TvP.getSelectionModel().getSelectedItem();
        pay.setTaSsn(Integer.parseInt(e.getNewValue().toString())); 
        connectDB();
	execute("UPDATE Pay set TaSsn = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE PId = "+ pay.getPId()+";");
        con.close();
          
    }
        
        public void changeBiIdCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Pay pay =  TvP.getSelectionModel().getSelectedItem();
        pay.setBiId(Integer.parseInt(e.getNewValue().toString()));  
        connectDB();
	execute("UPDATE Pay set BiId = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE PId = "+ pay.getPId()+";");
        con.close();
        
    }
        
            @FXML
    public void addNewPay() throws ClassNotFoundException, SQLException{
        Pay pay =  new Pay(Integer.parseInt(PIdTextField.getText()),
                Integer.parseInt(TaSsnTextField.getText()),
                Integer.parseInt(BiIdTextField.getText()));
        
        connectDB();
	execute("Insert into Bill_Line (PId, TaSsn,BiId) values("+Integer.parseInt(PIdTextField.getText())+","
                +Integer.parseInt(TaSsnTextField.getText())+","+Integer.parseInt(BiIdTextField.getText())+");");
        con.close();
        
        TvP.getItems().add(pay);
    }
    
    @FXML
    public void deleteSelectedPay() throws ClassNotFoundException, SQLException{
        ObservableList<Pay> selectedRows, allPay;
        allPay = TvP.getItems();
        selectedRows = TvP.getSelectionModel().getSelectedItems();
        
        connectDB();
        for(Pay p : selectedRows){
            execute("delete from Pay where PId= "+ p.getPId() + ";");
            allPay.remove(p);
        }

	con.close();
        
    }
}
