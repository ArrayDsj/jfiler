package net.wisedream.jfiler;

import java.util.Arrays;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import net.wisedream.common.cli.CLIOptionsProcessor;
import net.wisedream.jfiler.client.ClientOptions;
import net.wisedream.jfiler.client.FileSender;
import net.wisedream.jfiler.server.ServerConfig;
import net.wisedream.jfiler.server.ServerOptions;
import net.wisedream.tasklet.Launcher;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws Exception {
		if (args.length < 1)
			showHelp();
		getLauncher(args).launch();
	}
	private static Launcher getLauncher(String[] args) {
		String role = args[0];
		String[] optionArgs = Arrays.copyOfRange(args, 1, args.length);
		Options options = null;
		if (role.equalsIgnoreCase("Server") || role.equalsIgnoreCase("S"))
//			ServerOptions.parseOptions(options).launch();
			options = CLIOptionsProcessor.process(ServerConfig.class);
		else if (role.equalsIgnoreCase("Client") || role.equalsIgnoreCase("C"))
			options = CLIOptionsProcessor.process(ClientOptions.class);
			new FileSender(ClientOptions.parseOptions(options)).launch();
		else
			showHelp();
	}
	private static void showHelp() {
		System.out
				.println("use 'java -jar jfile.jar S[erver]|C[lient] -h' for help.");
		System.exit(0);
	}
}
