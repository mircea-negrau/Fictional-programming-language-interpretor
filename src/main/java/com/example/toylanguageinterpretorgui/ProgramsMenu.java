package com.example.toylanguageinterpretorgui;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramsMenu {
    private final Map<IStatement, Controller> controllersByStatements;
    private final List<IStatement> programStatements;

    ProgramsMenu() {
        controllersByStatements = new HashMap<>();
        programStatements = new ArrayList<>();
        try {
            this.populateProgramsMenu();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.exit(1);
        }
    }

    public List<IStatement> getStatementsList() {
        return programStatements;
    }

    public Controller getControllerByIndex(Integer index) {
        return controllersByStatements.get(programStatements.get(index));
    }

    public void populateProgramsMenu() throws Exception {
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
        Repository firstRepository = new Repository("src/main/java/Files/log1.txt");
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
        Repository secondRepository = new Repository("src/main/java/Files/log2.txt");
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
        Repository thirdRepository = new Repository("src/main/java/Files/log3.txt");
        thirdRepository.addProgram(thirdProgram);
        Controller thirdController = new Controller(thirdRepository);

        IStatement fourthExample = new CompoundStatement(
                new CompoundStatement(
                        new VariableDeclarationStatement("varf", new StringType()),
                        new AssignStatement("varf", new ValueExpression(new StringValue("src/main/java/Files/test.in")))),
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
        Repository fourthRepository = new Repository("src/main/java/Files/log4.txt");
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
        Repository fifthRepository = new Repository("src/main/java/Files/log5.txt");
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
        Repository sixthRepository = new Repository("src/main/java/Files/log6.txt");
        sixthRepository.addProgram(sixthProgram);
        Controller sixthController = new Controller(sixthRepository);


        IStatement seventhExample = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))), new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))
                )
        );
        seventhExample.typeCheck(new DictionaryADT<>());
        ProgramState seventhProgram = new ProgramState(seventhExample);
        Repository seventhRepository = new Repository("src/main/java/Files/log7.txt");
        seventhRepository.addProgram(seventhProgram);
        Controller seventhController = new Controller(seventhRepository);

        IStatement eightiethExample = new CompoundStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a"))))
                                        )
                                )
                        )
                )
        );
        eightiethExample.typeCheck(new DictionaryADT<>());
        ProgramState eightiethProgram = new ProgramState(eightiethExample);
        Repository eightiethRepository = new Repository("src/main/java/Files/log7.txt");
        eightiethRepository.addProgram(eightiethProgram);
        Controller eightiethController = new Controller(eightiethRepository);


        controllersByStatements.put(firstExample, firstController);
        controllersByStatements.put(secondExample, secondController);
        controllersByStatements.put(thirdExample, thirdController);
        controllersByStatements.put(fourthExample, fourthController);
        controllersByStatements.put(fifthExample, fifthController);
        controllersByStatements.put(sixthExample, sixthController);
        controllersByStatements.put(seventhExample, seventhController);
        controllersByStatements.put(eightiethExample, eightiethController);

        programStatements.add(firstExample);
        programStatements.add(secondExample);
        programStatements.add(thirdExample);
        programStatements.add(fourthExample);
        programStatements.add(fifthExample);
        programStatements.add(sixthExample);
        programStatements.add(seventhExample);
        programStatements.add(eightiethExample);
    }
}
