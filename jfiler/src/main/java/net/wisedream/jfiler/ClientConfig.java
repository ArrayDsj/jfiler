package net.wisedream.jfiler;

import net.wisedream.common.cli.CLIOption;
import net.wisedream.common.cli.CLIOptionsProcessor;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class ClientConfig {
    @CLIOption(argName = "HOST [PORT]", isRequired = true, hasArgs = true, desc = "Remote server host and port. Default port is 8888", longOpt = "host", opt = "H")
    private String host;

    private int port;
    @CLIOption(argName = "KEY", isRequired = true, hasArg = true, desc = "The key used to connect to server", longOpt = "key", opt = "k")
    private String key;

    @CLIOption(argName = "HELP", desc = "Show this help info", longOpt = "help", opt = "h")
    private Object help;

    private ClientConfig(String host, int port, String key) {
        this.host = host;
        this.port = port;
        this.key = key;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "ClientConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", key='" + key + '\'' +
                '}';
    }

    public static ClientConfig parseArgs(String[] args) {
        Options options = CLIOptionsProcessor.process(ClientConfig.class);
        try {
            CommandLine cli = new BasicParser().parse(options, args);
            if (cli.hasOption('h'))//show help
                throw new Exception();
            String[] hostOption = cli.getOptionValues('H');
            return new ClientConfig(hostOption[0],
                    hostOption.length > 1 ? Integer.parseInt(hostOption[1]) : Const.DEFAULT_SERVER_PORT,
                    cli.getOptionValue('k'));
        } catch (Exception e) {
            new HelpFormatter().printHelp("java -jar jfiler.jar C[lient] [OPTIONS]", options, false);
            System.exit(0);
        }
        return null;
    }
}
