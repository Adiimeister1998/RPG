package logger;

import java.io.*;

public class Logger {
    private static Logger SINGLETON = null;
    private final PrintWriter printWriter;

    public static void createSingleton(String filename) throws IOException {
        if(SINGLETON == null) {
            SINGLETON = new Logger(filename);
        }
    }

    public static Logger getSingleton() throws IOException {
        if(SINGLETON == null) {
            createSingleton("logger.txt");
        }
        return SINGLETON;
    }

    private Logger(String filename) throws IOException {
        File file = new File(filename);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        this.printWriter = new PrintWriter(bufferedWriter);
    }

    public void log(String str, LoggerType type) throws IOException {
        printWriter.println("[" + type.toString() + "]: " + str);
    }

    public void closeLogger() {
        printWriter.close();
    }
}
