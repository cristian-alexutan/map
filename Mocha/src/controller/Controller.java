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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;


public class Controller {
    private final IRepository repository;
    private boolean displayFlag;
    ExecutorService executor;

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

    List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates) {
        return programStates.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<ProgramState> programStates) throws MochaException, InterruptedException {
        List<ProgramState> prgList = programStates;

        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MochaException e) {
                System.out.println(e.getMessage());
            }
        });

        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (() -> {
                    return p.oneStep();
                }))
                .collect(Collectors.toList());

        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        prgList.addAll(newProgramList);

        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MochaException e) {
                System.out.println(e.getMessage());
            }
        });

        repository.setProgramStateList(prgList);
    }

    public void conservativeGarbageCollector(List<ProgramState> programStates) {
        List<Integer> allAddresses = programStates.stream()
                .flatMap(prg -> getAccessibleAddresses(prg.getSymTable().getContent().values(), prg.getHeap().getContent()).stream())
                .distinct()
                .collect(Collectors.toList());

        programStates.forEach(prg -> {
            prg.getHeap().setContent(safeGarbageCollector(allAddresses, prg.getHeap().getContent()));
        });
    }

    public void allSteps() throws MochaException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrograms(repository.getProgramStateList());

        while (!programStates.isEmpty()) {
            try {
                oneStepForAllPrograms(programStates);
                conservativeGarbageCollector(programStates);
                programStates = removeCompletedPrograms(repository.getProgramStateList());
            }
            catch (InterruptedException e) {
                throw new MochaExecutionException("Execution interrupted: " + e.getMessage());
            }
        }
        executor.shutdownNow();
        repository.setProgramStateList(programStates);
    }
}
