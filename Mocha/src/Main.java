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
}