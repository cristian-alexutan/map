package model.statement;

import exceptions.MochaException;
import exceptions.MochaExpEvalException;
import exceptions.MochaFileException;
import model.container.IDictionary;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public class CloseReadFileStatement implements Statement {
    private final Expression exp;

    public CloseReadFileStatement(Expression exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MochaException {
        Value value = exp.eval(state.getSymTable());
        if (!value.getType().equals(new StringType())) {
            throw new MochaExpEvalException("File path expression does not evaluate to a string.");
        }
        StringValue filePath = (StringValue) value;
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if (fileTable.hasKey(filePath.getValue())) {
            try {
                java.io.BufferedReader reader = fileTable.get(filePath.getValue());
                reader.close();
                fileTable.remove(filePath.getValue());
            } catch (java.io.IOException e) {
                throw new MochaFileException("Error closing file: " + e.getMessage());
            } catch (MochaException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new MochaFileException("File " + filePath.getValue() + " is not opened.");
        }
        return state;
    }

    @Override
    public String toString() {
        return "closeReadFile(" + exp + ")";
    }
}
