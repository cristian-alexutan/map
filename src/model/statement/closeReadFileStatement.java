package model.statement;

import exceptions.MochaDictionaryException;
import exceptions.MochaException;
import model.value.StringValue;
import model.type.StringType;
import model.programstate.ProgramState;
import model.container.IDictionary;
import java.io.BufferedReader;

public class closeReadFileStatement implements Statement {
    private final String exp;

    public closeReadFileStatement(String exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        StringValue filePath = new StringValue(exp);
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if (fileTable.hasKey(filePath.getValue())) {
            try {
                java.io.BufferedReader reader = fileTable.get(filePath.getValue());
                reader.close();
                fileTable.remove(filePath.getValue());
            } catch (java.io.IOException e) {
                System.err.println("Error closing file: " + e.getMessage());
            } catch (MochaException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("File " + filePath.getValue() + " is not opened.");
        }
        return state;
    }
}
