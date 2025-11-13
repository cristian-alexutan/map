package model.command;

import controller.Controller;
import exceptions.MochaException;

import java.io.IOException;

public class RunExampleCommand extends Command {
    private Controller ctr;

    public RunExampleCommand(String key, String description, Controller ctr) {
        super(key, description);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try {
            ctr.allSteps();
        } catch (MochaException e) {
            System.err.println(e.getMessage());
        }
    }
}
