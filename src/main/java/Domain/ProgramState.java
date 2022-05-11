package Domain;


import Domain.ADTs.DictionaryADT;
import Domain.ADTs.HeapADT;
import Domain.ADTs.ListADT;
import Domain.ADTs.StackADT;
import Domain.Statements.IStatement;
import Domain.Values.IValue;

import java.io.BufferedReader;


public class ProgramState {
    static Integer freeThreadId = 0;

    static synchronized int getThreadId() {
        freeThreadId++;
        return freeThreadId;
    }

    Integer id;
    StackADT<IStatement> executionStack;
    DictionaryADT<String, IValue> symbolsTable;
    ListADT<String> output;
    DictionaryADT<String, BufferedReader> fileTable;
    HeapADT heap;
    IStatement originalProgram;

    public ProgramState(StackADT<IStatement> _executionStack, DictionaryADT<String, IValue> _symbolsTable, ListADT<String> _output, IStatement program, DictionaryADT<String, BufferedReader> _fileTable, HeapADT _heap) throws Exception {
        this.id = getThreadId();
        this.executionStack = _executionStack;
        this.symbolsTable = _symbolsTable;
        this.output = _output;
        this.fileTable = _fileTable;
        this.heap = _heap;
        executionStack.push(program);
        originalProgram = program;
    }

    public ProgramState(IStatement program) throws Exception {
        this.id = getThreadId();
        this.executionStack = new StackADT<>();
        this.symbolsTable = new DictionaryADT<>();
        this.output = new ListADT<>();
        this.fileTable = new DictionaryADT<>();
        this.heap = new HeapADT();
        executionStack.push(program);
        this.originalProgram = program;
    }

    public int getId() {
        return this.id;
    }

    public ListADT<String> getOutput() {
        return this.output;
    }

    public DictionaryADT<String, IValue> getSymbolTable() {
        return this.symbolsTable;
    }

    public HeapADT getHeap() {
        return this.heap;
    }

    public DictionaryADT<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public StackADT<IStatement> getExecutionStack() {
        return this.executionStack;
    }

    public void setSymbolTable(DictionaryADT<String, IValue> _symbolsTable) {
        this.symbolsTable = _symbolsTable;
    }

    public void setHeap(HeapADT _heap) {
        this.heap = _heap;
    }

    public void setExecutionStack(StackADT<IStatement> _executionStack) {
        this.executionStack = _executionStack;
    }

    public void setFileTable(DictionaryADT<String, BufferedReader> _fileTable) {
        this.fileTable = _fileTable;
    }

    public void setOutput(ListADT<String> _output) {
        this.output = _output;
    }

    public boolean isNotCompleted() {
        return !this.executionStack.isEmpty();
    }

    public ProgramState oneStep() throws Exception {
        if (this.executionStack.isEmpty())
            throw new Exception();
        IStatement currentStatement = this.executionStack.pop();
        return currentStatement.execute(this);
    }

    @Override

    public String toString() {
        return "ProgramState{" +
                "executionStack=" + executionStack.toString() +
                ", symbolsTable=" + symbolsTable.toString() +
                ", output=" + output.toString() +
                '}';
    }
}
