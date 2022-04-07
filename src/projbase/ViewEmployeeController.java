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
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import projbase.Employee;



public class ViewEmployeeController implements Initializable {

    private ArrayList<Employee> data;
    private ObservableList<Employee> dataList;
    

    
    private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "BaladiCenter";
	private Connection con;
    
   @FXML private TableView <Employee> TvE;
   @FXML private TableColumn<Employee,Integer> EmSsn;
   @FXML private TableColumn<Employee,Integer> Salary;
   @FXML private TableColumn <Employee,String>  EmName;
   @FXML private TableColumn <Employee,String>  PhoneNum;
   @FXML private TableColumn <Employee,String>  Birthdate;
   @FXML private TableColumn <Employee,String>  Address;

   @FXML private TableColumn <Employee,String>  EmployeeType;
   @FXML private TableColumn <Employee,Integer>  Rid;
   
   
   @FXML private TextField EmSsnTextField;
   @FXML private TextField SalaryTextField;
   @FXML private TextField EmNameTextField;
   @FXML private TextField PhoneNumTextField;
   @FXML private TextField BirthdateextField;
   @FXML private TextField AddressTextField;
   @FXML private TextField EmployeeTypeTextField;
   @FXML private TextField RidTypeTextField;       

   

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        data = new ArrayList<>();
        
        try {
            // TODO
            getData();
            dataList = FXCollections.observableArrayList(data);

        } catch (SQLException ex) {
            Logger.getLogger(ViewEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       EmSsn.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("EmSsn"));
       Salary.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("Salary"));
       EmName.setCellValueFactory(new PropertyValueFactory<Employee,String>("EmName"));
       PhoneNum.setCellValueFactory(new PropertyValueFactory<Employee,String>("PhoneNum"));
       Birthdate.setCellValueFactory(new PropertyValueFactory<Employee,String>("Birthdate"));
       Address.setCellValueFactory(new PropertyValueFactory<Employee,String>("Address"));
       EmployeeType.setCellValueFactory(new PropertyValueFactory<Employee,String>("EmployeeType"));
       Rid.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("Rid"));
        
       TvE.setItems(dataList);
       TvE.setEditable(true);
       
       EmSsn.setCellFactory(TextFieldTableCell.<Employee,Integer>forTableColumn(new IntegerStringConverter()));
       Salary.setCellFactory(TextFieldTableCell.<Employee,Integer>forTableColumn(new IntegerStringConverter()));
       EmName.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
       PhoneNum.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
       Birthdate.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
       Address.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
       EmployeeType.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
       Rid.setCellFactory(TextFieldTableCell.<Employee,Integer>forTableColumn(new IntegerStringConverter()));
       
       TvE.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
       
}    
    
