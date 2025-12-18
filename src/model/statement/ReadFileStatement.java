package model.statement;

import exceptions.MochaException;
import exceptions.MochaFileException;
import model.container.IDictionary;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.type.StringType;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import model.type.Type;
import model.type.IntType;

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
        Value value = exp.eval(state.getSymTable(), state.getHeap());
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
        return null;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MochaException {
        Type expType = exp.typeCheck(typeEnv);
        if (!expType.equals(new StringType())) {
            throw new MochaException("ReadFile Statement: expression must be of type String.");
        }
        Type varType = typeEnv.get(varName);
        if (!varType.equals(new IntType())) {
            throw new MochaException("ReadFile Statement: variable must be of type Int.");
        }
        return typeEnv;
    }
}
