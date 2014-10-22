package net.wisedream.jfiler.server;

import java.net.Socket;

import net.wisedream.jfiler.server.recv.RecvManager;
import net.wisedream.tasklet.Context;
import net.wisedream.tasklet.Launcher;
import net.wisedream.tasklet.Task;

public class FileReceiver extends Launcher implements Runnable {
	private Socket connection;
	private Server config;

	public FileReceiver(Socket connection, Server config) {
		super();
		this.connection = connection;
		this.config = config;
	}

	public void run() {
		this.launch();
	}

	@Override
	public Context configureContext() {
		return new Context()
			.withAttrib("config", config)
			.withAttrib("connection", connection);
	}

	@Override
	public Task configureTask() {
		return new RecvManager(connection);
	}

}
