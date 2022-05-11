package Repository;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.HeapADT;
import Domain.ADTs.ListADT;
import Domain.ADTs.StackADT;
import Domain.ProgramState;
import Domain.Statements.IStatement;
import Domain.Values.IValue;
import Exception.IsEmptyException;
import Exception.InvalidFilePath;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class Repository implements IRepository {
    ListADT<ProgramState> programStates;
    String logFilePath;

    public Repository(String filePath) {
        this.programStates = new ListADT<>();
        this.logFilePath = filePath;
    }

    @Override
    public void addProgram(ProgramState newProgram) {
        this.programStates.append(newProgram);
    }

    @Override
    public ListADT<ProgramState> getProgramsList() {
        return this.programStates;
    }

    @Override
    public void setProgramsList(ListADT<ProgramState> programsList) {
        this.programStates = programsList;
    }

    public void setProgramsListFromList(List<ProgramState> programsList) {
        this.programStates = new ListADT<>(programsList);
    }

    @Override
    public ProgramState endCurrentProgram() throws Exception {
        if (this.programStates.size() > 0)
            return this.programStates.pop();
        throw new IsEmptyException();
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) throws Exception {
        if (this.logFilePath.equals(""))
            throw new InvalidFilePath();
        PrintWriter logFile = new PrintWriter(new FileWriter(this.logFilePath, true));
        logFile.println("Thread ID: " + programState.getId());
        StackADT<IStatement> executionStack = programState.getExecutionStack();
        this.logExecutionStack(executionStack, logFile);
        DictionaryADT<String, IValue> symbolTable = programState.getSymbolTable();
        this.logSymbolTable(symbolTable, logFile);
        ListADT<String> output = programState.getOutput();
        this.logOutput(output, logFile);
        DictionaryADT<String, BufferedReader> fileTable = programState.getFileTable();
        this.logFileTable(fileTable, logFile);
        HeapADT heap = programState.getHeap();
        this.logHeap(heap, logFile);
        logFile.println();
        logFile.close();
    }

    private void logFileTable(DictionaryADT<String, BufferedReader> fileTable, PrintWriter logFile) {
        logFile.println("- FileTable:");
        logFile.print(fileTable.toString());
    }

    private void logExecutionStack(StackADT<IStatement> executionStack, PrintWriter logFile) {
        logFile.println("- ExeStack:");
        logFile.print(executionStack.toString());
    }

    private void logSymbolTable(DictionaryADT<String, IValue> symbolTable, PrintWriter logFile) {
        logFile.println("- SymTable:");
        logFile.print(symbolTable.toString());
    }

    private void logOutput(ListADT<String> output, PrintWriter logFile) {
        logFile.println("- Out:");
        logFile.print(output.toString());
    }

    private void logHeap(HeapADT heap, PrintWriter logFile) {
        logFile.println("- Heap:");
        logFile.print(heap.toString());
    }

    public void setFilePath(String filePath) {
        this.logFilePath = filePath;
    }
}
