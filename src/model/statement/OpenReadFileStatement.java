package model.statement;

import exceptions.MochaDictionaryException;
import exceptions.MochaException;
import exceptions.MochaExpEvalException;
import model.expression.Expression;
import model.programstate.ProgramState;
import exceptions.MochaFileException;
import model.type.StringType;
import model.value.Value;
import model.value.StringValue;
import model.container.IDictionary;

import java.io.BufferedReader;

public class OpenReadFileStatement implements Statement {
    private final Expression expression;

    public OpenReadFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MochaException {
        Value value = expression.eval(state.getSymTable());
        if (value.getType().equals(new StringType())) {
            StringValue stringValue = (StringValue) value;
            String fileName = stringValue.getValue();
            IDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (fileTable.hasKey(fileName)) {
                throw new MochaFileException("File " + fileName + " is already opened.");
            } else {
                try {
                    java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(fileName));
                    fileTable.insert(fileName, bufferedReader);
                } catch (java.io.FileNotFoundException e) {
                    throw new MochaFileException("File " + fileName + " not found.");
                }
            }
        } else {
            throw new MochaExpEvalException("File path expression does not evaluate to a string.");
        }
        return state;
    }

    @Override
    public String toString() {
        return "openReadFile(" + expression.toString() + ")";
    }
}
