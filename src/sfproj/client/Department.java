package sfproj.client;

public class Department {
	private String DepartmentName;
	private String EmployeeNum;

	public Department(){
		
	}
	
	public Department(String dName, String eNum){
		this.DepartmentName = dName;
		this.EmployeeNum = eNum;
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
}
