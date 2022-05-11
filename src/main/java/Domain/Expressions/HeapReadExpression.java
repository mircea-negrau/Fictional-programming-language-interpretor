package Domain.Expressions;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.HeapADT;
import Domain.ADTs.IDictionary;
import Domain.Types.IType;
import Domain.Types.ReferenceType;
import Domain.Values.IValue;
import Domain.Values.ReferenceValue;

public class HeapReadExpression extends Expression {
    Expression expression;

    public HeapReadExpression(Expression _expression) {
        this.expression = _expression;
    }

    @Override
    public IType typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        IType type = this.expression.typeCheck(typeEnvironment);
        if (type instanceof ReferenceType) {
            ReferenceType referenceType = (ReferenceType) type;
            return referenceType.getInner();
        } else
            throw new Exception("The HeapRead argument is not a ReferenceType!");
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, HeapADT heap) throws Exception {
        IValue value = this.expression.evaluate(table, heap);
        if (value instanceof ReferenceValue) {
            int address = ((ReferenceValue) value).getAddress();
            return heap.search(address);
        }
        //todo: exceptions
        throw new Exception();
    }

    @Override
    public String toString() {
        return "Heap-Read(" + expression.toString() + ")";
    }
}
