package net.wisedream.jfiler.server.recv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;

import net.wisedream.jfiler.Const;
import net.wisedream.jfiler.ServerConfig;
import net.wisedream.jfiler.util.StreamUtil;
import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

/**
 * Receive file
 * 
 * @author zxp
 * @Created Oct 21, 2014
 */
public class Recv03 extends Task {

	@Override
	public void perform(Manager manager) {
		Socket connection = context.getAttrib("connection");
		String fileName = context.getAttrib("fileName");
		Long fileSize = context.getAttrib("fileSize");
		ServerConfig config = context.getAttrib("config");
		i("Receiving " + fileName + " ...");
		InputStream in = null;
		FileOutputStream out = null;
		try {
			in = connection.getInputStream();
			File file = new File(config.getDir(), fileName);
			int i = 1;
			while (file.exists()) {
				file = new File(config.getDir(), fileName + "(" + i++ + ")");
			}
			i("Saving file [" + fileName + "]" + " as [" + file.getName() + "]");
			out = new FileOutputStream(file);
			StreamUtil.writeData(in, out, Const.BUFFER_SIZE, fileSize);
			context.putAttrib("fileName", file.getName());
			manager.addTask(new Recv04());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			StreamUtil.closeStream(out);
		}
	}

}
