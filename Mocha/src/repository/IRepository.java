package repository;

import exceptions.MochaException;
import model.programstate.ProgramState;
import java.io.IOException;

public interface IRepository {
    ProgramState getProgramState();
    void logPrgStateExec() throws MochaException;
}
