package com.example.toylanguagegui.src.View;

import com.example.toylanguagegui.src.Controller.ContainerException;
import com.example.toylanguagegui.src.Controller.ExpressionException;
import com.example.toylanguagegui.src.Controller.StatementException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;
    public TextMenu(){
        commands = new HashMap<>();
    }
    public void addCommand(Command c){
        commands.put(c.getKey(), c);
    }
    private void printMenu(){
        for(Command c: commands.values()){
            String line = String.format("%4s: %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }

    public void show() throws StatementException, ContainerException, IOException, ExpressionException {
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.println("Give option: ");
            String key = scanner.nextLine();
            Command com = commands.get(key);
            if(com == null){
                System.out.println("Invalid option");
                continue;
            }
            com.execute();
        }
    }
}
