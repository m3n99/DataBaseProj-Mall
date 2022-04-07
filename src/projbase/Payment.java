package projbase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Payment {
    
    private int PId;
	private String PAdate;
	private String typeOFpa;

	Payment(int PId, String PAdate, String typeOFpa) {
		this.PId = PId;
		this.PAdate = PAdate;
		this.typeOFpa = typeOFpa;
	}


	public int getPId() {
		return PId;
	}

	public void setPId(int pId) {
		PId = pId;
	}

	public String getPAdate() {
		return PAdate;
	}

	public void setPAdate(String pAdate) {
		PAdate = pAdate;
	}

	public String getTypeOFpa() {
		return typeOFpa;
	}

	public void setTypeOFpa(String typeOFpa) {
		this.typeOFpa = typeOFpa;
	}
}
