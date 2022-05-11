package Domain.Statements;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.HeapADT;
import Domain.ADTs.StackADT;
import Domain.Expressions.Expression;
import Domain.ProgramState;
import Domain.Types.BooleanType;
import Domain.Types.IType;
import Domain.Values.BooleanValue;
import Domain.Values.IValue;

public class WhileStatement implements IStatement {
    Expression expression;
    IStatement statement;

    public WhileStatement(Expression _expression, IStatement _statement) {
        this.expression = _expression;
        this.statement = _statement;
    }

    @Override
    public DictionaryADT<String, IType> typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        IType typeExpression = this.expression.typeCheck(typeEnvironment);
        if (typeExpression.equals(new BooleanType())) {
            statement.typeCheck(typeEnvironment.deepCopy());
            return typeEnvironment;
        } else
            throw new Exception("The type check of the while statement failed");
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        DictionaryADT<String, IValue> symbolTable = state.getSymbolTable();
        HeapADT heap = state.getHeap();
        IValue evaluation = expression.evaluate(symbolTable, heap);
        if (evaluation instanceof BooleanValue) {
            if (!((BooleanValue) evaluation).getValue()) {
                return null;
            }
            StackADT<IStatement> executionStack = state.getExecutionStack();
            executionStack.push(this);
            executionStack.push(statement);
            state.setExecutionStack(executionStack);
            return null;
        }
        throw new Exception("Expression condition is not boolean!");
    }

    @Override
    public String toString() {
        return "While("+expression.toString() + ") " + statement.toString();
    }
}
