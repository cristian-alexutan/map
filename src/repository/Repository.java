package repository;

import exceptions.MochaException;
import exceptions.MochaFileException;
import model.programstate.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements IRepository {
    ProgramState programState, originalProgram;
    String logFilePath;

    public Repository(ProgramState programState, String logFilePath) {
        this.programState = programState;
        this.logFilePath = logFilePath;
        this.originalProgram = programState.deepCopy();
    }

    @Override
    public ProgramState getProgramState() {
        return this.programState;
    }

    @Override
    public void logPrgStateExec() throws MochaException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(programState.toString());
            logFile.close();
        } catch (IOException e) {
            throw new MochaFileException("Could not log program state to file: " + e.getMessage());
        }
    }
}
