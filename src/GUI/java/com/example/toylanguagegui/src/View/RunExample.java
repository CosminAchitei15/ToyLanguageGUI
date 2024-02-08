package com.example.toylanguagegui.src.View;

import com.example.toylanguagegui.src.Controller.Controller;
import com.example.toylanguagegui.src.Controller.*;
import com.example.toylanguagegui.src.Model.Value;
import com.example.toylanguagegui.src.utils.MyIList;

import java.io.IOException;

public class RunExample extends Command{

    private Controller ctr;
    public RunExample(String key, String description, Controller ctr) {
        super(key, description);
        this.ctr = ctr;
    }

    @Override
    public void execute(){
        try{
            ctr.allStep();
            //MyIList<Value> out = ctr.getRepo().getOut();
            //System.out.println(out.toString());
        }
        catch(StatementException | IOException | ExpressionException | ContainerException | FileException e){
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
