package Domain.Types;

import Domain.Values.IValue;
import Domain.Values.IntValue;

public class IntType implements IType {

    @Override
    public IValue getDefaultValue() {
        return new IntValue();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        return object.getClass() == this.getClass();
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public String toString() {
        return "int";
    }
}
