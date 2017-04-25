package sfproj.client;

public class timeList {
	private String clockIn;
	private String clockOut;
	private String date;
	private String hoursWorked;
	private String totalPay;
	private String clockType;
	private String ids;
	
	public timeList(){
		//I hate java so much
	}
	public timeList(String clockIn, String clockOut, String date, String hoursWorked, String totalPay, String clockType, String ids){
		this.setClockIn(clockIn);
		this.setClockOut(clockOut);
		this.setDate(date);
		this.setHoursWorked(hoursWorked);
		this.setTotalPay(totalPay);
		this.setClockType(clockType);
		this.setIds(ids);
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
	public String getClockType() {
		return clockType;
	}
	public void setClockType(String callBack) {
		this.clockType = callBack;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
