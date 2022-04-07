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


public class ViewTanentController implements Initializable {
    
    
    private ArrayList<Tanent> data;
     private ObservableList<Tanent> dataList;
    

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "BaladiCenter";
	private Connection con;
    
   @FXML private TableView <Tanent> TvT;
   @FXML private TableColumn<Tanent,Integer> TaSsn;
   @FXML private TableColumn <Tanent,String> TaName;
   @FXML private TableColumn <Tanent,String> PhoneNum;
   @FXML private TableColumn <Tanent,String> BirthDe;
   @FXML private TableColumn <Tanent,String> AddressOfBank;
   @FXML private TableColumn <Tanent,String> TypeOfShop;
   @FXML private TableColumn <Tanent,String> BankAccount;
    
    
   @FXML private TextField TaSsnTextField;
   @FXML private TextField TaNameTextField;
   @FXML private TextField PhoneNumTextField;
   @FXML private TextField BirthDeTextField;
   @FXML private TextField AddressOfBankTextField;
   @FXML private TextField TypeOfShopTextField;
   @FXML private TextField BankAccountTextField;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        data = new ArrayList<>();
        
        try {
            getData();
            dataList = FXCollections.observableArrayList(data);
        } catch (SQLException ex) {
            Logger.getLogger(ViewTanentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewTanentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          TaSsn.setCellValueFactory(new PropertyValueFactory<Tanent , Integer>("TaSsn"));
          TaName.setCellValueFactory(new PropertyValueFactory<Tanent , String>("TaName"));
          PhoneNum.setCellValueFactory(new PropertyValueFactory<Tanent , String>("PhoneNum"));
          BirthDe.setCellValueFactory(new PropertyValueFactory<Tanent , String>("BirthDe"));
          AddressOfBank.setCellValueFactory(new PropertyValueFactory<Tanent , String>("AddressOfBank"));
          TypeOfShop.setCellValueFactory(new PropertyValueFactory<Tanent , String>("TypeOfShop"));
          BankAccount.setCellValueFactory(new PropertyValueFactory<Tanent , String>("BankAccount"));
          
          TvT.setItems(dataList);
          TvT.setEditable(true);
        
          TaSsn.setCellFactory(TextFieldTableCell.<Tanent,Integer>forTableColumn(new IntegerStringConverter()));
          TaName.setCellFactory(TextFieldTableCell.<Tanent>forTableColumn());
          PhoneNum.setCellFactory(TextFieldTableCell.<Tanent>forTableColumn());
          BirthDe.setCellFactory(TextFieldTableCell.<Tanent>forTableColumn());
          AddressOfBank.setCellFactory(TextFieldTableCell.<Tanent>forTableColumn());
          TypeOfShop.setCellFactory(TextFieldTableCell.<Tanent>forTableColumn());
          BankAccount.setCellFactory(TextFieldTableCell.<Tanent>forTableColumn());
          
          TvT.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    
    
    private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;
				
		connectDB();
		System.out.println("Connection established");

		SQL = "select * from tenant";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);


		while ( rs.next() ) 
			data.add(new Tanent(Integer.parseInt(rs.getString(1)),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
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
		}
		
		
	}
         
         @FXML
    private void handleReportAction(ActionEvent event) throws IOException {
        Parent rootB = FXMLLoader.load(getClass().getResource("reportT.fxml"));
        Scene sceneB = new Scene(rootB);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(sceneB);
        stage.show();
         }
    
      @FXML private void handleBackAction(ActionEvent event) throws IOException {
        Parent rootB = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene sceneB = new Scene(rootB);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(sceneB);
        stage.show();
         }
      
      public void changeTaSsnCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Tanent tanent =  TvT.getSelectionModel().getSelectedItem();
        
        connectDB();
        execute("UPDATE tenant set TaSsn = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE TaSsn = "+ tanent.getTaSsn()+";");
        con.close();
        tanent.setTaSsn(Integer.parseInt(e.getNewValue().toString()));  
    }
      
