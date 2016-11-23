package level2.constants;

import level2.interpreter.Bfck;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Trace {

    static Calendar cal;
    private static int step = 1;
    private static BufferedWriter writer;

    /**
     * print all the data into a log file and save the file
     */
    public static void init(String fileName) {
        try{
            // checks if the file has an extension, if it has one, remove it
            if ((fileName.lastIndexOf('.')) != -1) {
                fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            }
            //put the new extension at the end of the filename
            File file = new File(fileName + ".log");
            file.createNewFile();

            writer = new BufferedWriter(new FileWriter(file));
            writer.write("Brainf*ck interpreter launched at "+getCurrentTime());
            writer.newLine();
            writer.newLine();
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveState(Bfck bfck) {
        try {
            writer.write("step : " + step);
            writer.newLine();
            writer.write("execPtr : " + bfck.getInstruction());
            writer.newLine();
            writer.write("dataPtr : " + bfck.getPointer());
            writer.newLine();
            writer.write("memoryState : " + bfck.toDebugString());
            writer.newLine();
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        step++;
    }

    private static String getCurrentTime(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    public static void end(){
        try{
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}