package sfproj.client;

public class timeList {
	private String clockIn;
	private String clockOut;
	private String date;
	private String hoursWorked;
	private String totalPay;
	
	public timeList(){
		//I hate java so much
	}
	public timeList(String clockIn, String clockOut, String date, String hoursWorked, String totalPay){
		this.setClockIn(clockIn);
		this.setClockOut(clockOut);
		this.setDate(date);
		this.setHoursWorked(hoursWorked);
		this.setTotalPay(totalPay);
	}
	public String getClockIn() {
		return clockIn;
	}
	public void setClockIn(String clockIn) {
		this.clockIn = clockIn;
	}
	public String getClockOut() {
		return clockOut;
	}
	public void setClockOut(String clockOut) {
		this.clockOut = clockOut;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHoursWorked() {
		return hoursWorked;
	}
	public void setHoursWorked(String hoursWorked) {
		this.hoursWorked = hoursWorked;
	}
	public String getTotalPay() {
		return totalPay;
	}
	public void setTotalPay(String totalPay) {
		this.totalPay = totalPay;
	}
}