private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;
                connectDB();
       

		SQL = "select * from Employee";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);


		while ( rs.next() ) 
			data.add(new Employee(
					Integer.parseInt(rs.getString(1)),
                                        rs.getString(2),
                                        rs.getString(3),
                                        rs.getString(4),
                                        rs.getString(5),
                                        Integer.parseInt(rs.getString(6)),    
                                        Integer.parseInt(rs.getString(7)),
                                        rs.getString(8),
                                        Integer.parseInt(rs.getString(9))));

		
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
    
    @FXML
    private void handleReportAction(ActionEvent event) throws IOException {
        Parent rootB = FXMLLoader.load(getClass().getResource("report.fxml"));
        Scene sceneB = new Scene(rootB);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(sceneB);
        stage.show();
         }
    

    public void changeEmSsnCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Employee employee =  TvE.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE Employee set EmSsn = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE EmSsn = "+ employee.getEmSsn()+";");
        con.close();
        employee.setEmSsn(Integer.parseInt(e.getNewValue().toString()));  
    }
    
    public void changeSalaryCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Employee employee =  TvE.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE Employee set Salary = " + Integer.parseInt(e.getNewValue().toString()) + " WHERE EmSsn = "+ employee.getEmSsn()+";");
        con.close();
        employee.setSalary(Integer.parseInt(e.getNewValue().toString()));  
    }
    
    public void changeEmNameCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Employee employee =  TvE.getSelectionModel().getSelectedItem();
        employee.setEmName(e.getNewValue().toString());
        String a = employee.getEmName();
        
        connectDB();
	execute("UPDATE Employee set EmName = '" +a+"' WHERE EmSsn = "+employee.getEmSsn()+";");
        con.close();

    }

    public void changePhoneNumCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Employee employee =  TvE.getSelectionModel().getSelectedItem();
        employee.setPhoneNum(e.getNewValue().toString());
        String a = employee.getPhoneNum();
        
        connectDB();
	execute("UPDATE Employee set PhoneNum = '" + a + "' WHERE EmSsn = "+ employee.getEmSsn()+";");
        con.close();
 
    }
    
    public void changeBirthdateCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Employee employee =  TvE.getSelectionModel().getSelectedItem();
        employee.setBirthdate(e.getNewValue().toString());  
        String a = employee.getBirthdate();
        
        connectDB();
	execute("UPDATE Employee set Birthde = '" + a + "' WHERE EmSsn = "+ employee.getEmSsn()+";");
        con.close();  
    }
    
    public void changeAddressCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Employee employee =  TvE.getSelectionModel().getSelectedItem();
        employee.setAddress(e.getNewValue().toString()); 
        String a = employee.getAddress();
        connectDB();
	execute("UPDATE Employee set Address = '" + a + "' WHERE EmSsn = "+ employee.getEmSsn()+";");
        con.close();
    }
    

    
     public void changeEmployeeTypeCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Employee employee =  TvE.getSelectionModel().getSelectedItem();
        employee.setEmployeeType(e.getNewValue().toString()); 
        String a = employee.getEmployeeType();
        connectDB();
	execute("UPDATE Employee set EmployeeType = '" + a + "' WHERE EmSsn = "+ employee.getEmSsn()+";");
        con.close();
        
    }
    
    public void changeRidCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
        Employee employee =  TvE.getSelectionModel().getSelectedItem();
        connectDB();
	execute("UPDATE Employee set Rid = " + e.getNewValue().toString() + " WHERE EmSsn = "+ employee.getEmSsn()+";");
        con.close();
        employee.setRid(Integer.parseInt(e.getNewValue().toString()));  
    }
    
     @FXML
    public void addNewEmployee() throws ClassNotFoundException, SQLException{
        Employee employee =  new Employee(Integer.parseInt(EmSsnTextField.getText()),
                EmNameTextField.getText(),
                PhoneNumTextField.getText(),
                BirthdateextField.getText(),
                AddressTextField.getText(),
                Integer.parseInt(EmSsnTextField.getText()),
                Integer.parseInt(SalaryTextField.getText()),
                EmployeeTypeTextField.getText(),
                Integer.parseInt(RidTypeTextField.getText()));
        
        connectDB();
	execute("INSERT INTO Employee VALUES ("+Integer.parseInt(EmSsnTextField.getText())+",'"+EmNameTextField.getText()+"', '"+PhoneNumTextField.getText()+"', '"+BirthdateextField.getText()+"','"+ AddressTextField.getText()+"',"+Integer.parseInt(EmSsnTextField.getText())+","+Integer.parseInt(SalaryTextField.getText())+",'"+EmployeeTypeTextField.getText()+"',"+Integer.parseInt(RidTypeTextField.getText())+");");
        
        TvE.getItems().add(employee);
    }
    
      @FXML
    public void deleteSelectedEmployee() throws ClassNotFoundException, SQLException{
        ObservableList<Employee> selectedRows, allEmployee;
        allEmployee = TvE.getItems();
        selectedRows = TvE.getSelectionModel().getSelectedItems();
        
        connectDB();
        for(Employee e : selectedRows){
            execute("delete from Employee where emssn="+ e.getEmSsn()+ ";");
            allEmployee.remove(e);
        }
	con.close();
    }
    

    
}