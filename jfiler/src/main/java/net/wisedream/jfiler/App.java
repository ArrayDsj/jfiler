package net.wisedream.jfiler;

import java.util.Arrays;

import net.wisedream.jfiler.client.ClientOptions;
import net.wisedream.jfiler.client.FileSender;
import net.wisedream.jfiler.server.ServerOptions;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws Exception {
		if(args.length<1)
			showHelp();
		String role = args[0];
		String[] options = Arrays.copyOfRange(args, 1, args.length);
		if(role.equalsIgnoreCase("Server"))
			ServerOptions.parseOptions(options).launch();
		else if(role.equalsIgnoreCase("Client"))
			new FileSender(ClientOptions.parseOptions(options)).launch();
		else
			showHelp();
	}
	private static void showHelp(){
		System.out.println("use 'java-jar jfile.jar Server|Client -h' for help.");
		System.exit(0);
	}
}
