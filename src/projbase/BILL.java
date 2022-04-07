package projbase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class BILL {
    
        private int id;
	private String Start_Date;
	private String End_Date;
        

	BILL(int id, String Start_Date, String End_Date) {
            
		this.id = id;
		this.Start_Date = Start_Date;
		this.End_Date = End_Date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getStart_Date() {
		return Start_Date;
	}

	public void setStart_Date(String start_Date) {
		Start_Date = start_Date;
	}

	public String getEnd_Date() {
		return End_Date;
	}

	public void setEnd_Date(String end_Date) {
		End_Date = end_Date;
	}
	
}
