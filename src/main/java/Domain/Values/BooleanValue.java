package Domain.Values;

import Domain.Types.BooleanType;
import Domain.Types.IType;

public class BooleanValue implements IValue {
    boolean value;

    public BooleanValue() {
        this.value = false;
    }

    public BooleanValue(boolean _value) {
        this.value = _value;
    }

    @Override
    public IType getType() {
        return new BooleanType();
    }


    public boolean getValue() {
        return this.value;
    }

    @Override
    public IValue deepCopy() {
        return new BooleanValue(this.value);
    }


    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (object.getClass() != this.getClass())
            return false;
        return this.value == ((BooleanValue) object).getValue();
    }


    @Override
    public String toString() {
        if (!this.value)
            return "false";
        return "true";
    }
}
