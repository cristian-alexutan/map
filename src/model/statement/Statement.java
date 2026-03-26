package model.statement;

import exceptions.MochaException;
import model.container.IDictionary;
import model.programstate.ProgramState;
import model.type.Type;

public interface Statement {
    ProgramState execute(ProgramState state) throws MochaException;

    IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MochaException;
}
