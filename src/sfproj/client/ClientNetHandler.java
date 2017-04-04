package sfproj.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
		else if(message[0].equals("EmployeeList")){
			try {
				writer = new BufferedWriter(new FileWriter(new File("src/sfproj/client/dataSet/employeeList.txt")));
				for(int i=1; i<message.length-1;){
					writer.write(message[i] + "|" + message[i+1] + "|" + message[i+2]);
					System.out.println("Wrote: " + message[i] + "|" + message[i+1] + "|" + message[i+2]);
					i = i+3;
					writer.newLine();
				}
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
