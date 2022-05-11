package Domain.Expressions;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.HeapADT;
import Domain.ADTs.IDictionary;
import Domain.Types.BooleanType;
import Domain.Types.IType;
import Domain.Types.IntType;
import Domain.Values.BooleanValue;
import Domain.Values.IValue;
import Domain.Values.IntValue;

public class RelationalExpression extends Expression {
    Expression expressionA;
    Expression expressionB;
    String operation;

    public RelationalExpression(String _operation, Expression _expressionA, Expression _expressionB) {
        expressionA = _expressionA;
        expressionB = _expressionB;
        operation = _operation;
    }

    @Override
    public IType typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        IType typeA, typeB;
        typeA = expressionA.typeCheck(typeEnvironment);
        typeB = expressionB.typeCheck(typeEnvironment);
        if (typeA.equals(new IntType())) {
            if (typeB.equals(new IntType())) {
                return new BooleanType();
            } else
                throw new Exception("Second operand is not an integer");
        } else
            throw new Exception("First operand is not an integer");
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, HeapADT heap) throws Exception {
        IValue firstEvaluation = expressionA.evaluate(table, heap);
        if (!firstEvaluation.getType().equals(new IntType())) {
            // todo: except
            throw new Exception();
        }
        IValue secondEvaluation = expressionB.evaluate(table, heap);
        if (!secondEvaluation.getType().equals(new IntType())) {
            // todo: except
            throw new Exception();
        }
        int firstValue = ((IntValue) firstEvaluation).getValue();
        int secondValue = ((IntValue) secondEvaluation).getValue();
        if (operation.equals("<")) return this.lesser(firstValue, secondValue);
        if (operation.equals("<=")) return this.lesserOrEqual(firstValue, secondValue);
        if (operation.equals("==")) return this.equal(firstValue, secondValue);
        if (operation.equals("!=")) return this.notEqual(firstValue, secondValue);
        if (operation.equals(">")) return this.greater(firstValue, secondValue);
        if (operation.equals(">=")) return this.greaterOrEqual(firstValue, secondValue);
        // todo: except
        throw new Exception();
    }

    private BooleanValue lesser(int first, int second) {
        return new BooleanValue(first < second);
    }

    private BooleanValue lesserOrEqual(int first, int second) {
        return new BooleanValue(first <= second);
    }

    private BooleanValue equal(int first, int second) {
        return new BooleanValue(first == second);
    }

    private BooleanValue notEqual(int first, int second) {
        return new BooleanValue(first != second);
    }

    private BooleanValue greater(int first, int second) {
        return new BooleanValue(first > second);
    }

    private BooleanValue greaterOrEqual(int first, int second) {
        return new BooleanValue(first >= second);
    }

    @Override
    public String toString() {
        return expressionA.toString() + " " + operation + " " + expressionB.toString();
    }
}
