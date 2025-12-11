package repository;

import exceptions.MochaException;
import model.programstate.ProgramState;
import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramStateList();
    void setProgramStateList(List<ProgramState> programStateList);

    void logPrgStateExec(ProgramState programState) throws MochaException;
}
