package sfproj.server;

import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
					sStmt = "INSERT INTO clock(eId,type,rank) VALUES(?,?,?)";
					ps = con.prepareStatement(sStmt);
					ps.setInt(1, Integer.parseInt(userId));
					ps.setString(2, "IN");
					ps.setInt(3, Integer.parseInt(message[2])); 
					ps.executeUpdate();
				}
			}
			else if(message[0].equals("ClockOut")){
				String userId = message[1].substring(3, 7);
				String type = "";
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
					sStmt = "INSERT INTO clock(eId,type,rank) VALUES(?,?,?)";
					ps = con.prepareStatement(sStmt);
					ps.setInt(1, Integer.parseInt(userId));
					ps.setString(2, "OUT");
					ps.setInt(3, Integer.parseInt(message[2])); 
					ps.executeUpdate();
				}
			}
			else if(message[0].equals("RequestTimes")){
				sStmt = "SELECT * FROM clockStuff WHERE eId=? ORDER BY Date, Time DESC";
				ps = con.prepareStatement(sStmt);
				ps.setInt(1, Integer.parseInt(message[1]));
				rs = ps.executeQuery();
				String tempString = "TimeList|";
				while(rs.next()){
					tempString = tempString + rs.getString("type") + "|" + rs.getDate("Date") + "|" + rs.getTime("Time") + "|" + rs.getDouble("pay") + "|" + rs.getInt("callBack") + "|";
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
				else{
					client.sendToClient("Login|Success|" + rank);
				}
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
