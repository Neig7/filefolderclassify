package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.security.PublicKey;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {


    @FXML
    private javafx.scene.control.TextField textF;

    @FXML
    private javafx.scene.control.Button getFileNameButton;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
    }

    @FXML
    protected void onButton(ActionEvent evt){
        //  TextField textF = new TextField();

        File file = new File("C:\\test");
        File files[] = file.listFiles();
        int fileNum = files.length;

        System.out.println("filesLength is "+ fileNum);
        String filesAllString = "";

        for(int i = 0; i < files.length; i++){
            //System.out.println("File "+ (1 + i) + "->" + files[i]);
            filesAllString = filesAllString + files[i] +"\n";
        }
        //System.out.println(filesAllString);

        textF.setText(filesAllString);

    }



    public static void main(String[] args) {
        launch(args);
    }
}
