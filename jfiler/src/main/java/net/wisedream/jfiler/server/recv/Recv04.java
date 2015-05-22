package net.wisedream.jfiler.server.recv;

import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;

import net.wisedream.jfiler.Const;
import net.wisedream.jfiler.ServerConfig;
import net.wisedream.jfiler.util.StreamUtil;
import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

/**
 * check md5
 * 
 * @author zxp
 * @Created Oct 21, 2014
 */
public class Recv04 extends Task {

	@Override
	public void perform(Manager manager) {
		Socket connection = context.getAttrib("connection");
		String fileName = context.getAttrib("fileName");
		ServerConfig config = context.getAttrib("config");
		String md5 = context.getAttrib("md5");
		i("Checking MD5...");
		DataOutputStream out = null;
		try {
			File file = new File(config.getDir(), fileName);
			out = new DataOutputStream(connection.getOutputStream());
			String localMD5 = StreamUtil.convertToHexString(StreamUtil
					.calcMD5(file));
			// check
			if (md5.equals(localMD5)) {
				((RecvManager) manager).setResult(true);
				out.writeInt(Const.ACTION_SUCCESS);
			} else {
				e("MD5 not match!");
				out.writeInt(Const.ACTION_FAIL);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
