package Domain.Statements;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.IDictionary;
import Domain.ADTs.ListADT;
import Domain.Expressions.Expression;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Values.IValue;

public class PrintStatement implements IStatement {
    Expression expression;

    public PrintStatement(Expression _expression) {
        this.expression = _expression;
    }

    @Override
    public DictionaryADT<String, IType> typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        this.expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        ListADT<String> output = state.getOutput();
        DictionaryADT<String, IValue> symbolTable = state.getSymbolTable();
        output.append(this.expression.evaluate(symbolTable, state.getHeap()).toString());
        state.setOutput(output);
        return null;
    }

    @Override
    public String toString() {
        return "Print(" + expression.toString() + ')';
    }
}
