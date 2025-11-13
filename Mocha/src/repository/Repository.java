package repository;

import model.programstate.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements IRepository {
    ProgramState programState;
    String logFilePath;

    public Repository(ProgramState programState, String logFilePath) {
        this.programState = programState;
        this.logFilePath = logFilePath;
    }

    @Override
    public ProgramState getProgramState() {
        return this.programState;
    }

    @Override
    public void logPrgStateExec() throws IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(programState.toString());
        logFile.close();
    }
}
