package Domain.Statements;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.HeapADT;
import Domain.ADTs.ListADT;
import Domain.ADTs.StackADT;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Values.IValue;

import java.io.BufferedReader;

public class ForkStatement implements IStatement {
    IStatement statement;

    public ForkStatement(IStatement _statement) {
        this.statement = _statement;
    }

    @Override
    public DictionaryADT<String, IType> typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        statement.typeCheck(typeEnvironment.deepCopy());
        return typeEnvironment;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        StackADT<IStatement> forkedExecutionStack = new StackADT<>();

        DictionaryADT<String, IValue> symbolTable = state.getSymbolTable();
        DictionaryADT<String, IValue> forkedSymbolTable = symbolTable.deepCopy();

        HeapADT heap = state.getHeap();

        DictionaryADT<String, BufferedReader> fileTable = state.getFileTable();

        ListADT<String> output = state.getOutput();

        return new ProgramState(forkedExecutionStack, forkedSymbolTable, output, this.statement, fileTable, heap);
    }

    @Override
    public String toString() {
        return "Fork(" + statement.toString() + ");";
    }
}
