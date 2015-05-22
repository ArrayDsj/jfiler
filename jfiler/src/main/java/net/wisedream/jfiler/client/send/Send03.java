package net.wisedream.jfiler.client.send;

import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;

import net.wisedream.jfiler.ClientConfig;
import net.wisedream.jfiler.util.StreamUtil;
import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

/**
 * send file info
 * 
 * @author zxp
 * @Created Oct 20, 2014
 */
public class Send03 extends Task {
	@Override
	public void perform(Manager manager) {
		Socket connection = context.getAttrib("connection");
		System.out.print("Sending file info... ");
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(connection.getOutputStream());
			ClientConfig config = context.getAttrib("config");
			File file = new File(config.getFileName());
			out.writeUTF(file.getName());
			out.writeLong(file.length());
			out.writeUTF(StreamUtil.convertToHexString(StreamUtil.calcMD5(file)));
			out.flush();
			manager.addTask(new Send04());
			System.out.println("[OK]");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
