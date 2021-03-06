package level4.command;

import level4.interpreter.BfckContainer;

public class MetricsCommand implements Command {
    /**
     * print metrics
     *
     * @param bfck
     */
    @Override
    public void execute(BfckContainer bfck) {
        bfck.toMetrics();
    }
}
