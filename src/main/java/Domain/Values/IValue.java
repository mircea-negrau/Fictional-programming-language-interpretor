package Domain.Values;

import Domain.Types.IType;

public interface IValue {
    IType getType();

    IValue deepCopy();

    boolean equals(Object object);

    String toString();
}
