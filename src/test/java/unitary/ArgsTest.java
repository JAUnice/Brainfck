package unitary;

import level4.argument.ArgsCheck;
import level4.command.*;
import level4.exceptions.FileException;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertTrue;

/**
 * test sur l'interpreteur d'agument
 */
public class ArgsTest {

    @org.junit.Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none(); //Cutom library 'system rule'
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void test0() {
        String args[] = {"-p", "fichier", "--rewrite"};
        ArgsCheck check = new ArgsCheck(args);
        assertTrue(check.getFileName().equals("fichier"));
        assertTrue(check.nextStoppingAction() instanceof RewriteCommand);
    }

    @Test
    public void test1() {
        String args[] = {"-p", "fichier", "--rewrite", "--translate", "--check"};
        ArgsCheck check = new ArgsCheck(args);
        assertTrue(check.getFileName().equals("fichier"));
        while (check.hasStoppingActions()) {
            Command stoppingAction = check.nextStoppingAction();
            assertTrue(stoppingAction instanceof RewriteCommand
                    || stoppingAction instanceof TranslateCommand
                    || stoppingAction instanceof CheckCommand);
        }
    }

    @Test
    public void test2() {
        String args[] = {"-p"};
        exception.expect(FileException.class);
        exit.expectSystemExitWithStatus(3);
        ArgsCheck check = new ArgsCheck(args);

    }

    @Test
    public void test3() {
        String args[] = {"-p", "fichier", "zez"};
        exit.expectSystemExitWithStatus(3);
        ArgsCheck check = new ArgsCheck(args);
    }

    @Test
    public void test4() {
        String args[] = {"-p", "fichier", "-i"};
        exit.expectSystemExitWithStatus(3);
        ArgsCheck check = new ArgsCheck(args);
    }

    @Test
    public void test5() {
        String args[] = {"-p", "fichier", "--rewrite", "--translate", "--check", "--trace", "--showMetrics"};
        ArgsCheck check = new ArgsCheck(args);
        assertTrue(check.getFileName().equals("fichier"));
        while (check.hasStoppingActions()) {
            Command stoppingAction = check.nextStoppingAction();
            assertTrue(stoppingAction instanceof RewriteCommand
                    || stoppingAction instanceof TranslateCommand
                    || stoppingAction instanceof CheckCommand);
        }
        while (check.hasPassiveActions()) {
            Command passiveAction = check.nextPassiveAction();
            assertTrue(passiveAction instanceof TraceCommand
                    || passiveAction instanceof MetricsCommand);
        }
    }

    @Test
    public void test6() {
        String args[] = {"-p", "fichier", "--rewrite", "--translate", "--check", "--trace", "--showMetrics", "-i", "in", "-o", "out"};
        ArgsCheck check = new ArgsCheck(args);
        assertTrue(check.getFileName().equals("fichier"));
        assertTrue(check.getIn().equals("in"));
        assertTrue(check.getOut().equals("out"));
        while (check.hasStoppingActions()) {
            Command stoppingAction = check.nextStoppingAction();
            assertTrue(stoppingAction instanceof RewriteCommand
                    || stoppingAction instanceof TranslateCommand
                    || stoppingAction instanceof CheckCommand);
        }
        while (check.hasPassiveActions()) {
            Command passiveAction = check.nextPassiveAction();
            assertTrue(passiveAction instanceof TraceCommand
                    || passiveAction instanceof MetricsCommand);
        }


    }

}


