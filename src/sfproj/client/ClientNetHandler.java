package sfproj.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
		String line;
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
				Time in;
				Time out;
				long timeDiff;
				double hours;
				double pay;
				writer = new BufferedWriter(new FileWriter(new File("src/sfproj/client/dataSet/timeList.txt")));
				int i=1;
				if(message[1].equals("IN")){
					i = 5;
				}
				while(i<message.length-1){
					in = Time.valueOf(message[i+7]);
					out = Time.valueOf(message[i+2]);
					timeDiff = (out.getTime()-in.getTime());
					hours  = (double)timeDiff/1000/60/60;
					pay = Double.parseDouble(message[i+3]);
					System.out.println("Wrote: " + message[i+7] + "|" + message[i+2] + "|" + message[i+6] + "|" + df.format(hours) + "|" + df.format(hours*pay)+ "|" + message[i+9]);
					writer.write(message[i+7] + "|" + message[i+2] + "|" + message[i+6] + "|" + df.format(hours) + "|" + df.format(hours*pay)+ "|" + message[i+9]);
					i = i+10;
					writer.newLine();
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
		
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("The writer didn't close... wtf");
			e.printStackTrace();
		}
	}

}
