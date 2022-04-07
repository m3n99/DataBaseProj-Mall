package projbase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class RoomRe {
        private int Rid;
	private int SpaceOfRoom;
	private int FloorNum;
	private int ParkingNum;
	private int TaSsn;
	private String S_Date;
	private String D_Date;

	RoomRe(int Rid, int SpaceOfRoom, int FloorNum, int ParkingNum, int TaSsn, String S_Date, String D_Date) {
		this.Rid = Rid;
		this.SpaceOfRoom = SpaceOfRoom;
		this.FloorNum = FloorNum;
		this.ParkingNum = ParkingNum;
		this.TaSsn = TaSsn;
		this.S_Date = S_Date;
		this.D_Date = D_Date;

	}

    RoomRe(int parseInt, int parseInt0, String string, String string0, String string1, String string2, int parseInt1, String string3, int parseInt2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	public int getRid() {
		return Rid;
	}

	public void setRid(int rid) {
		Rid = rid;
	}

	public int getSpaceOfRoom() {
		return SpaceOfRoom;
	}

	public void setSpaceOfRoom(int spaceOfRoom) {
		SpaceOfRoom = spaceOfRoom;
	}

	public int getFloorNum() {
		return FloorNum;
	}

	public void setFloorNum(int floorNum) {
		FloorNum = floorNum;
	}

	public int getParkingNum() {
		return ParkingNum;
	}

	public void setParkingNum(int parkingNum) {
		ParkingNum = parkingNum;
	}

	public int getTaSsn() {
		return TaSsn;
	}

	public void setTaSsn(int taSsn) {
		TaSsn = taSsn;
	}

	public String getS_Date() {
		return S_Date;
	}

	public void setS_Date(String s_Date) {
		S_Date = s_Date;
	}

	public String getD_Date() {
		return D_Date;
	}

	public void setD_Date(String d_Date) {
		D_Date = d_Date;
	}
}
