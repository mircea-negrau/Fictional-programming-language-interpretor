package Domain.Expressions;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.HeapADT;
import Domain.ADTs.IDictionary;
import Domain.Types.IType;
import Domain.Values.IValue;

public abstract class Expression {
    public abstract IType typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception;

    public abstract IValue evaluate(IDictionary<String, IValue> table, HeapADT heap) throws Exception;

    public abstract String toString();
}
