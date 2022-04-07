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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;


public class Bill_LineViewController implements Initializable {

     private ArrayList<Bill_Line> data;
     private ObservableList<Bill_Line> dataList;
    

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "BaladiCenter";
	private Connection con;
   
   @FXML private TableView<Bill_Line> TvB;
   @FXML private TableColumn<Bill_Line,Integer> Bill_id;
   @FXML private TableColumn <Bill_Line,Integer>  water;
   @FXML private TableColumn <Bill_Line,Integer>  parking;
   @FXML private TableColumn <Bill_Line,Integer>  electricity;
   @FXML private TableColumn <Bill_Line,Integer>  rent_room;
   @FXML private TableColumn <Bill_Line,Integer>  maintenance;

   
   @FXML private TextField Bill_idTextField;
   @FXML private TextField waterTextField;
   @FXML private TextField parkingTextField;
   @FXML private TextField electricityTextField;
   @FXML private TextField rent_roomTextField;
   @FXML private TextField maintenanceTextField;
   
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
        
        
        Bill_id.setCellValueFactory(new PropertyValueFactory<Bill_Line , Integer>("Bill_id"));
        water.setCellValueFactory(new PropertyValueFactory<Bill_Line , Integer>("water"));
        parking.setCellValueFactory(new PropertyValueFactory<Bill_Line , Integer>("parking"));
        electricity.setCellValueFactory(new PropertyValueFactory<Bill_Line , Integer>("electricity"));
        rent_room.setCellValueFactory(new PropertyValueFactory<Bill_Line , Integer>("rent_room"));
        maintenance.setCellValueFactory(new PropertyValueFactory<Bill_Line , Integer>("maintenance"));
        
        TvB.setItems(dataList); 
        TvB.setEditable(true);
        
        Bill_id.setCellFactory(TextFieldTableCell.<Bill_Line,Integer>forTableColumn(new IntegerStringConverter()));
        water.setCellFactory(TextFieldTableCell.<Bill_Line,Integer>forTableColumn(new IntegerStringConverter()));
        parking.setCellFactory(TextFieldTableCell.<Bill_Line,Integer>forTableColumn(new IntegerStringConverter()));
        electricity.setCellFactory(TextFieldTableCell.<Bill_Line,Integer>forTableColumn(new IntegerStringConverter()));
        rent_room.setCellFactory(TextFieldTableCell.<Bill_Line,Integer>forTableColumn(new IntegerStringConverter()));
        maintenance.setCellFactory(TextFieldTableCell.<Bill_Line,Integer>forTableColumn(new IntegerStringConverter()));
        
        TvB.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
    }   
    
    
     private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;
				
		connectDB();
		System.out.println("Connection established");

		SQL = "select * from bill_line";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);


		while ( rs.next() ) 
			data.add(new Bill_Line(Integer.parseInt(rs.getString(1)),
                              Integer.parseInt(rs.getString(2)),
                              Integer.parseInt(rs.getString(3)),
                              Integer.parseInt(rs.getString(4)),
                              Integer.parseInt(rs.getString(5)),
                              Integer.parseInt(rs.getString(6))));

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
    
    public void changeBill_IdCellEvent(CellEditEvent e) throws SQLException, ClassNotFoundException {
        Bill_Line bill_line =  TvB.getSelectionModel().getSelectedItem();  
        connectDB();
	execute("UPDATE bill_line set Bill_Id = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE bill_id = "+ bill_line.getBill_id()+";");
        con.close();
        bill_line.setBill_id( Integer.parseInt(e.getNewValue().toString())); 
    }
    
    public void changeWaterCellEvent(CellEditEvent e) throws ClassNotFoundException, SQLException{
        Bill_Line bill_line =  TvB.getSelectionModel().getSelectedItem();

        connectDB();
	execute("UPDATE bill_line set Water = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE bill_id = "+ bill_line.getBill_id()+";");
        con.close();
        
        bill_line.setWater(Integer.parseInt(e.getNewValue().toString()));      
    }
    
    public void changeParkingCellEvent(CellEditEvent e) throws ClassNotFoundException, SQLException{
        Bill_Line bill_line =  TvB.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE bill_line set Parking = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE bill_id = "+ bill_line.getBill_id()+";");
        con.close();
        bill_line.setParking(Integer.parseInt(e.getNewValue().toString()));      
    }
    
    public void changeElectricityCellEvent(CellEditEvent e) throws ClassNotFoundException, SQLException{
        Bill_Line bill_line =  TvB.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE bill_line set Electricity = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE bill_id = "+ bill_line.getBill_id()+";");
        con.close();
        bill_line.setElectricity(Integer.parseInt(e.getNewValue().toString()));      
    }
    
    public void changeRent_RoomCellEvent(CellEditEvent e) throws ClassNotFoundException, SQLException{
        Bill_Line bill_line =  TvB.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE bill_line set Rent_Room = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE bill_id = "+ bill_line.getBill_id()+";");
        con.close();
        bill_line.setRent_room(Integer.parseInt(e.getNewValue().toString()));      
    }
    
    public void changeMaintenanceCellEvent(CellEditEvent e) throws ClassNotFoundException, SQLException{
        Bill_Line bill_line =  TvB.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE bill_line set Maintenance = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE bill_id = "+ bill_line.getBill_id()+";");
        con.close();
        bill_line.setMaintenance(Integer.parseInt(e.getNewValue().toString()));      
    }
    
    @FXML
    public void addNewBill_Line() throws ClassNotFoundException, SQLException{
        Bill_Line bill_line =  new Bill_Line(Integer.parseInt(Bill_idTextField.getText()),
                Integer.parseInt(waterTextField.getText()),
                Integer.parseInt(parkingTextField.getText()),
                Integer.parseInt(electricityTextField.getText()),
                Integer.parseInt(rent_roomTextField.getText()),
                Integer.parseInt(maintenanceTextField.getText()));
        
        connectDB();
	execute("Insert into Bill_Line (Bill_id, water,parking,electricity,rent_room,maintenance) values("+Integer.parseInt(Bill_idTextField.getText())+","
                +Integer.parseInt(waterTextField.getText())+","+Integer.parseInt(parkingTextField.getText())+","+Integer.parseInt(electricityTextField.getText())+
                ","+Integer.parseInt(rent_roomTextField.getText())+","+Integer.parseInt(maintenanceTextField.getText())+");");
        con.close();
        
        TvB.getItems().add(bill_line);
    }

    @FXML
    public void deleteNewBill_Line() throws ClassNotFoundException, SQLException{
        ObservableList<Bill_Line> selectedRows, allBills;
        allBills = TvB.getItems();
        selectedRows = TvB.getSelectionModel().getSelectedItems();
        
        
        connectDB();
        for(Bill_Line b : selectedRows){
            execute("delete from Bill_Line where Bill_id="+ b.getBill_id() + ";");
            allBills.remove(b);
        }
	con.close();
        

    }

}
