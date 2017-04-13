package sfproj.client;

public class Department {
	private String DepartmentName;
	private String EmployeeNum;
	private String Type;

	public Department(){
		
	}
	
	public Department(String dName, String eNum, String type){
		this.DepartmentName = dName;
		this.EmployeeNum = eNum;
		this.Type = type;
	}
	
	public String getDepartmentName() {
		return DepartmentName;
	}

	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
	}

	public String getEmployeeNum() {
		return EmployeeNum;
	}

	public void setEmployeeNum(String employeeNum) {
		EmployeeNum = employeeNum;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}
}
