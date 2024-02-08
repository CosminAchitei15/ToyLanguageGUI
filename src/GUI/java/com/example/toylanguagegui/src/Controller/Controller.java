package com.example.toylanguagegui.src.Controller;

import com.example.toylanguagegui.src.Model.RefValue;
import com.example.toylanguagegui.src.Model.Statement.IStmt;
import com.example.toylanguagegui.src.Model.PrgState;
import com.example.toylanguagegui.src.Model.Value;
import com.example.toylanguagegui.src.Repository.IRepository;
import com.example.toylanguagegui.src.utils.MyIHeap;
import com.example.toylanguagegui.src.utils.MyIStack;
import com.example.toylanguagegui.src.Controller.*;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    IRepository repo;
    ExecutorService executor;
    public Controller(IRepository r){
        repo = r;
    }

    public IRepository getRepo() {
        return repo;
    }

    public void setRepo(IRepository repo) {
        this.repo = repo;
    }

    //beh is short for behaviour

    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prg-> {
            try {
                repo.logPrgStateExecution(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        List<Callable<PrgState>> callList= prgList.stream()
                .map((PrgState p)->(Callable<PrgState>)(()->{return p.oneStep();}))
                .collect(Collectors.toList());
        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future->{
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                       System.out.println("End of program");
                    }
                    return null;
                }).filter(p->p!=null)
                .collect(Collectors.toList());
        prgList.addAll(newPrgList);
        prgList.forEach(prg-> {
            try {
                repo.logPrgStateExecution(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        repo.setRepo(prgList);
    }
    public void allStep() throws ContainerException, ExpressionException, StatementException, IOException, FileException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getRepo());
        while(prgList.size() > 0){
            MyIHeap<Value> garbagecollection = prgList.get(0).getHeap();
            garbagecollection.setHeap(safeGarbageCollector(getAddress_AllScopes(prgList), garbagecollection.getHeap()));
            oneStepForAllPrg(prgList);
            prgList=removeCompletedPrg(repo.getRepo());
        }
        executor.shutdownNow();
        repo.setRepo(prgList);
    }

    List<Integer> getAddress_AllScopes(List<PrgState> programStates){
        HashSet<Integer> alladdresses = new HashSet<>();
        for(PrgState prg: programStates){
            ConcurrentLinkedDeque<Integer> symTableAdr = prg.getSymTable().getMap().values()
                    .stream()
                    .filter(v-> v instanceof RefValue)
                    .map(v-> { RefValue v1 = (RefValue) v; return v1.getAddress();})
                    .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

            symTableAdr.stream()
                    .forEach(a -> {
                        Value v = prg.getHeap().getHeap().get(a);
                        if(v instanceof RefValue)
                            if(!symTableAdr.contains(((RefValue) v).getAddress()))
                                symTableAdr.add(((RefValue) v).getAddress());
                    });
            symTableAdr.forEach((Int) -> alladdresses.add(Int));
        }
        return alladdresses.stream().toList();
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream().filter(p->p.isNotCompleted()).collect(Collectors.toList());
    }

    public void oneStepForGUI() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgStateList = removeCompletedPrg(repo.getRepo());
        if(!prgStateList.isEmpty()){
            oneStepForAllPrg(repo.getRepo());
            prgStateList.forEach(e ->{
                try {
                    repo.logPrgStateExecution(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            removeCompletedPrg(repo.getRepo());
            executor.shutdownNow();
        }
    }
}
