package Domain.Statements;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.IDictionary;
import Domain.ProgramState;
import Domain.Types.IType;

public interface IStatement {
    DictionaryADT<String, IType> typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception;

    ProgramState execute(ProgramState state) throws Exception;
}
