package Controller;

import Domain.ADTs.*;
import Domain.ProgramState;
import Domain.Values.IValue;
import Domain.Values.ReferenceValue;
import Repository.Repository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements IController {
    Repository repository;
    ExecutorService executor;

    public Controller(Repository _repository) {
        this.repository = _repository;
    }

    @Override
    public void addProgram(ProgramState newProgram) {
        this.repository.addProgram(newProgram);
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> initialProgramsList) {
        return initialProgramsList.stream()
                .filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }

    public Repository getProgramRepo() {
        return repository;
    }

    public void executeAllSteps() throws Exception {
        this.executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStatesList = this.removeCompletedPrograms(this.repository.getProgramsList().toList());
        while (programStatesList.size() > 0) {
            HashSet<IValue> values = new HashSet<>();
            for (ProgramState programState : programStatesList) {
                DictionaryADT<String, IValue> currentSymbolTable = programState.getSymbolTable();
                values.addAll(currentSymbolTable.values());
            }
            Map<Integer, IValue> newHeap = this.unsafeGarbageCollector(this.getAddresses(values, programStatesList.get(0).getHeap().getHeapContent()), programStatesList.get(0).getHeap().getHeapContent());
            programStatesList.get(0).getHeap().setContent(newHeap);

            this.executeOneStepForAllPrograms(programStatesList);
            programStatesList = this.removeCompletedPrograms(this.repository.getProgramsList().toList());
        }
        this.executor.shutdownNow();
        this.repository.setProgramsList(new ListADT<>(programStatesList));
    }

    @Override
    public void executeOneStepForAllPrograms(List<ProgramState> programStatesList) throws Exception {
        List<Callable<ProgramState>> callList = programStatesList.stream().map((ProgramState program) -> (Callable<ProgramState>) (program::oneStep)).collect(Collectors.toList());
        List<ProgramState> newProgramStatesList = this.executor.invokeAll(callList).stream().map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        ).filter(Objects::nonNull).collect(Collectors.toList());
        programStatesList.addAll(newProgramStatesList);
        programStatesList.forEach(programState -> {
            try {
                this.repository.logProgramStateExecution(programState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.repository.setProgramsList(new ListADT<>(programStatesList));
    }


    List<Integer> getAddresses(Collection<IValue> symbolTableValues, Map<Integer, IValue> content) {
        List<Integer> references = symbolTableValues.stream().filter(v -> v instanceof ReferenceValue).map(v -> {
            ReferenceValue v1 = (ReferenceValue) v;
            return v1.getAddress();
        }).collect(Collectors.toList());
        List<Integer> referenced = content.values().stream().filter(e -> e instanceof ReferenceValue).map(v -> ((ReferenceValue) v).getAddress()).collect(Collectors.toList());
        referenced.addAll(references);
        return referenced;
    }

    Map<Integer, IValue> unsafeGarbageCollector(List<Integer> addresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream().filter(e -> addresses.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
