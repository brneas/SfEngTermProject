package sfproj.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JOptionPane;

import ocsf.client.AbstractClient;

public class ClientNetHandler extends AbstractClient{

	public ClientNetHandler(String host, int port) throws IOException {
		super(host, port);
		openConnection();
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		System.out.println(msg);
		String line, lineAgain;
		BufferedWriter writer = null;
		String message[] = ((String) msg).split("\\|");
		if(message[0].equals("DepartmentList")){
			try {
				writer = new BufferedWriter(new FileWriter(new File("src/sfproj/client/dataSet/departmentList.txt")));
				for(int i=1; i<message.length-1;){
					writer.write(message[i] + "|" + message[i+1] + "|" + message[i+2] + "|" + message[i+3]);
					System.out.println("Wrote: " + message[i] + "|" + message[i+1] + "|" + message[i+2] + "|" + message[i+3]);
					i = i+4;
					writer.newLine();
				}
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(message[0].equals("EmployeeList")){
			try {
				writer = new BufferedWriter(new FileWriter(new File("src/sfproj/client/dataSet/employeeList.txt")));
				for(int i=1; i<message.length-1;){
					writer.write(message[i] + "|" + message[i+1] + "|" + message[i+2] + "|" + message[i+3] + "|" + message[i+4]);
					System.out.println("Wrote: " + message[i] + "|" + message[i+1] + "|" + message[i+2] + "|" + message[i+3] + "|" + message[i+4]);
					i = i+5;
					writer.newLine();
				}
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(message[0].equals("RankList")){
			try {
				writer = new BufferedWriter(new FileWriter(new File("src/sfproj/client/dataSet/rankList.txt")));
				for(int i=1; i<message.length-1;){
					writer.write(message[i] + "|" + message[i+1]);
					System.out.println("Wrote: " + message[i] + "|" + message[i+1]);
					i = i+2;
					writer.newLine();
				}
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(message[0].equals("Error")){
			if(message[1].equals("1")){//Already clocked In
				JOptionPane.showMessageDialog(null, "You are already clocked in.", "Clock In Error " + "Error", JOptionPane.INFORMATION_MESSAGE);
			}
			else if(message[1].equals("2")){//Already clocked out
				JOptionPane.showMessageDialog(null, "You are already clocked out.", "Clock Out Error " + "Error", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(message[0].equals("TimeList")){
			try {
				DecimalFormat df = new DecimalFormat("#.00");
				Time in, out;
				Date dateIn, dateOut, clockDate;
				long timeDiff;
				double hours;
				double pay, totalPay;
				Double weekHours = 0.00;
				boolean overtime = false;
				writer = new BufferedWriter(new FileWriter(new File("src/sfproj/client/dataSet/timeList.txt")));
				int i=1;
				if(message[2].equals("IN")){
					i = 8;
				}
				while(i<message.length-1){
					DateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
					Calendar monday = GregorianCalendar.getInstance(Locale.US);
					Calendar sunday = GregorianCalendar.getInstance(Locale.US);
					Date mondayD, sundayD, clock;
					monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
					sunday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
					sunday.add(Calendar.DATE, 6);
					mondayD = Date.valueOf(datef.format(monday.getTime()).toString());
					sundayD = Date.valueOf(datef.format(sunday.getTime()).toString());
					Time nextDate = Time.valueOf("22:30:00");
					in = Time.valueOf(message[i+9]);
					dateIn = Date.valueOf(message[i+8]);
					out = Time.valueOf(message[i+3]);
					dateOut = Date.valueOf(message[i+2]);
					clockDate = dateIn;
					timeDiff = (out.getTime()-in.getTime());
					if(dateOut.after(dateIn)){
						Time outMidnight = Time.valueOf("00:00:00");
						Time inMidnight = Time.valueOf("24:00:00");
						timeDiff = ((inMidnight.getTime() - in.getTime()) - (outMidnight.getTime() - out.getTime()));//What a bitch
					}
					if(in.after(nextDate)){
						clockDate = dateOut;
					}
					hours  = (double)timeDiff/1000/60/60;
					if(hours > 4.0){
						hours = hours - 0.5;
					}
					pay = Double.parseDouble(message[i+4]);
					clock = Date.valueOf(message[i+8]);
					if(!mondayD.after(clock) && !sundayD.before(clock)){
						weekHours = weekHours + hours;
						if(!message[i+11].equals("Call-Back") && !message[i+11].equals("Regular") && !message[i+11].equals("Holiday")){
							hours = 0;
						}
						if(message[i+11].equals("Call-Back") && hours < 4){
							totalPay = 4*pay;
						}
						else{
							if(overtime){
								totalPay = hours*(pay*1.5);
							}
							else{
								if(weekHours > 40.00){
									overtime = true;
									double partHours = weekHours-40.00;
									totalPay = partHours*(pay/2);
									//double temp = hours-partHours;
									totalPay = totalPay + hours*pay;
								}
								else{
									totalPay = hours*pay;
								}
							}
						}
					}
					else{
						if(!message[i+11].equals("Call-Back") && !message[i+11].equals("Regular") && !message[i+11].equals("Holiday")){
							hours = 0;
						}
						if(message[i+11].equals("Call-Back") && hours < 4){
							totalPay = 4*pay;
						}
						else{
							totalPay = hours*pay;
						}
					}
					System.out.println("Wrote: " + message[i] + "-" + message[i+6] + "|" + message[i+9] + "|" + message[i+3] + "|" + clockDate + "|" + df.format(hours) + "|" + df.format(totalPay)+ "|" + message[i+11]);
					writer.write(message[i] + "-" + message[i+6] + "|" + message[i+9] + "|" + message[i+3] + "|" + clockDate + "|" + df.format(hours) + "|" + df.format(totalPay)+ "|" + message[i+11]);
					i = i+12;
					writer.newLine();
					writer.flush();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(message[0].equals("FullTimeList")){
			try {
				DecimalFormat df = new DecimalFormat("0.00");
				Time in, out;
				Date dateIn, dateOut, clockDate;
				long timeDiff;
				double hours;
				double pay, totalPay = 0.00;
				writer = new BufferedWriter(new FileWriter(new File("src/sfproj/client/dataSet/fullTimeList.txt")));
				int i=1;
				if(message[1].equals("IN")){
					i = 7;
				}
				while(i<message.length-1){
					Time nextDate = Time.valueOf("22:30:00");
					in = Time.valueOf(message[i+9]);
					dateIn = Date.valueOf(message[i+8]);
					out = Time.valueOf(message[i+3]);
					dateOut = Date.valueOf(message[i+2]);
					clockDate = dateIn;
					timeDiff = (out.getTime()-in.getTime());
					if(dateOut.after(dateIn)){
						Time outMidnight = Time.valueOf("00:00:00");
						Time inMidnight = Time.valueOf("24:00:00");
						timeDiff = ((inMidnight.getTime() - in.getTime()) - (outMidnight.getTime() - out.getTime()));
					}
					if(in.after(nextDate)){
						clockDate = dateOut;
					}
					hours  = (double)timeDiff/1000/60/60;
					if(hours > 4.0){
						hours = hours - 0.5;
					}
					pay = Double.parseDouble(message[i+4]);
					if(!message[i+11].equals("Call-Back") && !message[i+11].equals("Regular") && !message[i+11].equals("Holiday")){
						hours = 0.00;
					}
					if(message[i+11].equals("Call-Back") && hours < 4){
						totalPay = 4*pay;
					}
					else{
						totalPay = hours*pay;
					}
					System.out.println("Wrote: " + message[i+6] + "|" + message[i+9] + "|" + message[i+3] + "|" + message[i+8] + "|" + df.format(hours) + "|" + df.format(totalPay)+ "|" + message[i+11]);
					writer.write(message[i+6] + "|" + message[i+9] + "|" + message[i+3] + "|" + clockDate + "|" + df.format(hours) + "|" + df.format(totalPay)+ "|" + message[i+11]);
					i = i+12;
					writer.newLine();
					writer.flush();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(message[0].equals("Login")){
			try {
				writer = writer = new BufferedWriter(new FileWriter(new File("src/sfproj/client/dataSet/login.txt")));
				writer.write(message[1] + "|" + message[2]);
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(message[0].equals("EmpList")){
			try {
				DecimalFormat payF = new DecimalFormat("0.00");
				DecimalFormat hourF = new DecimalFormat("0.0");
				writer = writer = new BufferedWriter(new FileWriter(new File("src/sfproj/client/dataSet/emp.txt")));
				int i = 1;
				double hours = 0.00, hoursTotal = 0.00, totalPay = 0.00;
				while(i < message.length -1){
					BufferedReader reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/fullTimeList.txt")));
					while((line = reader.readLine()) != null){
						String[] payLines = ((String) line).split("\\|");
						if(payLines[0].equals(message[i])){
							hours = hours + Double.parseDouble(payLines[4]);
						}
					}
					writer.write(message[i] + " - " + message[i+1] + "|" + message[i+2] + " - " + message[i+3] + "|" + message[i+4] + "|" + hourF.format(hours) + "|" + payF.format(Double.parseDouble(message[i+5])));
					System.out.println("Wrote " + message[i] + " - " + message[i+1] + "|" + message[i+2] + " - " + message[i+3] + "|" + message[i+4] + "|" + hours + "|" + message[i+5]);
					writer.newLine();
					writer.flush();
					hoursTotal = hoursTotal + hours;
					totalPay = totalPay + Double.parseDouble(message[i+5]);
					i = i+6;
					hours = 0.00;
				}
				writer.write("End" + "|" + hoursTotal + "|" + totalPay);
				System.out.println("Wrote " + "End" + "|" + hoursTotal + "|" + totalPay);
				writer.newLine();
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (message[0].equals("DeptBriefList")){
			try {
				DecimalFormat payF = new DecimalFormat("0.00");
				DecimalFormat hourF = new DecimalFormat("0.0");
				writer = writer = new BufferedWriter(new FileWriter(new File("src/sfproj/client/dataSet/deptBrief.txt")));
				int i = 1;
				double hours = 0.00, hoursTotal = 0.00, totalPay = 0.00;
				while(i < message.length -1){
					BufferedReader reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/fullTimeList.txt")));
					while((line = reader.readLine()) != null){
						String[] timeLines = ((String) line).split("\\|");
						BufferedReader readerAgain = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/employeeList.txt")));
						while((lineAgain = readerAgain.readLine()) != null){
							String[] empLines = ((String) lineAgain).split("\\|");
							if(empLines[2].equals(message[i])){
								if(timeLines[0].equals(empLines[0])){
									hours = hours + Double.parseDouble(timeLines[4]);
								}
							}
						}
					}
					writer.write(message[i+1] + "|" + hourF.format(hours) + "|" + message[i+2]);
					System.out.println("Wrote " + message[i+1] + "|" + hourF.format(hours) + "|" + payF.format(Double.parseDouble(message[i+2])));
					writer.newLine();
					writer.flush();
					hoursTotal = hoursTotal + hours;
					totalPay = totalPay + Double.parseDouble(message[i+2]);
					i = i+3;
					hours = 0.00;
				}
				writer.write("End" + "|" + hoursTotal + "|" + totalPay);
				System.out.println("Wrote " + "End" + "|" + hoursTotal + "|" + totalPay);
				writer.newLine();
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("The writer didn't close... wtf");
			e.printStackTrace();
		}
	}

}
