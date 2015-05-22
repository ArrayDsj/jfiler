package net.wisedream.jfiler;

import java.io.File;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;

import net.wisedream.common.cli.CLIOption;
import net.wisedream.common.cli.CLIOptions;
import net.wisedream.common.cli.CLIOptionsProcessor;
import org.apache.commons.cli.Options;

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
    @CLIOption(argName = "HELP", desc = "Show this help info", longOpt = "help", opt = "h")
    private Object help;

    private ServerConfig(int port, File dir, String key) {
        this.port = port;
        this.dir = dir;
        this.key = key;
    }

    public static ServerConfig parseArgs(String[] args) {
        Options options = CLIOptionsProcessor.process(ServerConfig.class);
        try {
            CommandLine cli = new BasicParser().parse(options, args);
            if (cli.hasOption('h'))//show help
                throw new Exception();
            String strPort = cli.getOptionValue('p');
            ServerConfig server = new ServerConfig(strPort == null ? Const.DEFAULT_SERVER_PORT : Integer.parseInt(strPort),
                    new File(cli.getOptionValue('d')),
                    cli.getOptionValue('k'));
            if (server.checkConfig())
                return server;
        } catch (Exception e) {
            new HelpFormatter().printHelp("java -jar jfiler.jar S[erver] [OPTIONS]", options, false);
            System.exit(0);
        }
        return null;
    }

    public int getPort() {
        return port;
    }

    public File getDir() {
        return dir;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "key='" + key + '\'' +
                ", dir=" + dir +
                ", port=" + port +
                '}';
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
        if (key == null || key.trim().length() < 1) {
            System.err.println("key must not be null");
            result = false;
        }
        return result;
    }

}
