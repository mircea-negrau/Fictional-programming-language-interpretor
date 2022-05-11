package Domain.Expressions;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.HeapADT;
import Domain.ADTs.IDictionary;
import Domain.Types.IType;
import Domain.Values.IValue;

public class ConstantExpression extends Expression {
    IValue constant;

    public ConstantExpression(IValue _constant) {
        this.constant = _constant;
    }

    @Override
    public IType typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        return constant.getType();
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, HeapADT heap) {
        return this.constant;
    }

    @Override
    public String toString() {
        return this.constant.toString();
    }
}
