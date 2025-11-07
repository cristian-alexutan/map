package utils;

import model.expression.*;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IntValue;
import java.util.Vector;

public class HardcodedPrograms {
    Vector<Statement> statements;

    public HardcodedPrograms() {
        statements = new Vector<>();
        Statement ex1 = new CompoundStatement(new DeclarationStatement("v", new IntType()), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        Statement ex2 = new CompoundStatement(new DeclarationStatement("a", new IntType()), new CompoundStatement(new DeclarationStatement("b", new IntType()), new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)), new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), '*'), '+')), new CompoundStatement(new AssignStatement("b", new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)), '+')), new PrintStatement(new VariableExpression("b"))))));
        Statement ex3 = new CompoundStatement(new DeclarationStatement("a", new BoolType()), new CompoundStatement(new DeclarationStatement("v", new IntType()), new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))), new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
        statements.add(ex1);
        statements.add(ex2);
        statements.add(ex3);
    }

    public Statement getStatement(int index) {
        if (index < 0 || index >= statements.size()) {
            throw new IndexOutOfBoundsException("Invalid statement index");
        }
        return statements.get(index);
    }
}
