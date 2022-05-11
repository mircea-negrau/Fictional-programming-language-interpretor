package Domain.Statements;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.HeapADT;
import Domain.Expressions.Expression;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Types.IntType;
import Domain.Types.ReferenceType;
import Domain.Values.IValue;
import Domain.Values.ReferenceValue;

public class NewStatement implements IStatement {
    String variableName;
    Expression expression;

    public NewStatement(String _variableName, Expression _expression) {
        this.variableName = _variableName;
        this.expression = _expression;
    }

    @Override
    public DictionaryADT<String, IType> typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        IType typeVariable = typeEnvironment.search(this.variableName);
        IType typeExpression = this.expression.typeCheck(typeEnvironment);
        if (typeVariable.equals(new ReferenceType(typeExpression)))
            return typeEnvironment;
        else
            throw new Exception("New statement: right hands side and left hand side have different types");
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        DictionaryADT<String, IValue> symbolTable = state.getSymbolTable();
        if (symbolTable.contains(this.variableName)) {
            IValue variable = symbolTable.search(variableName);
            if (variable.getType() instanceof ReferenceType) {
                IValue expressionValue = expression.evaluate(symbolTable, state.getHeap());
                IType expressionType = expressionValue.getType();
                ReferenceValue referenceValue = (ReferenceValue) variable;
                if (expressionType.equals(referenceValue.getLocationType())) {
                    HeapADT heap = state.getHeap();
                    int address = heap.getNewAddress();
                    heap.add(address, expressionValue);
                    symbolTable.update(this.variableName, new ReferenceValue(address, new IntType()));
                    state.setHeap(heap);
                    state.setSymbolTable(symbolTable);
                    return null;
                }
            }
        }
        // todo: Add exceptions
        throw new Exception();
    }

    public String toString() {
        return "new(" + variableName + "," + expression.toString() + ")";
    }
}
