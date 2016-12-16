package level2.procedures;

import level2.constants.Executable;
import level2.interpreter.Interpreter;
import level2.interpreter.Memory;

import java.util.List;

import static level2.constants.Sizes.MAXMEMORYSIZE;
import static level2.constants.Sizes.PROC_SIZE;

/**
 * Handle procedures with or without parameters
 */

public class Procedure implements Executable {
    private byte[] parameters;
    private Interpreter procedureInterpreter;

    /**
     * Method used when a procedure is declared in a Bf program
     * @param instructions the list of instructions done by the procedure
     * @param parameters the list of parameters (values)
     */
    public Procedure(List<Executable> instructions, byte... parameters){
        procedureInterpreter = new Interpreter(instructions);
        this.parameters = parameters;
    }

    /**
     * Method used when a procedure is called in a Bf program
     * @param bfck the memory
     * @param interpreter the interpreter
     */
    @Override
    public void exec(Memory bfck, Interpreter interpreter){
        short pointer = bfck.getPointer();
        int size = MAXMEMORYSIZE.get()-PROC_SIZE.get();
        bfck.setPointer((short) size);


        for(int i = 0;i<parameters.length;i++){
            bfck.setCase(parameters[i]);
            bfck.right();
        }

        procedureInterpreter.handle(bfck);
        bfck.setPointer(pointer);
    }

}