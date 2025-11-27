package controller;

import exceptions.MochaException;
import exceptions.MochaExecutionException;
import model.container.IStack;
import model.programstate.ProgramState;
import model.statement.Statement;
import repository.IRepository;
import model.value.Value;
import model.value.RefValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repository;
    private boolean displayFlag;

    public Controller(IRepository repository, boolean displayFlag) {
        this.repository = repository;
        this.displayFlag = displayFlag;
    }

    public List<Integer> getAddressesFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddressesFromHeap(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddresses, List<Integer> heapAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> ( symTableAddresses.contains(e.getKey()) || heapAddresses.contains(e.getKey())))
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
                            getAddressesFromSymTable(programState.getSymTable().getContent().values()),
                            getAddressesFromHeap(programState.getHeap().getContent().values()),
                            programState.getHeap().getContent()
                    )
            );
            if (displayFlag) {
                repository.logPrgStateExec();
            }
        }
    }
}
