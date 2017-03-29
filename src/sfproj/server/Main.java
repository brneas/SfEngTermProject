package sfproj.server;

import java.io.IOException;

public class Main {

	static ServerNetHandler handler;

	public static void main(String[] args) {
		handler = new ServerNetHandler(800);
		try {
			handler.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
