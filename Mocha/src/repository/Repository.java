package repository;

import exceptions.MochaException;
import exceptions.MochaFileException;
import model.programstate.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    List<ProgramState> programStates;
    String logFilePath;

    public Repository(ProgramState programState, String logFilePath) {
        this.programStates = new ArrayList<>();
        this.programStates.add(programState);
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getProgramStateList() {
        return this.programStates;
    }

    @Override
    public void setProgramStateList(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void logPrgStateExec(ProgramState programState) throws MochaException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(programState.toString());
            logFile.close();
        } catch (IOException e) {
            throw new MochaFileException("Could not log program state to file: " + e.getMessage());
        }
    }
}
