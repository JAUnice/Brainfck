package level4.command;

import level4.interpreter.BfckContainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class SetOutCommand implements Command {

    /**
     * set new output file specified
     * if not specified create it
     *
     * @param bfck
     */
    public void execute(BfckContainer bfck) {
        //setting output file
        try {
            if (bfck.getFilenameOut() != null) {
                File file = new File(bfck.getFilenameOut());
                System.setOut(new PrintStream(file));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Never actually goes there
        }
    }
}
