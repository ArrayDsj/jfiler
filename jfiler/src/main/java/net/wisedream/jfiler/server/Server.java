package net.wisedream.jfiler.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private int port;
	private File dir;
	private String key;

	public Server(int port, File dir, String key) {
		super();
		this.port = port;
		this.dir = dir;
		this.key = key;
	}

	public void launch() throws IOException {
		if(!checkConfig())
			System.exit(1);
		System.out.println("Server's starting with config: "+this.toString());
		ServerSocket ssocket = new ServerSocket(port);
		while (true) {
			Socket con = ssocket.accept();
			new Thread(new FileReceiver(con,this)).start();
		}
	}

	/**
	 * Configure working environment:<br/>
	 * 1.check if working directory exists and is a directory, if not specified
	 * current directory will be used.<br/>
	 * 2.check whether key is specified
	 * 
	 * @return true if succeed
	 */
	private boolean checkConfig() {
		boolean result = true;
		if (!dir.exists()) {
			System.out.println("working directory doesn't exist, creating...");
			if (!dir.mkdirs()) {
				System.err.println("create working directory failed.");
				result=false;
			}
		}
		if (dir.isFile()) {
			System.err.println("'" + dir.getAbsolutePath()
					+ "' is not a directory");
			result=false;
		}
		if(key==null){
			System.err.println("key should not be null");
			result=false;
		}
		return result;
	}

	@Override
	public String toString() {
		return "[port=" + port + ", dir=" + dir + ", key=" + key + "]";
	}

	public int getPort() {
		return port;
	}

	public File getDir() {
		return dir;
	}

	public String getKey() {
		return key;
	}
	
}
