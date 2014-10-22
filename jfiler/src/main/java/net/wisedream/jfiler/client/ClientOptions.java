package net.wisedream.jfiler.client;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

@SuppressWarnings("static-access")
public class ClientOptions {
	private static final Options OPTIONS = new Options();
	static {
		Option host = OptionBuilder
				.withArgName("HOST [PORT]")
				.isRequired()
				.hasArgs()
				.withDescription(
						"Server's host name and port. "
								+ "Default port value is 8888")
				.withLongOpt("host").create('H');
		Option key = OptionBuilder
				.withArgName("KEY")
				.isRequired()
				.hasArg()
				.withDescription("server-side specified key for connection")
				.withLongOpt("key").create('k');
		Option file = OptionBuilder
				.withArgName("FILE")
				.isRequired()
				.hasArg()
				.withDescription("File to be transferred")
				.withLongOpt("file").create('f');
		Option help = OptionBuilder.withDescription("show help info")
				.withLongOpt("help").create('h');
		OPTIONS.addOption(host);
		OPTIONS.addOption(key);
		OPTIONS.addOption(file);
		OPTIONS.addOption(help);
	}

	public static Client parseOptions(String[] args) {
		// parse
		try {
			CommandLine cmd = new BasicParser().parse(OPTIONS, args);
			if (cmd.hasOption('h'))
				showHelp();
			String[] hostOption = cmd.getOptionValues('H');
			String host = hostOption[0];
			String port = hostOption.length > 1 ? hostOption[1] : "8888";
			String key = cmd.getOptionValue('k');
			String fileName = cmd.getOptionValue('f');
			return new Client(host, Integer.parseInt(port), key, fileName);
		} catch (Exception e) {
			showHelp();
		}
		return null;
	}

	private static void showHelp() {
		HelpFormatter hf = new HelpFormatter();
		hf.printHelp("java -jar jfiler.jar Client [OPTIONS]", OPTIONS, false);
		System.exit(0);
	}
}
