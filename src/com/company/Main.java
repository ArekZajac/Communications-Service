package com.company;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static javafx.application.Application.launch;

public class Main extends Application {

    static Stage window;
    static String style = "StyleSheet.css";
    static String defFont = "Arial";
    static String version = "19.4.13a";
    static int shift = 10;
    private File dataFile = new File(Data.getDocumentsPath() + "\\dataFile.txt");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;

        // Crates a new data file and populates it if one does not already exist. If it does then it reads the data from it.
        dataFile.createNewFile();
        if (dataFile.length() == 0) {
            Data.setData();
        }
        Data.getData();

        // If the application is closed, the data gets saved into the data file.
        primaryStage.setOnCloseRequest(t -> {
            try {
                Data.setData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Sets a title and icon for the application
        primaryStage.setTitle("Communications Service Client");
        primaryStage.getIcons().add(new Image("file:Images/iconMain.png"));

        // Directs the user to either the login or main window depending whether they previously chose to stay logged in.
        if (Data.stayLogged) {
//            menuMain();
        } else {
            menuLogin.menu();
        }
    }
}
