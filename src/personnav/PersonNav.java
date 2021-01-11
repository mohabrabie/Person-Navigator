/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personnav;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
/**
 *
 * @author Mohab
 */


public class PersonNav extends Application {
    ConnectDB DB = new ConnectDB();
    List<Person> Data;
    GridPane grid = new GridPane();
    GridPane gridb = new GridPane();
    BorderPane pane = new BorderPane();
    FlowPane flow = new FlowPane();
    Scene scene = new Scene(pane, 500, 300);
    Text ID = new Text("ID");
    Text FName = new Text("First Name");
    Text LName = new Text("Last Name");
    Text MName = new Text("Middle Name");
    Text Email = new Text("Email");
    Text Phone = new Text("Phone");
    TextField id = new TextField();
    TextField fname = new TextField();
    TextField lname = new TextField();
    TextField mname = new TextField();
    TextField email = new TextField();
    TextField phone = new TextField();
    Button New = new Button("New");
    Button Update = new Button("Update");
    Button Delete = new Button("Delete");
    Button First = new Button("First");
    Button Last = new Button("Last");
    Button Prev = new Button("Previous");
    Button Next = new Button("Next");
    Button Clear = new Button("Clear");
    int count = 0,max;
    @Override
    public void init() throws ClassNotFoundException
    {
        GetData();
        //grid to show data
        grid.setPadding(new Insets(20, 20, 20, 20));
        gridb.setPadding(new Insets(10, 10, 10, 10));
        grid.setMinSize(600, 300);
        //grid.setVgap(5);
        //grid.setHgap(5);
        
        //show lables
        grid.add(ID, 1, 1);
        grid.add(FName, 1, 2);
        grid.add(MName, 1, 3);
        grid.add(LName, 1, 4);
        grid.add(Email, 1, 5);
        grid.add(Phone, 1, 6);
        
        //show colmns
        grid.add(id, 2, 1);
        grid.add(fname, 2, 2);
        grid.add(mname, 2, 3);
        grid.add(lname, 2, 4);
        grid.add(email, 2, 5);
        grid.add(phone, 2, 6);
        
        //fill start data
        ViewData(0);
        
        //add button
        grid.add(Prev, 0, 0);
        grid.add(Next, 3, 0);
        grid.add(First, 1, 7);
        gridb.add(New, 0, 2);
        gridb.add(Delete, 1, 2);
        gridb.add(Update, 2, 2);
        grid.add(Last, 3, 7);
        grid.add(Clear,3,8);

        
        //adding buttons to flow pane
        //flow.getChildren().addAll(New,Update,Delete,First,Prev,Next,Last);
        //flow.setStyle("-fx-background-color: #AEAEAE;");
        grid.add(gridb,2,7);
        
        grid.setStyle("-fx-background-color: #D8BFD8;");
        pane.setCenter(grid);
        //pane.setBottom(flow);
        
        //pane.setPrefSize(scene.getWidth(), scene.getHeight());
    }
    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException {
        
        
        New.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    DB.Insert(Integer.parseInt(id.getText()),fname.getText(),mname.getText()
                            , lname.getText(),email.getText(), phone.getText());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PersonNav.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    GetData();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PersonNav.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    DB.UpDate(Integer.parseInt(id.getText()),fname.getText(),mname.getText()
                            , lname.getText(),email.getText(), phone.getText());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PersonNav.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    GetData();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PersonNav.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                DB.Delete(Data.get(count).getId());
                Clear();
                try {
                    GetData();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PersonNav.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        First.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                count = 0;
                ViewData(count);
                try {
                    GetData();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PersonNav.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Prev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            count--;
            if(count < 0)
            {
               count = max-1;
            }
            ViewData(count);
            try {
                    GetData();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PersonNav.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                count++;
                if(count > max-1)
                {
                    count = 0;
                }
                ViewData(count);
                try {
                    GetData();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PersonNav.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Last.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                count = max-1;
                ViewData(count);
                try {
                    GetData();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PersonNav.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                Clear();
                try {
                    GetData();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PersonNav.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        //show GUI
        primaryStage.setTitle("Person Navigate System");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void Clear()
    {
                id.clear();
                fname.clear();
                mname.clear();
                lname.clear();
                email.clear();
                phone.clear();
    }
    public void GetData() throws ClassNotFoundException
    {
        Data = DB.selectAll();
        max = Data.size();
    }
    public void ViewData(int num)
    {
        id.setText(String.valueOf(Data.get(num).getId()));
        fname.setText(Data.get(num).getfName());
        mname.setText(Data.get(num).getmName());
        lname.setText(Data.get(num).getlName());
        email.setText(Data.get(num).getEmail());
        phone.setText(Data.get(num).getPhone());
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        launch(args);

    }
    
}
