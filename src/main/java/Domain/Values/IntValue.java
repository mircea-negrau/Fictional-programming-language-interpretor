package Domain.Values;

import Domain.Types.IType;
import Domain.Types.IntType;

public class IntValue implements IValue {
    int value;

    public IntValue() {
        this.value = 0;
    }

    public IntValue(int _value) {
        this.value = _value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(this.value);
    }
    
    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (object.getClass() != this.getClass())
            return false;
        return this.value == ((IntValue) object).getValue();
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}
