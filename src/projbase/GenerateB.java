package projbase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class GenerateB {
    private int Bill_id;
	private int BiId;

	GenerateB(int Bill_id, int BiId) {
		this.Bill_id = Bill_id;
		this.BiId = BiId;
	}

	public int getBill_id() {
		return Bill_id;
	}

	public void setBill_id(int bill_id) {
		Bill_id = bill_id;
	}

	public int getBiId() {
		return BiId;
	}

	public void setBiId(int biId) {
		BiId = biId;
	}
}
