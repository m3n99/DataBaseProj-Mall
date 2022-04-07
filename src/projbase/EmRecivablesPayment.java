package projbase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class EmRecivablesPayment {
    private int EmSsn;
	private int PId;

	EmRecivablesPayment(int EmSsn, int PId) {
		this.EmSsn = EmSsn;
		this.PId = PId;
	}

	public int getEmSsn() {
		return EmSsn;
	}

	public void setEmSsn(int emSsn) {
		EmSsn = emSsn;
	}

	public int getPId() {
		return PId;
	}

	public void setPId(int pId) {
		PId = pId;
	}

}
