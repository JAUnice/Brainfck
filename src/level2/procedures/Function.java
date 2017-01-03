package level2.procedures;

import level2.constants.Executable;
import level2.interpreter.Interpreter;
import level2.interpreter.Memory;

import java.util.List;

import static level2.constants.Sizes.MAXMEMORYSIZE;
import static level2.constants.Sizes.PROC_SIZE;

/**
 * Handle functions with or without parameters
 * The returned value is stored in the cell where the function was called
 */
public class Function implements Parametrized {
    private short[] parameters;
    private Interpreter functionInterpreter;

    /**
     * Method used when a procedure is declared in a Bf program
     * @param instructions the list of instructions done by the procedure
     */
    public Function(List<Executable> instructions){
        functionInterpreter = new Interpreter(instructions);
        this.parameters = parameters;
    }

    public Function(Function function, short... parameters){
        functionInterpreter = function.functionInterpreter;
        this.parameters = parameters;
    }

    public Executable getFunction(short... parameters){
        return new Function(this,parameters);
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
            bfck.setCase(bfck.getMemoryAt(parameters[i]));
            bfck.right();
        }

        functionInterpreter.handle(bfck);

        byte returnValue = bfck.getCell();
        bfck.setPointer(pointer); // we go back to the cell where the function was called
        bfck.setCase(returnValue); // we store the return value
        interpreter.incrementInstructions();
    }

}