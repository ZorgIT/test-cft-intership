package org.ex;

import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "typefilter", mixinStandardHelpOptions = true,
        version = "typefilter 1.0",
        description = "Get data from text file and extrude to separate file "
                + "by datatype.")
public final class App implements Runnable {
    /**
     * Picocli  annotated parameters.
     */
    @CommandLine.Parameters(description = "File Paths")
    private List<String> filePaths;
    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true,
            description = "Show this help message and exit.")
    private boolean helpRequested;

    @CommandLine.Option(names = {"-V", "--version"}, versionHelp = true,
            description = "Print version information and exit.")
    private boolean versionRequested;

    @CommandLine.Option(names = "-o",
            description = "Path to write output files", defaultValue = "")
    private static String outputPath;

    @CommandLine.Option(names = "-a",
            description = "Appends data to file is existed", defaultValue = "false")
    private static boolean appendStatus;

    @CommandLine.Option(names = "-s",
            description = "give short result report")
    private static boolean shortReport;

    @CommandLine.Option(names = "-f",
            description = "give short result report", defaultValue = "false")
    private static boolean fullReport;

    @CommandLine.Option(names = "-p",
            description = "Add prefix to filename", defaultValue = "")
    private static String fileNamePrefix;

    public static String getOutputPath() {
        return outputPath.isEmpty() ? System.getProperty("user.dir") : outputPath;
    }

    public static boolean isAppendStatus() {
        return appendStatus;
    }

    public static boolean isShortReport() {
        return shortReport;
    }

    public static boolean isFullReport() {
        return fullReport;
    }

    public static String getFileNamePrefix() {
        return fileNamePrefix;
    }

    //test args for run in idea 'run --args="src/test/resources/in1.txt src/test/resources/in2.txt'"
    @Override
    public void run() {
        Stats statistic = new Stats();
        Filter.generate(filePaths, statistic);
        statistic.showStats();
    }

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new App());
        commandLine.addSubcommand("help", new CommandLine.HelpCommand());
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}
