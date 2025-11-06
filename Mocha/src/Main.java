import model.statement.*;
import model.programstate.*;
import model.value.*;
import model.container.*;
import model.type.*;
import model.expression.*;
import repository.*;
import controller.*;

void main() {
    // int v; v = 2; Print(v);
    Statement ex1 = new CompoundStatement(
        new DeclarationStatement("v", new IntType()),
        new CompoundStatement(
            new AssignStatement("v", new ValueExpression(new IntValue(2))),
            new PrintStatement(new VariableExpression("v"))
        ));
    IStack<Statement> exeStack1 = new MochaStack<>();
    IDictionary<String, Value> symTable1 = new MochaDictionary<>();
    IList<Value> out1 = new MochaList<>();
    exeStack1.push(ex1);
    ProgramState programState1 = new ProgramState(exeStack1, symTable1, out1);
    IRepository repository1 = new Repository(programState1);
    Controller controller1 = new Controller(repository1, true);
    try {
        controller1.allSteps();
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }

    // int a;int b; a=2+3*5;b=a+1;Print(b)
    Statement ex2 = new CompoundStatement( new DeclarationStatement("a",new IntType()),
new CompoundStatement(new DeclarationStatement("b",new IntType()),
new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),new
ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), '*'), '+')),
 new CompoundStatement(new AssignStatement("b",new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new
IntValue(1)), '+')), new PrintStatement(new VariableExpression("b"))))));

    IStack<Statement> exeStack2 = new MochaStack<>();
    IDictionary<String, Value> symTable2 = new MochaDictionary<>();
    IList<Value> out2 = new MochaList<>();
    exeStack2.push(ex2);
    ProgramState programState2 = new ProgramState(exeStack2, symTable2, out2);
    IRepository repository2 = new Repository(programState2);
    Controller controller2 = new Controller(repository2, true);
    try {
        controller2.allSteps();
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }

    // bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
    Statement ex3 = new CompoundStatement(new DeclarationStatement("a",new BoolType()),
 new CompoundStatement(new DeclarationStatement("v", new IntType()),
new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
 new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new
IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
VariableExpression("v"))))));
    IStack<Statement> exeStack3 = new MochaStack<>();
    IDictionary<String, Value> symTable3 = new MochaDictionary<>();
    IList<Value> out3 = new MochaList<>();
    exeStack3.push(ex3);
    ProgramState programState3 = new ProgramState(exeStack3, symTable3, out3);
    IRepository repository3 = new Repository(programState3);
    Controller controller3 = new Controller(repository3, true);
    try {
        controller3.allSteps();
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}