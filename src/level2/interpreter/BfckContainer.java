package level2.interpreter;

import level2.constants.Executable;
import level2.constants.Languages;
import level2.writer.BfWriter;
import level2.writer.ImageWriter;
import level2.writer.InstructionWriter;

import java.util.List;

public class BfckContainer {
    private Memory bfck;
    private Interpreter interpreter;
    private BfWriter instructWriter;
    private BfWriter imgWriter;
    private BfWriter codeWriter;
    private String filenameOut;
    private String filenameIn;
    private String filename;

    public BfckContainer(List<Executable> instructions,String filename,String filenameIn,String filenameOut){
        bfck = new Bfck();
        interpreter = new Interpreter(instructions);
        this.filenameOut = filenameOut;
        this.filenameIn = filenameIn;
        this.filename = filename;
        instructWriter = new InstructionWriter();
        imgWriter = new ImageWriter();
    }

    public void check(){
        interpreter.check();
    }

    public void handle(){
        interpreter.handle(bfck);
    }

    public String toString(){
        return bfck.toString();
    }

    public void rewrite(){
        instructWriter.WriteFile(interpreter.getVisualisableInstructions(),"");
    }

    public void setTrace(){
        interpreter.setTrace();
    }

    public void translate(){
        imgWriter.WriteFile(interpreter.getVisualisableInstructions(),filename);
    }

    public void toMetrics(){
        bfck = new MetricsDecorator(bfck,interpreter.getInstructionSize());
    }
    public void toTrace(){
        bfck = new TraceDecorator(bfck,filename);
    }

    public void writeCode(Languages language, boolean optimize){
        codeWriter = language.getCodeClass();
        if(optimize){
            codeWriter.WriteFile(interpreter.getOptimizedInstructions(),filename);
        } else {
            codeWriter.WriteFile(interpreter.getVisualisableInstructions(),filename);
        }
    }

    public String getFilenameIn(){
        return filenameIn;
    }

    public void setIn(String in){
        interpreter.setIn(in);
    }

    public String getFilenameOut(){
        return filenameOut;
    }

    public String getFilename(){
        return filename;
    }

    public Memory getBfck(){
        return bfck;
    }


}
