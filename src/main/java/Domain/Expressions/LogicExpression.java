package Domain.Expressions;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.HeapADT;
import Domain.ADTs.IDictionary;
import Domain.Types.BooleanType;
import Domain.Types.IType;
import Domain.Types.IntType;
import Domain.Values.BooleanValue;
import Domain.Values.IValue;
import Exception.InvalidSecondOperandException;
import Exception.InvalidFirstOperandException;
import Exception.InvalidOperatorException;

import java.util.Locale;

public class LogicExpression extends Expression {
    Expression expressionA;
    Expression expressionB;
    String operation;

    public LogicExpression(Expression _expressionA, String _operation, Expression _expressionB) {
        this.expressionA = _expressionA;
        this.operation = _operation.toUpperCase(Locale.ROOT);
        this.expressionB = _expressionB;
    }

    @Override
    public IType typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        IType typeA, typeB;
        typeA = expressionA.typeCheck(typeEnvironment);
        typeB = expressionB.typeCheck(typeEnvironment);
        if (typeA.equals(new BooleanType())) {
            if (typeB.equals(new BooleanType())) {
                return new BooleanType();
            } else
                throw new Exception("Second operand is not a boolean");
        } else
            throw new Exception("First operand is not a boolean");
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, HeapADT heap) throws Exception {
        IValue valueA = this.expressionA.evaluate(table, heap);
        if (valueA.getType().equals(new BooleanType())) {
            IValue valueB = this.expressionB.evaluate(table, heap);
            if (valueB.getType().equals(new BooleanType())) {
                boolean booleanA = ((BooleanValue) valueA).getValue();
                boolean booleanB = ((BooleanValue) valueB).getValue();
                if (this.operation.equals("AND")) {
                    return new BooleanValue(booleanA && booleanB);
                }
                if (this.operation.equals("OR")) {
                    return new BooleanValue(booleanA || booleanB);
                }
                throw new InvalidOperatorException();
            }
            throw new InvalidSecondOperandException();
        }
        throw new InvalidFirstOperandException();
    }

    @Override
    public String toString() {
        return expressionA.toString() + " " + operation + " " + expressionB.toString();
    }
}
