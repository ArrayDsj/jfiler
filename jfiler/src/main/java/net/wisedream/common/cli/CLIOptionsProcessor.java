package net.wisedream.common.cli;

import java.lang.reflect.Field;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

public class CLIOptionsProcessor {

	public static Options process(Class<?> cliClz) {
		if (cliClz.getAnnotation(CLIOptions.class) == null)
			throw new RuntimeException(String.format(
					"Target class: %s is not annotated with CLIOptions",
					cliClz.getCanonicalName()));
		Options options = new Options();
		CLIOption cli = null;
		for (Field field : cliClz.getDeclaredFields()) {
			cli = field.getAnnotation(CLIOption.class);
			if (cli != null)
				options.addOption(parseOption(field, cli));
		}
		return options;
	}

	private static Option parseOption(Field field, CLIOption cli) {
		if (cli.hasArg())
			OptionBuilder.hasArg();
		if (cli.hasArgs())
			OptionBuilder.hasArgs();
		if (cli.isRequired())
			OptionBuilder.isRequired();

		if (!cli.desc().isEmpty())
			OptionBuilder.withDescription(cli.desc());
		if (!cli.argName().isEmpty())
			OptionBuilder.withArgName(cli.argName());
		if (cli.longOpt().isEmpty())
			OptionBuilder.withLongOpt(field.getName());
		else
			OptionBuilder.withLongOpt(cli.longOpt());
		return OptionBuilder.create(cli.opt());
	}
}
