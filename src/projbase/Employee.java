package projbase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Employee {
    
        private int EmSsn;
	private int Salary;
	private String EmName;
	private String PhoneNum;
	private String Birthdate;
	private String Address;
        private int Timework;
	private String EmployeeType;
        private int Rid;


	Employee(int EmSsn, String EmName, String PhoneNum, String Birthdate,String Address,int Timework, int Salary,
			String EmployeeType,int Rid) {
		this.EmSsn = EmSsn;
		this.Salary = Salary;
		this.EmName = EmName;
		this.PhoneNum = PhoneNum;
		this.Birthdate = Birthdate;
		this.Address = Address;
                this.Timework = Timework;
		this.EmployeeType = EmployeeType;
                this.Rid = Rid;
	}

	public int getEmSsn() {
		return EmSsn;
	}

	public void setEmSsn(int emSsn) {
		EmSsn = emSsn;
	}

	public int getSalary() {
		return Salary;
	}

	public void setSalary(int salary) {
		Salary = salary;
	}

	public String getEmName() {
		return EmName;
	}

	public void setEmName(String emName) {
		EmName = emName;
	}

	public String getPhoneNum() {
		return PhoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		PhoneNum = phoneNum;
	}

	public String getBirthdate() {
		return Birthdate;
	}

	public void setBirthdate(String birthdate) {
		Birthdate = birthdate;
	}
        public int Timework() {
		return Timework;
	}

	public void setTimework(int Timework) {
		Timework = Timework;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getEmployeeType() {
		return EmployeeType;
	}

	public void setEmployeeType(String employeeType) {
		EmployeeType = employeeType;
	}
        public int getRid() {
		return Rid;
	}

	public void setRid(int Rid) {
		Rid = Rid;
	}

    @Override
    public String toString() {
        return Salary+ " " + EmName +" " + PhoneNum+ " " + Birthdate +" " + Address+ " " + Timework +" " + EmployeeType+ " " + Rid ;
    }
        
        
}
