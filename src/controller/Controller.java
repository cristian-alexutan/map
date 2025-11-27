package controller;

import exceptions.MochaException;
import exceptions.MochaExecutionException;
import model.container.IStack;
import model.programstate.ProgramState;
import model.statement.Statement;
import repository.IRepository;
import model.value.Value;
import model.value.RefValue;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repository;
    private boolean displayFlag;

    public Controller(IRepository repository, boolean displayFlag) {
        this.repository = repository;
        this.displayFlag = displayFlag;
    }

    public List<Integer> getAccessibleAddresses(Collection<Value> symTableValues, Map<Integer, Value> heap) {
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();

        for (Value v : symTableValues) {
            if (v instanceof RefValue) {
                int addr = ((RefValue) v).getAddress();
                if (!visited.contains(addr)) {
                    visited.add(addr);
                    stack.push(addr);
                }
            }
        }
        while (!stack.isEmpty()) {
            int addr = stack.pop();
            Value hv = heap.get(addr);
            if (hv instanceof RefValue) {
                int inner = ((RefValue) hv).getAddress();
                if (!visited.contains(inner)) {
                    visited.add(inner);
                    stack.push(inner);
                }
            }
        }

        return new ArrayList<>(visited);
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> accessibleAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> ( accessibleAddresses.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public boolean getDisplayFlag() {
        return displayFlag;
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public ProgramState oneStep() throws MochaException {
        ProgramState programState = this.repository.getProgramState();
        IStack<Statement> exeStack = repository.getProgramState().getExeStack();
        if (exeStack.isEmpty()) throw new MochaExecutionException("ExeStack is empty");
        Statement currentStatement = exeStack.pop();
        return currentStatement.execute(programState);
    }

    public void allSteps() throws MochaException {
        ProgramState programState = this.repository.getProgramState();
        if (displayFlag) {
            repository.logPrgStateExec();
        }
        IStack<Statement> exeStack = programState.getExeStack();
        while (!exeStack.isEmpty()) {
            oneStep();
            if (displayFlag) {
                repository.logPrgStateExec();
            }
            programState.getHeap().setContent(
                    safeGarbageCollector(
                            getAccessibleAddresses(
                                    programState.getSymTable().getContent().values(),
                                    programState.getHeap().getContent()
                            ),
                            programState.getHeap().getContent()
                    )
            );
            if (displayFlag) {
                repository.logPrgStateExec();
            }
        }
    }
}
