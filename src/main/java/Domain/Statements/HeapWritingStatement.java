package Domain.Statements;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.HeapADT;
import Domain.Expressions.Expression;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Types.ReferenceType;
import Domain.Values.IValue;
import Domain.Values.ReferenceValue;

public class HeapWritingStatement implements IStatement {
    String variableName;
    Expression expression;

    public HeapWritingStatement(String _variableName, Expression _expression) {
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
            throw new Exception("Heap writing statement: right hand side and left hand side have different types");
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        DictionaryADT<String, IValue> symbolTable = state.getSymbolTable();
        HeapADT heap = state.getHeap();
        if (symbolTable.contains(variableName)) {
            IValue value = symbolTable.search(variableName);
            IType type = value.getType();
            if (type instanceof ReferenceType) {
                ReferenceValue referenceValue = (ReferenceValue) value;
                int address = referenceValue.getAddress();
                if (heap.contains(address)) {
                    IValue evaluation = expression.evaluate(symbolTable, heap);
                    if (evaluation.getType().equals(((ReferenceType) type).getInner())) {
                        heap.update(address, evaluation);
                        state.setSymbolTable(symbolTable);
                        state.setHeap(heap);
                        return null;
                    }
                }
            }
        }
        throw new Exception();
    }

    public String toString() {
        return "Heap-Write(" + variableName + ", " + expression + ")";
    }
}
