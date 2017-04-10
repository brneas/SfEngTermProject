package sfproj.client;

public class Employee {
	private int ID;
	private String Name;
	private String Department;
	private String Rank;
	private String HPay;
	private String WPay;
	
	public Employee(){
		
	}
	public Employee(int ID, String Name, String Department, String Rank, String HPay, String WPay){
		this.ID = ID;
		this.Name = Name;
		this.Department = Department;
		this.Rank = Rank;
		this.HPay = HPay;
		this.WPay = WPay;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int id) {
		ID = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public String getRank() {
		return Rank;
	}

	public void setRank(String rank) {
		Rank = rank;
	}

	public String getHPay() {
		return HPay;
	}

	public void setHPay(String hPay) {
		HPay = hPay;
	}

	public String getWPay() {
		return WPay;
	}

	public void setWPay(String wPay) {
		WPay = wPay;
	}
}
