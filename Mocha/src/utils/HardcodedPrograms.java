package utils;

import model.expression.ArithmeticExpression;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IntValue;

import java.util.Vector;

import static model.expression.ArithmeticOperator.ADD;
import static model.expression.ArithmeticOperator.MULTIPLY;

public final class HardcodedPrograms {
    private static final Vector<Statement> STATEMENTS = new Vector<>();

    static {
        Statement ex1 =
                new CompoundStatement(
                        new DeclarationStatement("v", new IntType()),
                        new CompoundStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                new PrintStatement(new VariableExpression("v"))
                        )
                );

        Statement ex2 =
                new CompoundStatement(
                        new DeclarationStatement("a", new IntType()),
                        new CompoundStatement(
                                new DeclarationStatement("b", new IntType()),
                                new CompoundStatement(
                                        new AssignStatement(
                                                "a",
                                                new ArithmeticExpression(
                                                        new ValueExpression(new IntValue(2)),
                                                        new ArithmeticExpression(
                                                                new ValueExpression(new IntValue(3)),
                                                                new ValueExpression(new IntValue(5)),
                                                                MULTIPLY
                                                        ),
                                                        ADD
                                                )
                                        ),
                                        new CompoundStatement(
                                                new AssignStatement(
                                                        "b",
                                                        new ArithmeticExpression(
                                                                new VariableExpression("a"),
                                                                new ValueExpression(new IntValue(1)),
                                                                ADD
                                                        )
                                                ),
                                                new PrintStatement(new VariableExpression("b"))
                                        )
                                )
                        )
                );

        Statement ex3 =
                new CompoundStatement(
                        new DeclarationStatement("a", new BoolType()),
                        new CompoundStatement(
                                new DeclarationStatement("v", new IntType()),
                                new CompoundStatement(
                                        new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                        new CompoundStatement(
                                                new IfStatement(
                                                        new VariableExpression("a"),
                                                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                        new AssignStatement("v", new ValueExpression(new IntValue(3)))
                                                ),
                                                new PrintStatement(new VariableExpression("v"))
                                        )
                                )
                        )
                );

        Statement ex4 =
                new CompoundStatement(
                        new DeclarationStatement("varf", new model.type.StringType()),
                        new CompoundStatement(
                                new AssignStatement("varf",
                                        new ValueExpression(new model.value.StringValue("test.in"))
                                ),
                                new CompoundStatement(
                                        new OpenReadFileStatement(new VariableExpression("varf")),
                                        new CompoundStatement(
                                                new DeclarationStatement("varc", new IntType()),
                                                new CompoundStatement(
                                                        new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                        new CompoundStatement(
                                                                new PrintStatement(new VariableExpression("varc")),
                                                                new CompoundStatement(
                                                                        new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("varc")),
                                                                                new CloseReadFileStatement(new VariableExpression("varf"))
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );

        STATEMENTS.add(ex1);
        STATEMENTS.add(ex2);
        STATEMENTS.add(ex3);
        STATEMENTS.add(ex4);
    }

    private HardcodedPrograms() {
    }

    public static Statement getStatement(int index) {
        return STATEMENTS.get(index);
    }
}
