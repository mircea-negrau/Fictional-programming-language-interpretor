package Domain.Types;

import Domain.Values.IValue;

public interface IType {
    IValue getDefaultValue();

    boolean equals(Object object);

    IType deepCopy();

    String toString();
}
