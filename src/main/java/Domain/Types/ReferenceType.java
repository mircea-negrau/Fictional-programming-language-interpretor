package Domain.Types;

import Domain.Values.IValue;
import Domain.Values.ReferenceValue;

public class ReferenceType implements IType {
    IType inner;

    public ReferenceType(IType _inner) {
        this.inner = _inner;
    }

    public IType getInner() {
        return this.inner;
    }

    public boolean equals(Object other) {
        if (other instanceof ReferenceType)
            return this.inner.equals(((ReferenceType) other).getInner());
        else
            return false;
    }

    @Override
    public IValue getDefaultValue() {
        return new ReferenceValue(0, this.inner);
    }

    @Override
    public IType deepCopy() {
        return new ReferenceType(this.inner.deepCopy());
    }

    public String toString() {
        return "Ref " + this.inner.toString();
    }
}
