package Domain.Statements;

import Domain.ADTs.DictionaryADT;
import Domain.Expressions.Expression;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Types.StringType;
import Domain.Values.IValue;
import Domain.Values.StringValue;

import java.io.BufferedReader;

public class CloseRFileStatement implements IStatement {
    Expression expression;

    public CloseRFileStatement(Expression _expression) {
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
        StringValue stringValue = (StringValue) evaluation;
        String string = stringValue.getValue();
        DictionaryADT<String, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.contains(string)) {
            // todo: except
            throw new Exception();
        }
        BufferedReader reader = fileTable.search(string);
        reader.close();
        fileTable.remove(string);
        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public String toString() {
        return "Close(" + expression.toString() + ")";
    }
}
