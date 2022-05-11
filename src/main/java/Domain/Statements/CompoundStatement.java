package Domain.Statements;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.IDictionary;
import Domain.ADTs.StackADT;
import Domain.ProgramState;
import Domain.Types.IType;

public class CompoundStatement implements IStatement {
    IStatement firstStatement;
    IStatement secondStatement;

    public CompoundStatement(IStatement _firstStatement, IStatement _secondStatement) {
        this.firstStatement = _firstStatement;
        this.secondStatement = _secondStatement;
    }

    @Override
    public DictionaryADT<String, IType> typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        return secondStatement.typeCheck(firstStatement.typeCheck(typeEnvironment));
    }

    @Override
    public ProgramState execute(ProgramState state) {
        StackADT<IStatement> executionStack = state.getExecutionStack();
        executionStack.push(secondStatement);
        executionStack.push(firstStatement);
        state.setExecutionStack(executionStack);
        return null;
    }

    @Override
    public String toString() {
        return "(" + firstStatement.toString() + "; " + secondStatement.toString() + ")";
    }
}
