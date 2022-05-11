package Controller;

import Domain.ProgramState;

import java.util.List;

public interface IController {
    void addProgram(ProgramState newProgram);

    void executeAllSteps() throws Exception;

    void executeOneStepForAllPrograms(List<ProgramState> programStatesList) throws Exception;
}
