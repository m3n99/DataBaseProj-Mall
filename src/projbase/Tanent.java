package projbase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Tanent {
        private int TaSsn;
	private String TaName;
	private String PhoneNum;
	private String BirthDe;
	private String AddressOfBank;
	private String TypeOfShop;
	private String BankAccount;


	Tanent(int TaSsn, String TaName, String PhoneNum, String BirthDe, String AddressOfBank, String TypeOfShop,
			String BankAccount) {
		this.TaSsn = TaSsn;
		this.TaName = TaName;
		this.PhoneNum = PhoneNum;
		this.BirthDe = BirthDe;
		this.AddressOfBank = AddressOfBank;
		this.TypeOfShop = TypeOfShop;
		this.BankAccount = BankAccount;
	}

   // Tanent(String string, String string0, String string1, String string2, String string3) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   // }

	public int getTaSsn() {
		return TaSsn;
	}

	public void setTaSsn(int taSsn) {
		TaSsn = taSsn;
	}

	public String getTaName() {
		return TaName;
	}

	public void setTaName(String taName) {
		TaName = taName;
	}

	public String getPhoneNum() {
		return PhoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		PhoneNum = phoneNum;
	}

	public String getBirthDe() {
		return BirthDe;
	}

	public void setBirthDe(String birthDe) {
		BirthDe = birthDe;
	}

	public String getAddressOfBank() {
		return AddressOfBank;
	}

	public void setAddressOfBank(String addressOfBank) {
		AddressOfBank = addressOfBank;
	}

	public String getTypeOfShop() {
		return TypeOfShop;
	}

	public void setTypeOfShop(String typeOfShop) {
		TypeOfShop = typeOfShop;
	}

	public String getBankAccount() {
		return BankAccount;
	}

	public void setBankAccount(String bankAccount) {
		BankAccount = bankAccount;
	}
}
