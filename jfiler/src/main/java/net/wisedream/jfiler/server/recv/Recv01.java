package net.wisedream.jfiler.server.recv;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import net.wisedream.jfiler.Const;
import net.wisedream.jfiler.server.Server;
import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

/**
 * check key
 * 
 * @author zxp
 * @Created Oct 20, 2014
 */
public class Recv01 extends Task {

	@Override
	public void perform(Manager manager) {
		Socket connection = context.getAttrib("connection");
		i("Checking key...");
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			in = new DataInputStream(connection.getInputStream());
			out = new DataOutputStream(connection.getOutputStream());
			// check key
			String key = ((Server) context.getAttrib("config")).getKey();
			String clientKey = in.readUTF();
			if (clientKey.equals(key)) {
				manager.addTask(new Recv02());
				out.writeInt(Const.ACTION_SUCCESS);
			} else {
				e("key not math");
				out.writeInt(Const.ACTION_FAIL);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
