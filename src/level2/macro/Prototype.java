package level2.macro;


import level2.constants.InstructionEnum;

import java.util.ArrayList;
import java.util.List;

public class Prototype {
    private String name;
    private int argument;

    public Prototype(String name) {
        this.name = name;
        this.argument = 0;
    }

    public Prototype(String name, Integer argument) {
        this.name = name;
        this.argument = argument;
    }

    public String getName() {
        return name;
    }

    public List<InstructionEnum> build(List<InstructionEnum> instructions) {
        if (argument <= 0) {
            return instructions;
        } else {
            List<InstructionEnum> finalInstructions = new ArrayList<>();
            for (int i = 0; i < this.argument; i++) {
                finalInstructions.addAll(instructions);
            }
            return finalInstructions;
        }
    }
}
