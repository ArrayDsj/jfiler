package net.wisedream.jfiler.client.send;

import java.net.Socket;

import net.wisedream.jfiler.ClientConfig;
import net.wisedream.tasklet.manager.StackManager;

public class SendManager extends StackManager {

	@Override
	public void onStart() {
		ClientConfig config = context.getAttrib("config");
		config.checkConfig();
		addTask(new Send01());
	}

	@Override
	public void onFinish() {
		Socket connection = context.getAttrib("connection");
		if (connection != null)
			try {
				connection.close();
			} catch (Exception e) {
			}
	}
}
