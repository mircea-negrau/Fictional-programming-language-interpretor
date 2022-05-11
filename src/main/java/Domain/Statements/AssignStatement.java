package Domain.Statements;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.IDictionary;
import Domain.Expressions.Expression;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Values.IValue;
import Exception.InvalidTypeException;
import Exception.InvalidKeyException;

public class AssignStatement implements IStatement {
    String variableName;
    Expression expression;

    public AssignStatement(String _variableName, Expression _expression) {
        this.variableName = _variableName;
        this.expression = _expression;
    }

    @Override
    public DictionaryADT<String, IType> typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        IType typeVariable = typeEnvironment.search(this.variableName);
        IType typeExpression = this.expression.typeCheck(typeEnvironment);
        if (typeVariable.equals(typeExpression))
            return typeEnvironment;
        else
            throw new Exception("Assignment: right hand side and left hand side have different types!");
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        DictionaryADT<String, IValue> symbolTable = state.getSymbolTable();
        if (symbolTable.contains(this.variableName)) {
            IValue value = expression.evaluate(symbolTable, state.getHeap());
            if (value.getType().equals(symbolTable.search(this.variableName).getType()))
                symbolTable.update(this.variableName, value);
            else
                throw new InvalidTypeException();
        } else
            throw new InvalidKeyException();
        state.setSymbolTable(symbolTable);
        return null;
    }

    public String toString() {
        return variableName + "=" + expression.toString();
    }
}
