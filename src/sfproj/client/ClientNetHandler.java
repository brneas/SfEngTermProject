package sfproj.client;

import java.io.IOException;

import ocsf.client.AbstractClient;

public class ClientNetHandler extends AbstractClient{

	public ClientNetHandler(String host, int port) throws IOException {
		super(host, port);
		openConnection();
	}

	@Override
	protected void handleMessageFromServer(Object msg) {

	}

}
