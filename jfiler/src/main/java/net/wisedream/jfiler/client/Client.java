package net.wisedream.jfiler.client;

import java.io.File;

public class Client {
	private String host;
	private int port;
	private String key;
	private String fileName;

	public Client(String host, int port, String key, String fileName) {
		super();
		this.host = host;
		this.port = port;
		this.key = key;
		this.fileName = fileName;
	}

	public void checkConfig() {
		if (this.fileName == null) {
			System.err.println("please specify a file");
			System.exit(1);
		}
		File file = new File(fileName);
		if (!new File(fileName).exists()) {
			System.err.println("non-existing file: " + fileName);
			System.exit(1);
		}
		if(file.isDirectory()){
			System.err.println(fileName+" is not a file.");
			System.exit(1);
		}
		System.out.println("Client starting with config: " + this.toString());
	}

	@Override
	public String toString() {
		return "[host=" + host + ", port=" + port + ", key=" + key
				+ ", fileName=" + fileName + "]";
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getKey() {
		return key;
	}

	public String getFileName() {
		return fileName;
	}

}
