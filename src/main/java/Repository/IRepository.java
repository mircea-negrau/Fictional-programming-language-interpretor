package Repository;

import Domain.ADTs.ListADT;
import Domain.ProgramState;

public interface IRepository {
    void addProgram(ProgramState newProgram);

    ListADT<ProgramState> getProgramsList();

    void setProgramsList(ListADT<ProgramState> programsList);

    ProgramState endCurrentProgram() throws Exception;

    void logProgramStateExecution(ProgramState programState) throws Exception;

    void setFilePath(String filePath);
}
