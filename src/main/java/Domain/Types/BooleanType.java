package Domain.Types;


import Domain.Values.BooleanValue;
import Domain.Values.IValue;

public class BooleanType implements IType {

    @Override
    public IValue getDefaultValue() {
        return new BooleanValue(false);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        return object.getClass() == this.getClass();
    }

    @Override
    public IType deepCopy() {
        return new BooleanType();
    }

    @Override
    public String toString() {
        return "boolean";
    }
}