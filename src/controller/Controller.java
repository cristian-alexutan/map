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

        repository.setProgramStateList(prgList);
    }

    public boolean conservativeGarbageCollector(List<ProgramState> programStates) {
        List<Integer> allAddresses = programStates.stream()
                .flatMap(prg -> getAccessibleAddresses(prg.getSymTable().getContent().values(), prg.getHeap().getContent()).stream())
                .distinct()
                .collect(Collectors.toList());

        boolean erased = false;
        for (ProgramState prg : programStates) {
            Map<Integer, Value> oldHeap = prg.getHeap().getContent();
            Map<Integer, Value> newHeap = safeGarbageCollector(allAddresses, oldHeap);
            if (newHeap.size() != oldHeap.size()) {
                erased = true;
            }
            prg.getHeap().setContent(newHeap);
        }

        return erased;
    }

    public void oneStepForAllProgramsWithGC() throws MochaException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);

        List<ProgramState> programStates = removeCompletedPrograms(repository.getProgramStateList());
        if (programStates.isEmpty()) {
            executor.shutdownNow();
            return;
        }
        if(displayFlag) {
            for (ProgramState prg : programStates) {
                repository.logPrgStateExec(prg);
            }
        }
        oneStepForAllPrograms(programStates);
        if(displayFlag) {
            for (ProgramState prg : programStates) {
                repository.logPrgStateExec(prg);
            }
        }
        boolean erased = conservativeGarbageCollector(programStates);
        if(displayFlag && erased) {
            for (ProgramState prg : programStates) {
                repository.logPrgStateExec(prg);
            }
        }

        executor.shutdownNow();
    }

    public void allSteps() throws MochaException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrograms(repository.getProgramStateList());

        if(displayFlag) {
            for (ProgramState prg : programStates) {
                repository.logPrgStateExec(prg);
            }
        }

        while (!programStates.isEmpty()) {
            try {
                oneStepForAllPrograms(programStates);
                if(displayFlag) {
                    for (ProgramState prg : programStates) {
                        repository.logPrgStateExec(prg);
                    }
                }
                boolean erased = conservativeGarbageCollector(programStates);
                if(displayFlag && erased) {
                    for (ProgramState prg : programStates) {
                        repository.logPrgStateExec(prg);
                    }
                }
                programStates = removeCompletedPrograms(repository.getProgramStateList());
            }
            catch (InterruptedException e) {
                throw new MochaExecutionException("Execution interrupted: " + e.getMessage());
            }
        }
        executor.shutdownNow();
        repository.setProgramStateList(programStates);
    }

    public int getProgramStatesCount() {
        return repository.getProgramStateList().size();
    }

    public List<ProgramState> getProgramStates() {
        return repository.getProgramStateList();
    }

    public ProgramState getProgramStateById(int id) {
        for (ProgramState prg : removeCompletedPrograms(repository.getProgramStateList())) {
            if (prg.getId() == id) {
                return prg;
            }
        }
        return null;
    }
}
