package net.wisedream.jfiler.server.recv;

import net.wisedream.common.tm.ContextualKeys;
import net.wisedream.common.tm.Task;
import net.wisedream.common.tm.TaskContext;
import net.wisedream.jfiler.Const;
import net.wisedream.jfiler.ServerConfig;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * check key
 * 
 * @author zxp
 * @Created Oct 20, 2014
 */
public class Recv01 implements Task {

    @Override
    public Task perform(TaskContext context) throws Exception {
       Socket connection = context.getContextualObject(ContextualKeys.OBJ_SOCKET);
		context.i("Checking key...");
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			in = new DataInputStream(connection.getInputStream());
			out = new DataOutputStream(connection.getOutputStream());
			// check key
			String key = ((ServerConfig) context.getAttrib("config")).getKey();
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
