package sfproj.server;

import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import com.mysql.jdbc.PreparedStatement;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class ServerNetHandler extends AbstractServer{

	private static final String url = "jdbc:mysql://144.217.243.209:3306/software?useSSL=true";
	private static final String user = "software";
	private static final String password = "letmeinplease";
	
	private static Connection con;
	private static Statement stmt;
	private static String sStmt;
	private static ResultSet rs;
	private static java.sql.PreparedStatement ps;
	
	public ServerNetHandler(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println(msg);
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			String message[] = ((String) msg).split("\\|");
			//Need to change this to a switch statement probably
			if(message[0].equals("AddEmployee")){
				sStmt = "INSERT INTO employee(name,departmentID,pay,rank) VALUES(?,?,?,?)";
				ps = con.prepareStatement(sStmt);
				ps.setString(1, message[1]);
				ps.setString(2, message[2]);
				ps.setFloat(3, Float.parseFloat(message[3]));
				ps.setString(4, message[4]);
				ps.executeUpdate();
			}
			else if(message[0].equals("AddDepartment")){
				sStmt = "INSERT INTO department(name,type) VALUES(?,?)";
				ps = con.prepareStatement(sStmt);
				ps.setString(1, message[1]);
				ps.setString(2, message[2]);
				ps.executeUpdate();
			}
			else if(message[0].equals("RequestDepartment")){
				sStmt = "SELECT * FROM department";
				rs = stmt.executeQuery(sStmt);
				String tempString = "DepartmentList|";
				ResultSet res;
				int count = 0;
				while(rs.next()){
					sStmt = "SELECT Count(*) FROM employee WHERE departmentID=?";
					ps = con.prepareStatement(sStmt);
					ps.setString(1, rs.getString(1));
					res = ps.executeQuery();
					while(res.next()){
						count = res.getInt(1);
					}
					tempString = tempString + rs.getString(1) + "|" + rs.getString(2) + "|" + count + "|"+ rs.getString("type") + "|";
				}
				try {
					client.sendToClient(tempString);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Send to client error");
				}
			}
			else if(message[0].equals("RequestEmployee")){
				sStmt = "SELECT * FROM employee";
				rs = stmt.executeQuery(sStmt);
				String tempString = "EmployeeList|";
				while(rs.next()){
					tempString = tempString + rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getInt(3) + "|" + rs.getDouble(4) + "|"+ rs.getInt(5) + "|";
				}
				try {
					client.sendToClient(tempString);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Send to client error");
				}
			}
			else if(message[0].equals("RequestRank")){
				sStmt = "SELECT * FROM ranks";
				rs = stmt.executeQuery(sStmt);
				String tempString = "RankList|";
				while(rs.next()){
					tempString = tempString + rs.getString(1) + "|" + rs.getString(2) + "|";
				}
				try {
					client.sendToClient(tempString);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Send to client error");
				}
			}
			else if(message[0].equals("ClockIn")){
				String userId = message[1].substring(3, 7);
				String type = "";
				String theType = "";
				String getClockStmt = "SELECT * FROM clock WHERE eID=? ORDER BY theTime DESC LIMIT 1";
				ps = con.prepareStatement(getClockStmt);
				ps.setInt(1, Integer.parseInt(userId));
				rs = ps.executeQuery();
				while(rs.next()){
					type = rs.getString("type");
				}
				if(type.equals("IN")){
					try {
						client.sendToClient("Error|1");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					if(message[2].equals("1")){
						theType = "Call-Back";
					}
					else{
						theType = "Regular";
					}
					sStmt = "INSERT INTO clock(eId,type,theType) VALUES(?,?,?)";
					ps = con.prepareStatement(sStmt);
					ps.setInt(1, Integer.parseInt(userId));
					ps.setString(2, "IN");
					ps.setString(3, theType); 
					ps.executeUpdate();
				}
			}
			else if(message[0].equals("ClockOut")){
				String userId = message[1].substring(3, 7);
				String type = "";
				String theType = "";
				String getClockStmt = "SELECT * FROM clock WHERE eID=? ORDER BY theTime DESC LIMIT 1";
				ps = con.prepareStatement(getClockStmt);
				ps.setInt(1, Integer.parseInt(userId));
				rs = ps.executeQuery();
				while(rs.next()){
					type = rs.getString("type");
				}
				if(type.equals("OUT")){
					try {
						client.sendToClient("Error|2");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					if(message[2].equals("1")){
						theType = "Call-Back";
					}
					else{
						theType = "Regular";
					}
					sStmt = "INSERT INTO clock(eId,type,theType) VALUES(?,?,?)";
					ps = con.prepareStatement(sStmt);
					ps.setInt(1, Integer.parseInt(userId));
					ps.setString(2, "OUT");
					ps.setString(3, theType); 
					ps.executeUpdate();
				}
			}
			else if(message[0].equals("RequestTimes")){
				sStmt = "SELECT * FROM clockStuff WHERE eId=? ORDER BY id DESC";
				ps = con.prepareStatement(sStmt);
				ps.setInt(1, Integer.parseInt(message[1]));
				rs = ps.executeQuery();
				String tempString = "TimeList|";
				while(rs.next()){
					tempString = tempString + rs.getInt("id") + "|" + rs.getString("type") + "|" + rs.getDate("Date") + "|" + rs.getTime("Time") + "|" + rs.getDouble("pay") + "|" + rs.getString("theType") + "|";
				}
				try {
					client.sendToClient(tempString);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Send to client error");
				}
			}
			else if(message[0].equals("RequestFullTimes")){
				sStmt = "SELECT * FROM clockStuff ORDER BY id DESC";
				rs = stmt.executeQuery(sStmt);
				String tempString = "FullTimeList|";
				while(rs.next()){
					tempString = tempString + rs.getInt("eId") + "|" + rs.getString("type") + "|" + rs.getDate("Date") + "|" + rs.getTime("Time") + "|" + rs.getDouble("pay") + "|" + rs.getString("theType") + "|";
				}
				try {
					client.sendToClient(tempString);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Send to client error");
				}
			}
			else if(message[0].equals("Login")){
				String userId = message[1].substring(3, 7);
				int deptId = Integer.parseInt(message[1].substring(0, 3));
				System.out.println(userId + " | " + deptId);
				String name ="";
				String rank ="";
				sStmt = "SELECT * FROM employee WHERE id=? AND departmentID=?";
				ps = con.prepareStatement(sStmt);
				ps.setString(1, userId);
				ps.setInt(2, deptId);
				rs = ps.executeQuery();
				while(rs.next()){
					name = rs.getString("name");
					rank = rs.getString("rank");
				}
				if(name.isEmpty()){
					client.sendToClient("Login|Fail");
				}
				else if(rank.equals("3")){
					client.sendToClient("Login|Fail|3");
				}
				else{
					client.sendToClient("Login|Success|" + rank);
				}
			}
			else if(message[0].equals("AddClock")){
				int userId = Integer.parseInt(message[1]);
				Double hours = Double.parseDouble(message[3]);
				Timestamp in, out = Timestamp.valueOf(message[2] + " 00:00:00");
				String type = message[4];
				long milSec = 0;
				if(hours > 4){
					hours = hours + 0.5;
				}
				milSec  = (long) (hours*1000*60*60);
				in = Timestamp.valueOf(message[2] + " 00:00:00");
				milSec = milSec + in.getTime();
				out.setTime(milSec);
				sStmt = "Insert INTO clock(eId, type, theTime, theType) VALUES(?,?,?,?)";
				ps = con.prepareStatement(sStmt);
				ps.setInt(1, userId);
				ps.setString(2, "IN");
				ps.setTimestamp(3, in);
				ps.setString(4, type);
				ps.executeUpdate();
				
				sStmt = "Insert INTO clock(eId, type, theTime, theType) VALUES(?,?,?,?)";
				ps = con.prepareStatement(sStmt);
				ps.setInt(1, userId);
				ps.setString(2, "OUT");
				ps.setTimestamp(3, out);
				ps.setString(4, type);
				ps.executeUpdate();
			}
			else if(message[0].equals("EditClock")){
				String[] idLine = ((String) message[1]).split("-");
				int eId = Integer.parseInt(idLine[0]);
				int outId = Integer.parseInt(idLine[1]); //I think these might be backwards
				int inId = Integer.parseInt(idLine[2]);
				System.out.println(eId + " | " + outId+ " | " + inId);
				//System.out.println(message[2] + " | " + message[3]);
				Timestamp in = Timestamp.valueOf(message[5] + " " + message[3]);
				Timestamp out = Timestamp.valueOf(message[4] + " " + message[2]);
				System.out.println(in + " | " + out);
				sStmt = "UPDATE clock SET theTime=?, theType=? WHERE id=?";
				ps = con.prepareStatement(sStmt);
				ps.setTimestamp(1, out);
				ps.setString(2, message[6]);
				ps.setInt(3, inId);
				ps.executeUpdate();
				
				sStmt = "UPDATE clock SET theTime=?, theType=? WHERE id=?";
				ps = con.prepareStatement(sStmt);
				ps.setTimestamp(1, in);
				ps.setString(2, message[6]);
				ps.setInt(3, outId);
				ps.executeUpdate();
			}
			else if(message[0].equals("Retire")){
				int userId = Integer.parseInt(message[1]);
				Double hours = Double.parseDouble(message[2]);
				LocalDate today = LocalDate.now();
				Timestamp in, out = Timestamp.valueOf(today.toString() + " 00:00:00");
				String type = "Regular";
				long milSec = 0;
				if(hours > 4){
					hours = hours + 0.5;
				}
				milSec  = (long) (hours*1000*60*60);
				in = Timestamp.valueOf(today.toString() + " 00:00:00");
				milSec = milSec + in.getTime();
				out.setTime(milSec);
				sStmt = "Insert INTO clock(eId, type, theTime, theType) VALUES(?,?,?,?)";
				ps = con.prepareStatement(sStmt);
				ps.setInt(1, userId);
				ps.setString(2, "IN");
				ps.setTimestamp(3, in);
				ps.setString(4, type);
				ps.executeUpdate();
				
				sStmt = "Insert INTO clock(eId, type, theTime, theType) VALUES(?,?,?,?)";
				ps = con.prepareStatement(sStmt);
				ps.setInt(1, userId);
				ps.setString(2, "OUT");
				ps.setTimestamp(3, out);
				ps.setString(4, type);
				ps.executeUpdate();
				
				sStmt = "UPDATE employee SET rank=3 WHERE id=?";
				ps = con.prepareStatement(sStmt);
				ps.setInt(1, userId);
				ps.executeUpdate();
			}
			
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Db Error");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
	}

}
