package net.wisedream.jfiler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        if (args.length < 1)
            showHelp();
        switch (args[0].toLowerCase()) {
            case "client":
            case "c":
                ClientConfig clientConfig = ClientConfig.parseArgs(Arrays.copyOfRange(args, 1, args.length));
                System.out.println("Start Client Instance: " + clientConfig.toString());
                Socket socket = new Socket(clientConfig.getHost(), clientConfig.getPort());
                Scanner scn = new Scanner(System.in);
                while(scn.hasNext()){
                    String action = scn.nextLine();
                    String directive = action.substring(0, action.indexOf(" "));
                }
                break;
            case "server":
            case "s":
                ServerConfig serverConfig = ServerConfig.parseArgs(Arrays.copyOfRange(args, 1, args.length));
                System.out.println("Starting Server Instance: " + serverConfig.toString());
                ServerSocket serverSocket = new ServerSocket(serverConfig.getPort());
                while (true)
                    serverSocket.accept();
        }
    }

    private static void showHelp() {
        System.out
                .println("use 'java -jar jfile.jar S[erver]|C[lient] -h' for help.");
        System.exit(0);
    }
}