      public void changeTaNameCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Tanent tanent =  TvT.getSelectionModel().getSelectedItem();
        tanent.setTaName(e.getNewValue().toString()); 
        String a = tanent.getTaName();
        connectDB();
        execute("UPDATE Tenant set TaName = '" + a + "' WHERE TaSsn = "+ tanent.getTaSsn()+";");
        con.close();
    };

      
      public void changePhoneNumCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Tanent tanent =  TvT.getSelectionModel().getSelectedItem();
        tanent.setPhoneNum(e.getNewValue().toString()); 
        String a = tanent.getPhoneNum();
        connectDB();
        execute("UPDATE Tenant set PhoneNum = '" + a + "' WHERE TaSsn = "+ tanent.getTaSsn()+";");
        con.close();
          
    }
      
     public void changeBirthDeCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Tanent tanent =  TvT.getSelectionModel().getSelectedItem();
        tanent.setBirthDe(e.getNewValue().toString()); 
        String a = tanent.getBirthDe();
        connectDB();
        execute("UPDATE tenant set BirthDe = '" + a + "' WHERE TaSsn = "+ tanent.getTaSsn()+";");
        con.close();
 
    }
    
    public void changeAddressOfBankCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Tanent tanent =  TvT.getSelectionModel().getSelectedItem();
        tanent.setAddressOfBank(e.getNewValue().toString());  
        String a = tanent.getAddressOfBank();
        connectDB();
        execute("UPDATE Tenant set AddressOfBank = '" + a + "' WHERE TaSsn = "+ tanent.getTaSsn()+";");
        con.close();
    } 
    
    public void changeTypeOfShopCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Tanent tanent =  TvT.getSelectionModel().getSelectedItem();
        tanent.setTypeOfShop(e.getNewValue().toString()); 
        String a = tanent.getTypeOfShop();
        connectDB();
        execute("UPDATE tenant set TypeOfShop = '" + a + "' WHERE TaSsn = "+ tanent.getTaSsn()+";");
        con.close();

    } 
    
    public void changeBankAccountCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Tanent tanent =  TvT.getSelectionModel().getSelectedItem();
        tanent.setBankAccount(e.getNewValue().toString()); 
        String a = tanent.getBankAccount();
        connectDB();
        execute("UPDATE tenant set BankAccount = '" + a + "' WHERE TaSsn = "+ tanent.getTaSsn()+";");
        con.close();
 
    } 
    
     @FXML
    public void addNewTanent() throws ClassNotFoundException, SQLException{
        Tanent tanent =  new Tanent(Integer.parseInt(TaSsnTextField.getText()),
                TaNameTextField.getText(),
                PhoneNumTextField.getText(),
                BirthDeTextField.getText(),
                AddressOfBankTextField.getText(),
                TypeOfShopTextField.getText(),
                BankAccountTextField.getText());
        
        connectDB();
	execute("Insert into tenant (TaSsn, PhoneNum,AddressOfBank,TypeOfShop,BankAccount) values("+Integer.parseInt(TaSsnTextField.getText())+",'"
                +TaNameTextField.getText()+"','"+PhoneNumTextField.getText()+"','"+BirthDeTextField.getText()+"','"+TypeOfShopTextField.getText()+
                "','"+AddressOfBankTextField.getText()+"','"+BankAccountTextField.getText()+"');");
        
        execute("INSERT INTO Tenant VALUES ("+Integer.parseInt(TaSsnTextField.getText())+",'"+TaNameTextField.getText()+"', '"+PhoneNumTextField.getText()+"', '"+BirthDeTextField.getText()+"','"+  AddressOfBankTextField.getText()+"','"+TypeOfShopTextField.getText()+"','"+BankAccountTextField.getText()+"');");
        con.close();
        
        TvT.getItems().add(tanent);
    }
    
     @FXML
    public void deleteSelectedTanents() throws ClassNotFoundException, SQLException{
        ObservableList<Tanent> selectedRows, allTanent;
        allTanent = TvT.getItems();
        selectedRows = TvT.getSelectionModel().getSelectedItems();
        
        connectDB();
        for(Tanent t: selectedRows){
            execute("delete from tenant where TaSsn="+ t.getTaSsn()+ ";");
            allTanent.remove(t);
        }
	con.close();
        
    }
}
