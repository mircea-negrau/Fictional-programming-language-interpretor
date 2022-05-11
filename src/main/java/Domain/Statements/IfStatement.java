package Domain.Statements;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.StackADT;
import Domain.Expressions.Expression;
import Domain.ProgramState;
import Domain.Types.BooleanType;
import Domain.Types.IType;
import Domain.Values.BooleanValue;
import Domain.Values.IValue;
import Exception.InvalidTypeException;


public class IfStatement implements IStatement {
    Expression expression;
    IStatement thenStatement;
    IStatement elseStatement;

    public IfStatement(Expression _expression, IStatement _then, IStatement _else) {
        this.expression = _expression;
        this.thenStatement = _then;
        this.elseStatement = _else;
    }

    @Override
    public DictionaryADT<String, IType> typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        IType typeExpression = this.expression.typeCheck(typeEnvironment);
        if (typeExpression.equals(new BooleanType())) {
            thenStatement.typeCheck(typeEnvironment.deepCopy());
            elseStatement.typeCheck(typeEnvironment.deepCopy());
            return typeEnvironment;
        } else
            throw new Exception("The conditions of IF has not the type boolean");
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        DictionaryADT<String, IValue> symbolsTable = state.getSymbolTable();
        StackADT<IStatement> executionStack = state.getExecutionStack();
        IValue conditionValue = this.expression.evaluate(symbolsTable, state.getHeap());
        if (!conditionValue.getType().equals(new BooleanType()))
            throw new InvalidTypeException();
        BooleanValue condition = (BooleanValue) conditionValue;
        if (condition.getValue()) {
            executionStack.push(this.thenStatement);
        } else {
            executionStack.push(this.elseStatement);
        }
        state.setExecutionStack(executionStack);
        return null;
    }

    public String toString() {
        return this.expression.toString() + " ? " + this.thenStatement.toString() + " : " + this.elseStatement.toString();
    }
}
