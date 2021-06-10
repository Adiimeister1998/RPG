package logger;

import java.io.*;

public class Logger {
    private static Logger SINGLETON = null;
    private final PrintWriter printWriter;

    public static void createSingleton(String filename)  {
        if(SINGLETON == null) {
            SINGLETON = new Logger(filename);
        }
    }

    public static Logger getSingleton()  {
        if(SINGLETON == null) {
            createSingleton("logger.txt");
        }
        return SINGLETON;
    }

    private Logger(String filename)  {
        File file = new File(filename);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert fileWriter != null;
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        this.printWriter = new PrintWriter(bufferedWriter);
    }

    public void log(String str, LoggerType type)  {
        printWriter.println("[" + type.toString() + "]: " + str);
    }

    public void closeLogger() {
        printWriter.close();
    }
}
