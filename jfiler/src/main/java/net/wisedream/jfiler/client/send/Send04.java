package net.wisedream.jfiler.client.send;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

import net.wisedream.jfiler.Const;
import net.wisedream.jfiler.ClientConfig;
import net.wisedream.jfiler.util.StreamUtil;
import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

/**
 * transfer file
 * 
 * @author zxp
 * @Created Oct 21, 2014
 */
public class Send04 extends Task {

	@Override
	public void perform(Manager manager) {
		Socket connection = context.getAttrib("connection");
		System.out.print("Sending file... 0%");
		OutputStream out = null;
		try {
			out = connection.getOutputStream();
			ClientConfig config = context.getAttrib("config");
			File file = new File(config.getFileName());
			sendFileWithProgress(file, new BufferedOutputStream(out));
			manager.addTask(new Send05());
		} catch (Exception e) {
			System.err.println(" Transfer failed");
			e.printStackTrace();
		}
	}

	private void sendFileWithProgress(File file, BufferedOutputStream out)
			throws Exception {
		long totalSize = file.length();
		long readSize = 0;
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[Const.BUFFER_SIZE];
		int i;
		while ((i = in.read(buffer)) > 0) {
			readSize += i;
			out.write(buffer, 0, i);
			showProgress((int) (readSize * 100 / totalSize));
		}
		out.flush();
		System.out.println();
		StreamUtil.closeStream(in);
	}

	private void showProgress(int progress) {
		// back 3 characters
		System.out.print("\b\b\b");
		System.out.print((progress < 10 ? " " : "") + progress + "%");
	}

}
