package Domain.Values;

import Domain.Types.IType;
import Domain.Types.ReferenceType;

public class ReferenceValue implements IValue {
    int address;
    IType locationType;

    public ReferenceValue(Integer _address, IType _locationType) {
        this.address = _address;
        this.locationType = _locationType;
    }

    public int getAddress() {
        return this.address;
    }

    public IType getLocationType() {
        return this.locationType;
    }

    @Override
    public IType getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public IValue deepCopy() {
        return new ReferenceValue(address, this.getType());
    }

    @Override
    public String toString() {
        return "Ref(" + address + ")";
    }
}
