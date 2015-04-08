package net.wisedream.jfiler.server.recv;

import java.io.File;
import java.net.Socket;

import net.wisedream.jfiler.server.ServerConfig;
import net.wisedream.tasklet.manager.StackManager;

public class RecvManager extends StackManager {
	public Socket manager;
	private boolean result;

	public RecvManager(Socket manager) {
		super();
		this.manager = manager;
	}

	@Override
	public void onStart() {
		addTask(new Recv01());
	}

	@Override
	public void onFinish() {
		Socket connection = context.getAttrib("connection");
		if (connection != null)
			try {
				connection.close();
			} catch (Exception e) {
			}
		if (result)
			i("OK");
		else {
			e("File not reveived, deleting temp file...");
			String fileName = context.getAttrib("fileName");
			if (fileName != null) {
				ServerConfig config = context.getAttrib("config");
				File file = new File(config.getDir(), fileName);
				if (file.exists())
					file.delete();
			}
		}
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

}
