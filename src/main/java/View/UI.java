package View;

import Controller.Controller;
import Domain.ADTs.DictionaryADT;
import Domain.Expressions.*;
import Domain.ProgramState;
import Domain.Statements.*;
import Domain.Types.BooleanType;
import Domain.Types.IntType;
import Domain.Types.ReferenceType;
import Domain.Types.StringType;
import Domain.Values.BooleanValue;
import Domain.Values.IntValue;
import Domain.Values.ReferenceValue;
import Domain.Values.StringValue;
import Repository.Repository;

public class UI {
    public static void main(String[] args) throws Exception {
        IStatement firstExample = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v",
                                new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v")
                        )
                )
        );
        firstExample.typeCheck(new DictionaryADT<>());
        ProgramState firstProgram = new ProgramState(firstExample);
        Repository firstRepository = new Repository("src/Files/log1.txt");
        firstRepository.addProgram(firstProgram);
        Controller firstController = new Controller(firstRepository);

        IStatement secondExample = new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(
                                new AssignStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(
                                        new AssignStatement("b", new ArithmeticExpression('+', new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                        new PrintStatement(new VariableExpression("b")
                                        )
                                )
                        )
                )
        );
        secondExample.typeCheck(new DictionaryADT<>());
        ProgramState secondProgram = new ProgramState(secondExample);
        Repository secondRepository = new Repository("src/Files/log2.txt");
        secondRepository.addProgram(secondProgram);
        Controller secondController = new Controller(secondRepository);

        IStatement thirdExample = new CompoundStatement(
                new VariableDeclarationStatement("a", new BooleanType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(
                                new AssignStatement("a", new ValueExpression(new BooleanValue(true))),
                                new CompoundStatement(
                                        new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v")
                                        )
                                )
                        )
                )
        );
        thirdExample.typeCheck(new DictionaryADT<>());
        ProgramState thirdProgram = new ProgramState(thirdExample);
        Repository thirdRepository = new Repository("src/Files/log3.txt");
        thirdRepository.addProgram(thirdProgram);
        Controller thirdController = new Controller(thirdRepository);

        IStatement fourthExample = new CompoundStatement(
                new CompoundStatement(
                        new VariableDeclarationStatement("varf", new StringType()),
                        new AssignStatement("varf", new ValueExpression(new StringValue("src/Files/test.in")))),
                new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")),
                        new CompoundStatement(
                                new CompoundStatement(
                                        new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(
                                                new CompoundStatement(
                                                        new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                        new PrintStatement(new VariableExpression("varc"))
                                                ),
                                                new CompoundStatement(
                                                        new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                        new PrintStatement(new VariableExpression("varc"))
                                                )
                                        )
                                ),
                                new CloseRFileStatement(new VariableExpression("varf"))
                        )
                )
        );
        fourthExample.typeCheck(new DictionaryADT<>());
        ProgramState fourthProgram = new ProgramState(fourthExample);
        Repository fourthRepository = new Repository("src/Files/log4.txt");
        fourthRepository.addProgram(fourthProgram);
        Controller fourthController = new Controller(fourthRepository);

        IStatement fifthExample = new CompoundStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ConstantExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new ConstantExpression(new ReferenceValue(1, new IntType()))),
                                        new CompoundStatement(
                                                new NewStatement("v", new ConstantExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a")))))
                                )
                        )
                )
        );
        fifthExample.typeCheck(new DictionaryADT<>());
        ProgramState fifthProgram = new ProgramState(fifthExample);
        Repository fifthRepository = new Repository("src/Files/log5.txt");
        fifthRepository.addProgram(fifthProgram);
        Controller fifthController = new Controller(fifthRepository);

        IStatement sixthExample = new CompoundStatement(
                new CompoundStatement(
                        new CompoundStatement(
                                new VariableDeclarationStatement("v", new IntType()),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                                        new AssignStatement("v", new ValueExpression(new IntValue(10))))),
                        new NewStatement("a", new ValueExpression(new IntValue(22)))
                ),
                new CompoundStatement(
                        new ForkStatement(new CompoundStatement(
                                new CompoundStatement(
                                        new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                        new AssignStatement("v", new ValueExpression(new IntValue(32)))
                                ),
                                new CompoundStatement(
                                        new PrintStatement(new VariableExpression("v")),
                                        new PrintStatement(new HeapReadExpression(new VariableExpression("a")))
                                )
                        )),
                        new CompoundStatement(
                                new PrintStatement(new VariableExpression("v")),
                                new PrintStatement(new HeapReadExpression(new VariableExpression("a")))
                        )
                )
        );
        sixthExample.typeCheck(new DictionaryADT<>());
        ProgramState sixthProgram = new ProgramState(sixthExample);
        Repository sixthRepository = new Repository("src/Files/log6.txt");
        sixthRepository.addProgram(sixthProgram);
        Controller sixthController = new Controller(sixthRepository);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", firstExample.toString(), firstController));
        menu.addCommand(new RunExample("2", secondExample.toString(), secondController));
        menu.addCommand(new RunExample("3", thirdExample.toString(), thirdController));
        menu.addCommand(new RunExample("4", fourthExample.toString(), fourthController));
        menu.addCommand(new RunExample("5", fifthExample.toString(), fifthController));
        menu.addCommand(new RunExample("6", sixthExample.toString(), sixthController));
        menu.show();
    }
}
