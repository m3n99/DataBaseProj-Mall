package projbase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Bill_Line {
    private int Bill_id;
	private int water;
	private int parking;
	private int electricity;
	private int rent_room;
	private int maintenance;

    public Bill_Line(int Bill_id, int water, int parking, int electricity, int rent_room, int maintenance) {
        this.Bill_id = Bill_id;
        this.water = water;
        this.parking = parking;
        this.electricity = electricity;
        this.rent_room = rent_room;
        this.maintenance = maintenance;
    }


        
        
        
	public int getBill_id() {
		return Bill_id;
	}

	public void setBill_id(int bill_id) {
		Bill_id = bill_id;
	}

	public int getWater() {
		return water;
	}

	public void setWater(int water) {
		this.water = water;
	}

	public int getParking() {
		return parking;
	}

	public void setParking(int parking) {
		this.parking = parking;
	}

	public int getElectricity() {
		return electricity;
	}

	public void setElectricity(int electricity) {
		this.electricity = electricity;
	}

	public int getRent_room() {
		return rent_room;
	}

	public void setRent_room(int rent_room) {
		this.rent_room = rent_room;
	}

	public int getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(int maintenance) {
		this.maintenance = maintenance;
	}

}
