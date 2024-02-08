package com.example.toylanguagegui.src.Model;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.FileException;
import com.example.toylanguagegui.src.Controller.StatementException;
import com.example.toylanguagegui.src.Model.Statement.IStmt;
import com.example.toylanguagegui.src.utils.MyIDictionary;
import com.example.toylanguagegui.src.utils.MyIHeap;
import com.example.toylanguagegui.src.utils.MyIList;
import com.example.toylanguagegui.src.utils.MyIStack;

import java.io.BufferedReader;
import java.io.IOException;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    IStmt originalProgram;
    MyIDictionary<StringValue, BufferedReader> fileTable;
    MyIHeap<Value> heap;
    int threadid = 1;
    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, IStmt prg, MyIDictionary<StringValue, BufferedReader> fltbl, MyIHeap<Value> hip, int id){
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        originalProgram = deepCopy(prg);
        fileTable = fltbl;
        heap = hip;
        this.threadid = id;
        stk.push(prg);
    }

    private IStmt deepCopy(IStmt prg) {
        return prg;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public MyIHeap<Value> getHeap() {
        return heap;
    }

    public void setHeap(MyIHeap<Value> heap) {
        this.heap = heap;
    }

    public boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws ContainerException, ExpressionException, StatementException, FileException, IOException {
        if(exeStack.isEmpty()) throw new ContainerException("prgstate stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public int getThreadid() {
        return threadid;
    }

    public void setThreadid(int threadid) {
        this.threadid = threadid;
    }

    @Override
    public String toString() {
        return "PrgState for thread number " + threadid +" {\n" +
                "\texeStack=" + exeStack.toString() +
                ", \n\tsymTable=" + symTable.toString() +
                ", \n\tout=" + out.toString() +
                ", \n\toriginalProgram=" + originalProgram.toString() +
                ", \n\tfileTable=" + fileTable.toString() +
                ", \n\theap =" + heap.toString() +
                "\n" +
                '}';
    }
}
