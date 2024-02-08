package com.example.toylanguagegui.src.Repository;
import com.example.toylanguagegui.src.Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<PrgState> repo;
    private String logFilePath;

    public Repository(){
        repo = new ArrayList<PrgState>();
        this.logFilePath = "";
    }

    public Repository(String logFilePath){
        this.logFilePath = logFilePath;
        repo = new ArrayList<PrgState>();
    }

    public List<PrgState> getRepo() {
        return repo;
    }

    public void setRepo(List<PrgState> repo) {
        this.repo = repo;
    }

    @Override
    public void add(PrgState p){
        repo.add(p);
    }

    @Override
    public void logPrgStateExecution(PrgState state) throws IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.write(state.toString());
        logFile.close();
    }

    @Override
    public String toString() {
        return "Repository{" +
                "repo=" + repo +
                '}';
    }

    @Override
    public PrgState getPrgStateByID(int id){
        for(PrgState p : repo){
            if(p.getThreadid() == id)
                return p;
        }
        return null;
    }
}
