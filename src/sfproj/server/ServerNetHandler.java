package sfproj.server;

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
				sStmt = "INSERT INTO employee(name,departmentName,pay) VALUES(?,?,?)";
				ps = con.prepareStatement(sStmt);
				ps.setString(1, message[1]);
				ps.setString(2, message[2]);
				ps.setFloat(3, Float.parseFloat(message[3]));
				ps.executeUpdate();
			}
			else if(message[0].equals("AddDepartment")){
				sStmt = "INSERT INTO department(name) VALUES(?)";
				ps = con.prepareStatement(sStmt);
				ps.setString(1, message[1]);
				ps.executeUpdate();
			}
			else if(message[0].equals("RequestDepartment")){
				sStmt = "SELECT * FROM department";
				rs = stmt.executeQuery(sStmt);
				String tempString = "DepartmentList|";
				while(rs.next()){
					tempString = tempString + rs.getString(2) + "|15|";
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
					tempString = tempString + rs.getString(2) + "|" + rs.getString(3) + "|" + rs.getDouble(4) + "|";
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
				//client.setName(message[1]);
				//System.out.println(client.getName());
				try {
					client.sendToClient("Test");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(message[0].equals("ClockIn")){
				String type = "";
				String getClockStmt = "SELECT * FROM clock WHERE eID=? ORDER BY theTime DESC LIMIT 1";
				ps = con.prepareStatement(getClockStmt);
				ps.setString(1, message[1]);
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
					ps.setString(1, message[1]);
					ps.setString(2, "IN");
					ps.setInt(3, 0); 
					ps.executeUpdate();
				}
			}
			else if(message[0].equals("ClockOut")){
				String type = "";
				String getClockStmt = "SELECT * FROM clock WHERE eID=? ORDER BY theTime DESC LIMIT 1";
				ps = con.prepareStatement(getClockStmt);
				ps.setString(1, message[1]);
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
					ps.setString(1, message[1]);
					ps.setString(2, "OUT");
					ps.setInt(3, 0); //This needs to be changed later to get the user rank.
					ps.executeUpdate();
				}
			}
			
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Db Error");
		}
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
	}

}
