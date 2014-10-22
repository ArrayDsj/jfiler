package net.wisedream.jfiler.server.recv;

import java.io.DataInputStream;
import java.net.Socket;

import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

/**
 * receive file info
 * 
 * @author zxp
 * @Created Oct 20, 2014
 */
public class Recv02 extends Task {

	@Override
	public void perform(Manager manager) {
		Socket connection = context.getAttrib("connection");
		i("Receiving file info...");
		DataInputStream in = null;
		try {
			in = new DataInputStream(connection.getInputStream());
			String fileName = in.readUTF();
			Long fileSize = in.readLong();
			String md5 = in.readUTF();
			i("File Name: " + fileName + ", Size: " + fileSize + ", MD5: "
					+ md5);
			context.putAttrib("fileName", fileName);
			context.putAttrib("fileSize", fileSize);
			context.putAttrib("md5", md5);
			manager.addTask(new Recv03());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
