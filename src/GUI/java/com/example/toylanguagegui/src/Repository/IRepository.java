package com.example.toylanguagegui.src.Repository;
import com.example.toylanguagegui.src.Model.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void add(PrgState p);
    void logPrgStateExecution(PrgState state) throws IOException;
    void setRepo(List<PrgState> repo);
    List<PrgState> getRepo();
    PrgState getPrgStateByID(int id);
}
