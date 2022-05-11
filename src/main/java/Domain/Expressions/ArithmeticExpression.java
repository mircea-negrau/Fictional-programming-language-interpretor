package Domain.Expressions;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.HeapADT;
import Domain.ADTs.IDictionary;
import Domain.Types.IType;
import Domain.Types.IntType;
import Domain.Values.IValue;
import Domain.Values.IntValue;
import Exception.InvalidSecondOperandException;
import Exception.InvalidFirstOperandException;
import Exception.DivisionByZeroException;
import Exception.InvalidOperatorException;

public class ArithmeticExpression extends Expression {
    Expression expressionA;
    Expression expressionB;
    char operation;

    public ArithmeticExpression(char _operation, Expression _expressionA, Expression _expressionB) {
        this.expressionA = _expressionA;
        this.operation = _operation;
        this.expressionB = _expressionB;
    }

    @Override
    public IType typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        IType typeA, typeB;
        typeA = expressionA.typeCheck(typeEnvironment);
        typeB = expressionB.typeCheck(typeEnvironment);
        if (typeA.equals(new IntType())) {
            if (typeB.equals(new IntType())) {
                return new IntType();
            } else
                throw new Exception("Second operand is not an integer");
        } else
            throw new Exception("First operand is not an integer");
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, HeapADT heap) throws Exception {
        IValue valueA = this.expressionA.evaluate(table, heap);
        if (valueA.getType().equals(new IntType())) {
            IValue valueB = this.expressionB.evaluate(table, heap);
            if (valueB.getType().equals(new IntType())) {
                IntValue integerA = (IntValue) valueA;
                IntValue integerB = (IntValue) valueB;
                int numberA = integerA.getValue();
                int numberB = integerB.getValue();
                switch (this.operation) {
                    case '+': {
                        return new IntValue(numberA + numberB);
                    }
                    case '-': {
                        return new IntValue(numberA - numberB);
                    }
                    case '*': {
                        return new IntValue(numberA * numberB);
                    }
                    case '/': {
                        if (numberB == 0)
                            throw new DivisionByZeroException();
                        return new IntValue(numberA / numberB);
                    }
                    default: {
                        throw new InvalidOperatorException();
                    }
                }
            }
            throw new InvalidSecondOperandException();
        }
        throw new InvalidFirstOperandException();
    }

    public char getOperation() {
        return this.operation;
    }

    public Expression getExpressionA() {
        return this.expressionA;
    }

    public Expression getExpressionB() {
        return this.expressionB;
    }

    @Override
    public String toString() {
        return expressionA.toString() + " " + operation + " " + expressionB.toString();
    }
}
