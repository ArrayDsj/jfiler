package net.wisedream.jfiler.client.send;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import net.wisedream.jfiler.Const;
import net.wisedream.jfiler.ClientConfig;
import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

/**
 * check key with server
 * 
 * @author zxp
 * @Created Oct 20, 2014
 */
public class Send02 extends Task {

	@Override
	public void perform(Manager manager) {
		Socket connection = context.getAttrib("connection");
		System.out.print("Checking key with server... ");
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			in = new DataInputStream(connection.getInputStream());
			out = new DataOutputStream(connection.getOutputStream());
			// send local key to server
			String key = ((ClientConfig) context.getAttrib("config")).getKey();
			out.writeUTF(key);
			out.flush();
			int result = in.readInt();
			if (result == Const.ACTION_SUCCESS) {
				manager.addTask(new Send03());
				System.out.println("[OK]");
			} else
				System.out.println("[Invalid Key]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
