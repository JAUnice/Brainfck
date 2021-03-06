package level4.command;

import level4.interpreter.BfckContainer;


public class TraceCommand implements Command {

    /**
     * When invoked with the --trace option, the
     * interpreter will create a file named p.log (considering the execution of p.bf) that contains the
     * following information: the execution step number (starting at one), the location of the execution
     * pointer after the execution of this step, the location of the data pointer at the very same time, and
     * a snapshot of the memory
     *
     * @param bfck
     */
    public void execute(BfckContainer bfck) {
        bfck.toTrace();
    }
}
