package Domain.Statements;

import Domain.ADTs.DictionaryADT;
import Domain.ADTs.IDictionary;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Values.IValue;
import Exception.VariableReinitializationException;

public class VariableDeclarationStatement implements IStatement {
    String variableName;
    IType variableType;

    public VariableDeclarationStatement(String _variableName, IType _variableType) {
        this.variableName = _variableName;
        this.variableType = _variableType;
    }

    @Override
    public DictionaryADT<String, IType> typeCheck(DictionaryADT<String, IType> typeEnvironment) throws Exception {
        typeEnvironment.add(this.variableName, this.variableType);
        return typeEnvironment;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        DictionaryADT<String, IValue> symbolTable = state.getSymbolTable();
        if (symbolTable.contains(this.variableName)) {
            throw new VariableReinitializationException();
        }
        symbolTable.add(variableName, variableType.getDefaultValue());
        state.setSymbolTable(symbolTable);
        return null;
    }

    @Override
    public String toString() {
        return variableType + " " + variableName;
    }
}
