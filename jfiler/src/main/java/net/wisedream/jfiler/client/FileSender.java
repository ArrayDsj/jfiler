package net.wisedream.jfiler.client;

import net.wisedream.jfiler.client.send.SendManager;
import net.wisedream.tasklet.Context;
import net.wisedream.tasklet.Launcher;
import net.wisedream.tasklet.Task;

public class FileSender extends Launcher {
	private Client config;

	public FileSender(Client config) {
		this.config = config;
	}

	@Override
	public Context configureContext() {
		return new Context().withAttrib("config", this.config);
	}

	@Override
	public Task configureTask() {
		return new SendManager();
	}

}
