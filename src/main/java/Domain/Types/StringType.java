package Domain.Types;

import Domain.Values.IValue;
import Domain.Values.StringValue;

public class StringType implements IType {
    @Override
    public IValue getDefaultValue() {
        return new StringValue();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        return object.getClass() == this.getClass();
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "string";
    }
}
