package Domain.Statements;

import Domain.ADTs.DictionaryADT;
import Domain.Expressions.Expression;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Types.IntType;
import Domain.Types.StringType;
import Domain.Values.IValue;
import Domain.Values.IntValue;
import Domain.Values.StringValue;

import java.io.BufferedReader;

public class ReadFileStatement implements IStatement {
    Expression expression;
    String variableName;

    public ReadFileStatement(Expression _expression, String _variableName) {
        this.expression = _expression;
        this.variableName = _variableName;
    }

    @Override
    public DictionaryADT<String, IType> typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        IType typeVariable = typeEnvironment.search(this.variableName);
        IType typeExpression = this.expression.typeCheck(typeEnvironment);
        if (typeVariable.equals(new IntType()) && typeExpression.equals(new StringType()))
            return typeEnvironment;
        else
            throw new Exception("Read file statement: invalid types");
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        DictionaryADT<String, IValue> symbolTable = state.getSymbolTable();
        if (symbolTable.contains(this.variableName)) {
            IValue variableValue = symbolTable.search(this.variableName);
            if (!variableValue.getType().equals(new IntType())) {
                // todo: except
                throw new Exception();
            }
            IValue evaluation = this.expression.evaluate(symbolTable, state.getHeap());
            if (!evaluation.getType().equals(new StringType())) {
                // todo: except
                throw new Exception();
            }
            StringValue path = (StringValue) evaluation;
            DictionaryADT<String, BufferedReader> fileTable = state.getFileTable();
            if (!fileTable.contains(path.getValue())) {
                // todo: except
                throw new Exception();
            }
            BufferedReader reader = fileTable.search(path.getValue());
            String line = reader.readLine();
            IntValue value;
            if (line == null) {
                value = new IntValue(0);
            } else {
                int intValue = Integer.parseInt(line);
                value = new IntValue(intValue);
            }
            symbolTable.update(this.variableName, value);
            state.setSymbolTable(symbolTable);
            return null;
        } else {
            // todo: except
            throw new Exception();
        }
    }

    @Override
    public String toString() {
        return "Read(" + expression.toString() + ", " + variableName + ")";
    }
}
