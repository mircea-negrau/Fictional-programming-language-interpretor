package Domain.Expressions;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.HeapADT;
import Domain.ADTs.IDictionary;
import Domain.Types.IType;
import Domain.Values.IValue;
import Exception.InvalidKeyException;

public class VariableExpression extends Expression {
    String variableName;

    public VariableExpression(String _variableName) {
        this.variableName = _variableName;
    }

    @Override
    public IType typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        return typeEnvironment.search(variableName);
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, HeapADT heap) throws InvalidKeyException {
        return table.search(this.variableName);
    }

    @Override
    public String toString() {
        return this.variableName;
    }
}
