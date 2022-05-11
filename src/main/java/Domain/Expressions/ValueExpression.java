package Domain.Expressions;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.HeapADT;
import Domain.ADTs.IDictionary;
import Domain.Types.IType;
import Domain.Values.IValue;

public class ValueExpression extends Expression {
    IValue value;

    public ValueExpression(IValue _value) {
        this.value = _value;
    }

    @Override
    public IType typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        return value.getType();
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, HeapADT heap) {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
