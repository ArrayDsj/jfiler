package net.wisedream.jfiler.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;

import net.wisedream.common.cli.CLIOption;
import net.wisedream.common.cli.CLIOptions;
import net.wisedream.common.cli.CLIOptionsProcessor;
import net.wisedream.jfiler.Const;

@CLIOptions
public class ServerConfig {
	@CLIOption(argName = "PORT", hasArg = true, longOpt = "port", opt = "p", desc = "Port of this Server instance listens on. "
			+ Const.DEFAULT_SERVER_PORT + " wile be used by default")
	private int port = Const.DEFAULT_SERVER_PORT;
	@CLIOption(argName = "DIR", hasArg = true, longOpt = "dir", opt = "d", desc = "Working directory to receive files. "
			+ "Current directory will be used by default")
	private File dir;
	@CLIOption(argName = "KEY", hasArg = true, isRequired = true, longOpt = "key", opt = "k", desc = "Key to verify clients")
	private String key;

	public void launch() throws IOException {
		if (!checkConfig())
			System.exit(1);
		System.out.println("Server's starting with config: " + this.toString());
		ServerSocket ssocket = new ServerSocket(port);
		while (true) {
			Socket con = ssocket.accept();
			new Thread(new FileReceiver(con, this)).start();
		}
	}

	/**
	 * Configure working environment:<br/>
	 * 1.check if working directory exists and is a directory, if not specified
	 * current directory will be used.<br/>
	 * 2.check whether key is specified
	 * 
	 * @return true if succeed
	 */
	private boolean checkConfig() {
		boolean result = true;
		if (!dir.exists()) {
			System.out.println("working directory doesn't exist, creating...");
			if (!dir.mkdirs()) {
				System.err.println("create working directory failed.");
				result = false;
			}
		}
		if (dir.isFile()) {
			System.err.println("'" + dir.getAbsolutePath()
					+ "' is not a directory");
			result = false;
		}
		if (key == null) {
			System.err.println("key should not be null");
			result = false;
		}
		return result;
	}
	public static ServerConfig parseOptions(String[] args) {
		// parse
		try {
			CommandLine cmd = new BasicParser().parse(
					CLIOptionsProcessor.process(ServerConfig.class), args);
			if (cmd.hasOption('h'))
				showHelp();
			String port = cmd.getOptionValue('p');
			if (port == null)
				port = "8888";
			String dir = cmd.getOptionValue('d');
			if (dir == null)
				dir = ".";
			String key = cmd.getOptionValue('k');
			return new ServerConfig(Integer.parseInt(port), new File(dir), key);
		} catch (Exception e) {
			showHelp();
		}
		return null;
	}
	
	private static void showHelp() {
		HelpFormatter hf = new HelpFormatter();
		hf.printHelp("java -jar jfiler.jar Server [OPTIONS]", OPTIONS, false);
		System.exit(0);
	}
	
	@Override
	public String toString() {
		return "[port=" + port + ", dir=" + dir + ", key=" + key + "]";
	}

}
