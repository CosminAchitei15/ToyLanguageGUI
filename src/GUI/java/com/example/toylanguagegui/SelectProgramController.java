package com.example.toylanguagegui;

import com.example.toylanguagegui.src.Model.Statement.IStmt;
import com.example.toylanguagegui.src.Model.*;
import com.example.toylanguagegui.src.Model.Statement.*;
import com.example.toylanguagegui.src.Model.Expressions.*;
import com.example.toylanguagegui.src.Controller.*;
import com.example.toylanguagegui.src.Repository.*;
import com.example.toylanguagegui.src.utils.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SelectProgramController {
    private List<IStmt> programs;
    private RunProgramController runProgramController;

    @FXML
    private Button executeButton;
    @FXML
    private ListView<String> programListView;

    public void setMainController(RunProgramController mainController){
        this.runProgramController = mainController;
    }

    private void processAllExamples() throws ExpressionException, StatementException {
        IStmt ex1 = new CompStatement(new VariableDeclarationStatement("v", new IntType()), new CompStatement(new AssignmentStatement
                ("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));

        IStmt ex2 = new CompStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompStatement(new VariableDeclarationStatement("b",new IntType()), new CompStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                        ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                        new CompStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new ValueExpression(new
                                IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        IStmt ex3 = new CompStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));

        IStmt declare_v4 = new VariableDeclarationStatement("v", new RefType(new IntType()));
        IStmt alloc_v4 = new HeapAllocationStatement(new ValueExpression(new IntValue(20)), "v");
        Exp read_v4 = new heapReadingExpression(new VariableExpression("v"));
        IStmt print_v4 = new PrintStatement(read_v4);
        IStmt write_v4 = new HeapWritingStatement("v", new ValueExpression(new IntValue(30)));
        Exp read_v4_2 = new heapReadingExpression(new VariableExpression("v"));
        Exp add4 = new ArithmeticExpression('+',read_v4_2, new ValueExpression(new IntValue(5)));
        IStmt print_v4_2 = new PrintStatement(add4);
        IStmt ex4 =  new CompStatement(declare_v4, new CompStatement(alloc_v4, new CompStatement(print_v4,
                new CompStatement(write_v4, print_v4_2))));

        IStmt declare_v5 = new VariableDeclarationStatement("v", new IntType());
        IStmt assign_v5_1 = new AssignmentStatement("v", new ValueExpression(new IntValue(4)));
        Exp rel_expr5 = new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), 5);
        IStmt print_v5_1 = new PrintStatement(new VariableExpression("v"));
        Exp arithmetic_v5 = new ArithmeticExpression('-',new VariableExpression("v"), new ValueExpression(new IntValue(1)) );
        IStmt assign_v5_2 = new AssignmentStatement("v", arithmetic_v5);
        IStmt compoundStatement_v5 = new CompStatement(print_v5_1, assign_v5_2);
        IStmt whileStatement_v5 = new WhileStatement(rel_expr5, compoundStatement_v5);
        IStmt print_v5_2 = new PrintStatement(new VariableExpression("v"));
        IStmt ex5 =new CompStatement(declare_v5, new CompStatement(assign_v5_1, new CompStatement(whileStatement_v5, print_v5_2)));

        IStmt ex6_fork_stmt = new CompStatement(
                new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                new CompStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                        new CompStatement(
                                new PrintStatement(new VariableExpression("v")),
                                new PrintStatement(new heapReadingExpression(new VariableExpression("a"))))));
        IStmt ex6 = new CompStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompStatement(
                        new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompStatement(
                                new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompStatement(
                                        new HeapAllocationStatement(new ValueExpression(new IntValue(22)), "a"),
                                        new CompStatement(
                                                new ForkStatement(ex6_fork_stmt),
                                                new CompStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new heapReadingExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        IStmt ex7fork = new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(7)), 1), new CompStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))))));
        IStmt ex7 = new CompStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompStatement(new ForkStatement(ex7fork), ex7fork));

        //CONDITIONAL ASSIGNMENT PROGRAM
        IStmt ex8 = new CompStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                new CompStatement(new VariableDeclarationStatement("b", new RefType(new IntType())),
                        new CompStatement(new VariableDeclarationStatement("v", new IntType()),
                                new CompStatement(new HeapAllocationStatement(new ValueExpression(new IntValue(0)), "a"),
                                        new CompStatement(new HeapAllocationStatement(new ValueExpression(new IntValue(0)), "b"),
                                                new CompStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(1))),
                                                        new CompStatement(new HeapWritingStatement("b", new ValueExpression(new IntValue(2))),
                                                                new CompStatement(new ConditionalAssignmentStmt("v", new RelationalExpression(new heapReadingExpression(new VariableExpression("a")), new heapReadingExpression(new VariableExpression("b")), 1), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
                                                                        new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                                                new CompStatement(new ConditionalAssignmentStmt("v", new RelationalExpression(new ArithmeticExpression('-', new heapReadingExpression(new VariableExpression("b")), new ValueExpression(new IntValue(2))), new heapReadingExpression(new VariableExpression("a")), 5), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
                                                                                        new PrintStatement(new VariableExpression("v"))))))))))));
        programs = new ArrayList<>(Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8));
    }

    private List<String> getStrings(){
        return programs.stream().map(IStmt::toString).collect(Collectors.toList());
    }

    public void initialize() {
        try {
            processAllExamples();
        } catch (ExpressionException e) {
            throw new RuntimeException(e);
        } catch (StatementException e) {
            throw new RuntimeException(e);
        }
        programListView.setItems(FXCollections.observableArrayList(getStrings()));

        executeButton.setOnAction(actionEvent -> {
            int selectedIndex = programListView.getSelectionModel().getSelectedIndex();

            IStmt selectedProgram = programs.get(selectedIndex);
            MyIStack<IStmt> exeStack = new MyStack<>();
            MyIDictionary<String, Value> symTable = new MyDictionary<>();
            MyIList<Value> output = new MyList<>();
            MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
            MyIHeap<Value> heap = new MyHeap<>();

            MyIDictionary<String, Type> typechecker = new MyDictionary<String, Type>();
            try {
                selectedProgram.typecheck(typechecker);
            } catch (StatementException e) {
                throw new RuntimeException(e);
            } catch (ExpressionException e) {
                throw new RuntimeException(e);
            }

            PrgState program = new PrgState(exeStack, symTable, output, selectedProgram, fileTable, heap, 1);
            IRepository repo = new Repository("log" + selectedIndex +".txt");
            repo.add(program);
            Controller controller = new Controller(repo);
            runProgramController.setController(controller);
        });
    }
}
