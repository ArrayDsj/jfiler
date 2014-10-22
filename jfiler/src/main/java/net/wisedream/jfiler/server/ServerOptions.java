package net.wisedream.jfiler.server;

import java.io.File;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

@SuppressWarnings("static-access")
public class ServerOptions {
	private static final Options OPTIONS = new Options();
	static {
		Option port = OptionBuilder
				.withArgName("PORT")
				.hasArg()
				.withDescription(
						"port of this Server instance listens on. "
								+ "Default value is 8888")
				.withLongOpt("port")
				.create('p');
		Option dir = OptionBuilder
				.withArgName("DIR")
				.hasArg()
				.withDescription(
						"working directory to receive files."
								+ "If not specified, current directory will be used")
				.withLongOpt("dir")
				.create('d');
		Option key = OptionBuilder
				.withArgName("KEY")
				.isRequired()
				.hasArg()
				.withDescription("used to verify connections").isRequired()
				.withLongOpt("key")
				.create('k');
		Option help = OptionBuilder
				.withDescription("show help info")
				.withLongOpt("help")
				.create('h');
		OPTIONS.addOption(port);
		OPTIONS.addOption(dir);
		OPTIONS.addOption(key);
		OPTIONS.addOption(help);
	}


	public static Server parseOptions(String[] args) {
		// parse
		try {
			CommandLine cmd = new BasicParser().parse(OPTIONS, args);
			if(cmd.hasOption('h'))
				showHelp();
			String port = cmd.getOptionValue('p');
			if(port==null)
				port="8888";
			String dir = cmd.getOptionValue('d');
			if(dir==null)
				dir=".";
			String key = cmd.getOptionValue('k');
			return new Server(Integer.parseInt(port), new File(dir), key);
		} catch (Exception e) {
			showHelp();
		}
		return null;
	}
	private static void showHelp(){
		HelpFormatter hf = new HelpFormatter();
		hf.printHelp("java -jar jfiler.jar Server [OPTIONS]", OPTIONS, false);
		System.exit(0);
	}
}
