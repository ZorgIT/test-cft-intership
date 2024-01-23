package org.ex;

import picocli.CommandLine;

@CommandLine.Command(name = "typefilter", mixinStandardHelpOptions = true,
        version = "typefilter 1.0",
        description = "get data from text file and extrude to separate file " +
                "by datatype.")
public final class App implements Runnable {
    /**
     * Picocli parameters
     */
    @CommandLine.Parameters(index = "0",
            description = "Path to the first file")
    private static String filePath1;

    @CommandLine.Parameters(index = "1",
            description = "Path to the second file")
    private static String filePath2;
    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true,
            description = "Show this help message and exit.")
    private boolean helpRequested;

    @CommandLine.Option(names = {"-V", "--version"}, versionHelp = true,
            description = "Print version information and exit.")
    private boolean versionRequested;

    //test args for run in idea 'run --args="src/test/resources/in1.txt src/test/resources/in2.txt'"
    @Override
    public void run() {
        Filter.generate(filePath1, filePath2);
    }

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new App());
        commandLine.addSubcommand("help", new CommandLine.HelpCommand());
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}
