package net.wisedream.jfiler.client.send;

import java.io.DataInputStream;
import java.net.Socket;

import net.wisedream.jfiler.Const;
import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

/**
 * check md5
 * 
 * @author zxp
 * @Created Oct 21, 2014
 */
public class Send05 extends Task {

	@Override
	public void perform(Manager manager) {
		Socket connection = context.getAttrib("connection");
		System.out.print("Check MD5 ... ");
		DataInputStream in = null;
		try {
			in = new DataInputStream(connection.getInputStream());
			int result = in.readInt();
			if (result == Const.ACTION_SUCCESS)
				System.out.println("[OK]");
			else
				System.out.println(" MD5 not match, file may be corrupt.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
