package org.ex;

import picocli.CommandLine;

@CommandLine.Command(name = "typefilter", mixinStandardHelpOptions = true,
        version = "typefilter 1.0",
        description = "get data from text file and extrude to separate file " +
                "by datatype.")
public final class App implements Runnable{
    /**
     * Some text here.
     */
    @CommandLine.Parameters(index = "0",
            description = "Path to the first file")
    private static String filePath1;

    /**
     * Some text here.
     */
    @CommandLine.Parameters(index = "1",
            description = "Path to the second file")
    private static String filePath2;

    /**
     * Some text here.
     */
    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true,
            description = "Show this help message and exit.")
    private boolean helpRequested;

    /**
     * Some text here.
     */
    @CommandLine.Option(names = {"-V", "--version"}, versionHelp = true,
            description = "Print version information and exit.")
    private boolean versionRequested;

    @Override
    public void run() {

    }
    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new App());
        commandLine.addSubcommand("help", new CommandLine.HelpCommand());
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}
