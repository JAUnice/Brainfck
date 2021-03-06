package unitary;

import level4.Optimization.MultiIncrDecr;
import level4.Optimization.MultiRightLeft;
import level4.Optimization.SetCell;
import level4.instructions.InstructionEnum;
import level4.constants.Languages;
import level4.instructions.Visualisable;
import level4.interpreter.Optimizer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static level4.instructions.InstructionEnum.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the optimizer
 */
public class testOptimizer {

    private List<Visualisable> instructions;
    private List<Visualisable> optimized;
    private Optimizer optimizer = new Optimizer();

    @org.junit.Test
    public void testUniqueINCR() {
        instructions = Collections.singletonList(INCR);
        optimized = optimizer.optimize(instructions);
        assertTrue(optimized.size() == 1);
        assertTrue(optimized.get(0) instanceof MultiIncrDecr);
        assertEquals("++mem[i];", optimized.get(0).getCode(Languages.java).get());
    }

    @org.junit.Test
    public void testUniqueDECR() {
        instructions = Collections.singletonList(DECR);
        optimized = optimizer.optimize(instructions);
        assertTrue(optimized.size() == 1);
        assertTrue(optimized.get(0) instanceof MultiIncrDecr);
        assertEquals("--mem[i];", optimized.get(0).getCode(Languages.java).get());
    }

    @org.junit.Test
    public void testUniqueLEFT() {
        instructions = Collections.singletonList(LEFT);
        optimized = optimizer.optimize(instructions);
        assertTrue(optimized.size() == 1);
        assertTrue(optimized.get(0) instanceof MultiRightLeft);
        assertEquals("--i;", optimized.get(0).getCode(Languages.java).get());
    }

    @org.junit.Test
    public void testUniqueRIGHT() {
        instructions = Collections.singletonList(RIGHT);
        optimized = optimizer.optimize(instructions);
        assertTrue(optimized.size() == 1);
        assertTrue(optimized.get(0) instanceof MultiRightLeft);
        assertEquals("++i;", optimized.get(0).getCode(Languages.java).get());
    }

    @org.junit.Test
    public void testUniqueIN() {
        instructions = Collections.singletonList(IN);
        optimized = optimizer.optimize(instructions);
        System.out.println(optimized);
        assertTrue(optimized.size() == 1);
        assertTrue(optimized.get(0) instanceof InstructionEnum);
    }

    @org.junit.Test
    public void testUnoptimizable() {
        instructions = Arrays.asList(IN, OUT, JUMP, BACK);
        optimized = optimizer.optimize(instructions);
        assertTrue(optimized.size() == 4);
        for (Visualisable v : optimized) {
            assertTrue(v instanceof InstructionEnum);
        }
    }

    @org.junit.Test
    public void testMultiINCR() {
        instructions = Arrays.asList(INCR, INCR, INCR, INCR);
        optimized = optimizer.optimize(instructions);
        assertTrue(optimized.size() == 1);
        assertTrue(optimized.get(0) instanceof MultiIncrDecr);
        assertEquals("mem[i] += 4;", optimized.get(0).getCode(Languages.java).get());
    }

    @org.junit.Test
    public void testMultiDECR() {
        instructions = Arrays.asList(DECR, DECR, DECR, DECR);
        optimized = optimizer.optimize(instructions);
        assertTrue(optimized.size() == 1);
        assertTrue(optimized.get(0) instanceof MultiIncrDecr);
        assertEquals("mem[i] -= 4;", optimized.get(0).getCode(Languages.java).get());
    }

    @org.junit.Test
    public void testMuldiLEFT() {
        instructions = Arrays.asList(LEFT, LEFT, LEFT, LEFT);
        optimized = optimizer.optimize(instructions);
        assertTrue(optimized.size() == 1);
        assertTrue(optimized.get(0) instanceof MultiRightLeft);
        assertEquals("i -= 4;", optimized.get(0).getCode(Languages.java).get());
    }

    @org.junit.Test
    public void testSetZero() {
        instructions = Arrays.asList(JUMP, DECR, BACK);
        optimized = optimizer.optimize(instructions);
        assertTrue(optimized.size() == 1);
        assertTrue(optimized.get(0) instanceof SetCell);
        assertEquals("mem[i] = 0;", optimized.get(0).getCode(Languages.java).get());
    }

    @org.junit.Test
    public void testSetValue() {
        instructions = Arrays.asList(JUMP, DECR, BACK, INCR, INCR, INCR);
        optimized = optimizer.optimize(instructions);
        assertTrue(optimized.size() == 1);
        assertTrue(optimized.get(0) instanceof SetCell);
        assertEquals("mem[i] = 3;", optimized.get(0).getCode(Languages.java).get());
    }

    @org.junit.Test
    public void testMultipleInstr() {
        instructions = Arrays.asList(RIGHT, RIGHT, LEFT, RIGHT, INCR, INCR, INCR, DECR, JUMP, DECR, BACK, INCR, INCR, INCR);
        optimized = optimizer.optimize(instructions);
        assertTrue(optimized.size() == 3);
        assertTrue(optimized.get(0) instanceof MultiRightLeft);
        assertEquals("i += 2;", optimized.get(0).getCode(Languages.java).get());
        assertTrue(optimized.get(1) instanceof MultiIncrDecr);
        assertEquals("mem[i] += 2;", optimized.get(1).getCode(Languages.java).get());
        assertTrue(optimized.get(2) instanceof SetCell);
        assertEquals("mem[i] = 3;", optimized.get(2).getCode(Languages.java).get());
    }

    @org.junit.Test
    public void testMultipleInstr2() {
        instructions = Arrays.asList(RIGHT, RIGHT, LEFT, RIGHT, IN, INCR, INCR, INCR, DECR, JUMP, DECR, BACK, INCR, INCR, INCR);
        optimized = optimizer.optimize(instructions);
        assertTrue(optimized.size() == 4);
        assertTrue(optimized.get(0) instanceof MultiRightLeft);
        assertEquals("i += 2;", optimized.get(0).getCode(Languages.java).get());
        assertEquals(IN, optimized.get(1));
        assertTrue(optimized.get(2) instanceof MultiIncrDecr);
        assertEquals("mem[i] += 2;", optimized.get(2).getCode(Languages.java).get());
        assertTrue(optimized.get(3) instanceof SetCell);
        assertEquals("mem[i] = 3;", optimized.get(3).getCode(Languages.java).get());
    }
}
