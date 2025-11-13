package model.statement;

import model.value.IntValue;
import model.type.IntType;
import model.value.StringValue;
import model.type.StringType;
import model.programstate.ProgramState;
import exceptions.MochaException;
import model.container.IDictionary;
import model.value.Value;

public class readFileStatement implements Statement{
    private final String exp;
    private final String varName;

    public readFileStatement(String exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MochaException {
        StringValue filePath = new StringValue(exp);
        java.io.BufferedReader reader = state.getFileTable().get(filePath.getValue());
        try {
            String line = reader.readLine();
            IntValue intValue;
            IDictionary<String, Value> symTable = state.getSymTable();
            if (line != null) {
                intValue = new IntValue(Integer.parseInt(line));
            } else {
                intValue = new IntValue(0); // Default value if EOF
            }
            if (symTable.hasKey(varName)) {
                symTable.update(varName, intValue);
            } else {
                throw new MochaException("Variable " + varName + " not declared.");
            }
        } catch (java.io.IOException e) {
            throw new MochaException("Error reading from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new MochaException("Error parsing integer from file: " + e.getMessage());
        }
        return state;
    }
}
