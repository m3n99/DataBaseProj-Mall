package projbase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Pay {
    private int PId;
	private int TaSsn;
	private int BiId;

	Pay(int PId, int TaSsn, int BiId) {
		this.PId = PId;
		this.TaSsn = TaSsn;
		this.BiId = BiId;
	}

	public int getPId() {
		return PId;
	}

	public void setPId(int pId) {
		PId = pId;
	}

	public int getTaSsn() {
		return TaSsn;
	}

	public void setTaSsn(int taSsn) {
		TaSsn = taSsn;
	}

	public int getBiId() {
		return BiId;
	}

	public void setBiId(int biId) {
		BiId = biId;
	}

}
