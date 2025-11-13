package model.statement;

import exceptions.MochaFileException;
import model.expression.Expression;
import model.value.IntValue;
import model.value.StringValue;
import model.programstate.ProgramState;
import exceptions.MochaException;
import model.container.IDictionary;
import model.value.Value;
import model.type.StringType;

import java.io.BufferedReader;

public class ReadFileStatement implements Statement {
    private final Expression exp;
    private final String varName;

    public ReadFileStatement(Expression exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MochaException {
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();
        Value value = exp.eval(state.getSymTable());
        if (!value.getType().equals(new StringType())) {
            throw new MochaFileException("File path expression does not evaluate to a string.");
        }
        StringValue filePath = (StringValue) value;
        if (!fileTable.hasKey(filePath.getValue())) {
            throw new MochaFileException("File " + filePath.getValue() + " is not opened.");
        }
        BufferedReader reader = fileTable.get(filePath.getValue());
        try {
            String line = reader.readLine();
            IntValue intValue;
            IDictionary<String, Value> symTable = state.getSymTable();
            if (line != null) {
                intValue = new IntValue(Integer.parseInt(line));
            } else {
                intValue = new IntValue(0);
            }
            if (symTable.hasKey(varName)) {
                symTable.update(varName, intValue);
            } else {
                throw new MochaException("Variable " + varName + " not declared.");
            }
        } catch (java.io.IOException e) {
            throw new MochaFileException("Error reading from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new MochaFileException("Error parsing integer from file: " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}
