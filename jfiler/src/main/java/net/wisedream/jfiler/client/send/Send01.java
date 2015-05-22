package net.wisedream.jfiler.client.send;

import java.net.Socket;

import net.wisedream.jfiler.ClientConfig;
import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

/**
 * connect to server
 * 
 * @author zxp
 * @Created Oct 20, 2014
 */
public class Send01 extends Task {

	@Override
	public void perform(Manager manager) {
		System.out.print("Connecting to server... ");
		ClientConfig config = context.getAttrib("config");
		try {
			Socket connection = new Socket(config.getHost(), config.getPort());
			context.putAttrib("connection", connection);
			manager.addTask(new Send02());
			System.out.println("[OK]");
		} catch (Exception e) {
			System.err.println(" Cannot connect to "+config.getHost());
		}

	}

}
