package level4.utils;

import level4.instructions.Executable;
import level4.interpreter.Interpreter;
import level4.interpreter.Memory;

import java.util.Arrays;
import java.util.List;

import static level4.constants.Sizes.MAXMEMORYSIZE;
import static level4.constants.Sizes.PROC_SIZE;

/**
 * Handle procedures with or without parameters
 */

public class Procedure implements Parametrized {
    private short[] parameters;
    private Interpreter procedureInterpreter;

    /**
     * Method used when a procedure is declared in a Bf program
     *
     * @param instructions the list of instructions done by the procedure
     */
    public Procedure(List<Executable> instructions) {
        procedureInterpreter = new Interpreter(instructions);
        parameters = new short[0];
    }

    public Procedure(Procedure proc, short... parameters) {
        this.procedureInterpreter = proc.procedureInterpreter;
        this.parameters = parameters;
    }

    @Override
    public Executable getFunction(short... parameters) {
        return new Procedure(this, parameters);
    }

    /**
     * Method used when a procedure is called in a Bf program
     *
     * @param bfck        the memory
     * @param interpreter the interpreter
     */
    @Override
    public void exec(Memory bfck, Interpreter interpreter) {
        short pointer = bfck.getPointer();
        int size = MAXMEMORYSIZE.get() - PROC_SIZE.get();
        bfck.setPointer((short) size);


        for (int i = 0; i < parameters.length; i++) {
            bfck.setCase(bfck.getMemoryAt(parameters[i]));
            bfck.right();
        }

        procedureInterpreter.handle(bfck);
        bfck.setPointer(pointer);
        interpreter.incrementInstructions();
    }

    @Override
    public boolean equals(Object proc){
        return proc instanceof Procedure &&
                Arrays.equals(((Procedure) proc).parameters,this.parameters) &&
                this.procedureInterpreter.getInstructions().equals(((Procedure) proc).procedureInterpreter.getInstructions());
    }

}