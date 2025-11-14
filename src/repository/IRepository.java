package repository;

import exceptions.MochaException;
import model.programstate.ProgramState;

public interface IRepository {
    ProgramState getProgramState();

    void logPrgStateExec() throws MochaException;
}
