package projbase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class EmWorkRo {
        private int EnSsn;
	private int Rid;

	EmWorkRo(int EnSsn, int Rid) {
		this.EnSsn = EnSsn;
		this.Rid = Rid;

	}

	public int getEnSsn() {
		return EnSsn;
	}

	public void setEnSsn(int enSsn) {
		EnSsn = enSsn;
	}

	public int getRid() {
		return Rid;
	}

	public void setRid(int rid) {
		Rid = rid;
	}

}
