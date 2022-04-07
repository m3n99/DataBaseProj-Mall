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


public class RoomReViewController implements Initializable {
    
    private ArrayList<RoomRe> data;
     private ObservableList<RoomRe> dataList;
    

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "BaladiCenter";
	private Connection con;
        
        
   @FXML private TableView<RoomRe> TvRE;
   @FXML private TableColumn<RoomRe,Integer> Rid;
   @FXML private TableColumn <RoomRe,Integer> SpaceOfRoom;
   @FXML private TableColumn <RoomRe,Integer> FloorNum;
   @FXML private TableColumn <RoomRe,Integer> ParkingNum;
   @FXML private TableColumn <RoomRe,Integer> TaSsn;
   @FXML private TableColumn <RoomRe,String>  S_Date;
   @FXML private TableColumn <RoomRe,String>  D_Date;
   
   @FXML private TextField RidTextField;
   @FXML private TextField SpaceOfRoomTextField;
   @FXML private TextField FloorNumTextField;
   @FXML private TextField ParkingNumTextField;
   @FXML private TextField TaSsnTextField;
   @FXML private TextField S_DateTextField;
   @FXML private TextField D_DateTextField;
   
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
        
        Rid.setCellValueFactory(new PropertyValueFactory<RoomRe , Integer>("Rid"));
        SpaceOfRoom.setCellValueFactory(new PropertyValueFactory<RoomRe , Integer>("SpaceOfRoom"));
        FloorNum.setCellValueFactory(new PropertyValueFactory<RoomRe , Integer>("FloorNum"));
        ParkingNum.setCellValueFactory(new PropertyValueFactory<RoomRe , Integer>("ParkingNum"));
        TaSsn.setCellValueFactory(new PropertyValueFactory<RoomRe , Integer>("TaSsn"));
        S_Date.setCellValueFactory(new PropertyValueFactory<RoomRe , String>("S_Date"));
        D_Date.setCellValueFactory(new PropertyValueFactory<RoomRe , String>("D_Date"));
        
        TvRE.setItems(dataList); 
        TvRE.setEditable(true);
        
        Rid.setCellFactory(TextFieldTableCell.<RoomRe,Integer>forTableColumn(new IntegerStringConverter()));
        SpaceOfRoom.setCellFactory(TextFieldTableCell.<RoomRe,Integer>forTableColumn(new IntegerStringConverter()));
        FloorNum.setCellFactory(TextFieldTableCell.<RoomRe,Integer>forTableColumn(new IntegerStringConverter()));
        ParkingNum.setCellFactory(TextFieldTableCell.<RoomRe,Integer>forTableColumn(new IntegerStringConverter()));
        TaSsn.setCellFactory(TextFieldTableCell.<RoomRe,Integer>forTableColumn(new IntegerStringConverter()));
        S_Date.setCellFactory(TextFieldTableCell.<RoomRe>forTableColumn());
        D_Date.setCellFactory(TextFieldTableCell.<RoomRe>forTableColumn());
        
        TvRE.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
    }    
    
    private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;
				
		connectDB();
		System.out.println("Connection established");

		SQL = "select * from roomre";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);


		while ( rs.next() ) 
			data.add(new RoomRe(Integer.parseInt(rs.getString(1)),
                              Integer.parseInt(rs.getString(2)),
                              Integer.parseInt(rs.getString(3)),
                              Integer.parseInt(rs.getString(4)),
                              Integer.parseInt(rs.getString(5)),
                              rs.getString(6),
                              rs.getString(7)));

                
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
    
    public void changeRidCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        RoomRe roomRe =  TvRE.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE roomRe set Rid = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE Rid = "+ roomRe.getRid()+";");
        con.close();
        roomRe.setRid(Integer.parseInt(e.getNewValue().toString()));  
    }
    
    public void changeSpaceOfRoomCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        RoomRe roomRe =  TvRE.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE roomRe set SpaceOfRoom = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE Rid = "+ roomRe.getRid()+";");
        con.close();
        roomRe.setSpaceOfRoom(Integer.parseInt(e.getNewValue().toString()));  
    }
       
    public void changeFloorNumCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        RoomRe roomRe =  TvRE.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE roomRe set FloorNum = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE Rid = "+ roomRe.getRid()+";");
        con.close();
        roomRe.setFloorNum(Integer.parseInt(e.getNewValue().toString()));  
    }
    
    public void changeParkingNumCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        RoomRe roomRe =  TvRE.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE roomRe set ParkingNum = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE Rid = "+ roomRe.getRid()+";");
        con.close();
        roomRe.setParkingNum(Integer.parseInt(e.getNewValue().toString()));  
    }
    
    public void changeTaSsnCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        RoomRe roomRe =  TvRE.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE roomRe set TaSsn = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE Rid = "+ roomRe.getRid()+";");
        con.close();
        roomRe.setTaSsn(Integer.parseInt(e.getNewValue().toString()));  
    }
    
    public void changeS_DateCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        RoomRe roomRe =  TvRE.getSelectionModel().getSelectedItem();
        roomRe.setS_Date(e.getNewValue().toString()); 
        String a = roomRe.getS_Date();
        connectDB();
	execute("UPDATE roomRe set S_Date = '" + a + "' WHERE Rid = "+ roomRe.getRid()+";");
        con.close();
          
    }
    
    public void changeD_DateCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        RoomRe roomRe =  TvRE.getSelectionModel().getSelectedItem();
        roomRe.setD_Date(e.getNewValue().toString()); 
        String a = roomRe.getD_Date();
        connectDB();
	execute("UPDATE roomRe set e_Date = '" + a + "' WHERE Rid = "+ roomRe.getRid()+";");
        con.close();
         
    }
    
    @FXML
    public void addNewRoomRe() throws ClassNotFoundException, SQLException{
        RoomRe roomRe =  new RoomRe(Integer.parseInt(RidTextField.getText()),
                Integer.parseInt(SpaceOfRoom.getText()),
                Integer.parseInt(FloorNum.getText()),
                Integer.parseInt(ParkingNum.getText()),
                Integer.parseInt(TaSsn.getText()),
                S_Date.getText(),
                D_Date.getText());
        
        connectDB();
	execute("INSERT INTO RoomRe VALUES("+Integer.parseInt(RidTextField.getText())+","+Integer.parseInt(SpaceOfRoom.getText())+","+Integer.parseInt(FloorNum.getText())+","+ Integer.parseInt(ParkingNum.getText())+","+Integer.parseInt(TaSsn.getText())+" ,'"+S_Date.getText()+"','"+D_Date.getText()+"');");
        con.close();
        
        TvRE.getItems().add(roomRe);
    }
    
    @FXML
    public void deleteSelectedRoomRe() throws ClassNotFoundException, SQLException{
        ObservableList<RoomRe> selectedRows, allroomRe;
        allroomRe = TvRE.getItems();
        selectedRows = TvRE.getSelectionModel().getSelectedItems();
        
        connectDB();
        for(RoomRe r : selectedRows){
            execute("delete from RoomRe where Rid="+ r.getRid() + ";");
            allroomRe.remove(r);
        }

	con.close();
        
    }
    
}
