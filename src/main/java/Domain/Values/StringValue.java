package Domain.Values;

import Domain.Types.IType;
import Domain.Types.StringType;

import java.util.Objects;

public class StringValue implements IValue {
    String value;

    public StringValue() {
        this.value = "";
    }

    public StringValue(String _value) {
        this.value = _value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }


    public String getValue() {
        return this.value;
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(this.value);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (object.getClass() != this.getClass())
            return false;
        return Objects.equals(this.value, ((StringValue) object).getValue());
    }
    
    @Override
    public String toString() {
        return this.value;
    }
}
