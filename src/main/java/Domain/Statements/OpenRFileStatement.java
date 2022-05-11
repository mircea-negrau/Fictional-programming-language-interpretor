package Domain.Statements;

import Domain.ADTs.DictionaryADT;
import Domain.Expressions.Expression;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Types.StringType;
import Domain.Values.IValue;
import Domain.Values.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenRFileStatement implements IStatement {
    Expression expression;

    public OpenRFileStatement(Expression _expression) {
        this.expression = _expression;
    }

    @Override
    public DictionaryADT<String, IType> typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        this.expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        DictionaryADT<String, IValue> symbolTable = state.getSymbolTable();
        IValue evaluation = this.expression.evaluate(symbolTable, state.getHeap());
        if (!evaluation.getType().equals(new StringType())) {
            // todo: except
            throw new Exception();
        }
        DictionaryADT<String, BufferedReader> fileTable = state.getFileTable();
        StringValue stringValue = (StringValue) evaluation;
        if (fileTable.contains(stringValue.getValue())) {
            // todo: except
            throw new Exception();
        }
        BufferedReader fileReader = new BufferedReader(new FileReader(stringValue.getValue()));
        fileTable.add(stringValue.getValue(), fileReader);
        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public String toString() {
        return "Open(" + expression.toString() + ")";
    }
}
