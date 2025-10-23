package model.statement;

import exceptions.MochaException;
import model.programstate.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState state) throws MochaException;
    String toString();
}
