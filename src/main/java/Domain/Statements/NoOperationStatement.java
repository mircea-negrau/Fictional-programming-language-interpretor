package Domain.Statements;

import Domain.ADTs.DictionaryADT;
import Domain.ProgramState;
import Domain.Types.IType;

public class NoOperationStatement implements IStatement {
    public NoOperationStatement() {
    }

    @Override
    public DictionaryADT<String, IType> typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        return typeEnvironment;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public String toString() {
        return "_";
    }
}
