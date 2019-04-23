package com.company;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class menuLogin {

    static Label textOutput = new Label();

    public static void menu() {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Creates all the layouts needed.
        VBox layoutMain = new VBox();
        layoutMain.setSpacing(10);
        layoutMain.setAlignment(Pos.CENTER);

        HBox layoutMicro1 = new HBox();
        layoutMicro1.setSpacing(90);
        layoutMicro1.setAlignment(Pos.CENTER);

        HBox layoutMicro2 = new HBox();
        layoutMicro2.setSpacing(5);
        layoutMicro2.setAlignment(Pos.CENTER);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Creates all the elements of the user interface.

        Label labelTitle = new Label("Login");

        TextField textUsername = new TextField();
        textUsername.setPromptText("Username");
        textUsername.setMaxWidth(250);
        textUsername.setMinWidth(250);

        PasswordField textPassword = new PasswordField();
        textPassword.setPromptText("Password");
        textPassword.setMaxWidth(250);
        textPassword.setMaxWidth(250);

        Label labelBlank2 = new Label("");
        Label labelServerTitle = new Label("Server Connection");

        TextField textIP = new TextField();
        textIP.setPromptText("IP");
        textIP.setMaxWidth(170);
        textIP.setMinWidth(170);

        Label labelServer = new Label(":");
        labelServer.setStyle("-fx-font-weight: bold");

        TextField textPort = new TextField();
        textPort.setPromptText("Port");
        textPort.setMaxWidth(70);
        textPort.setMinWidth(70);

        Label labelBlank3 = new Label("");

        Button buttonLogin = new Button("Login");
        buttonLogin.setMinSize(60, 30);
        buttonLogin.setMaxSize(60, 30);

        CheckBox stayLogged = new CheckBox("Stay Logged In");

        Button newServerButton = new Button("Create a new server");
        newServerButton.setMinSize(120, 30);
        newServerButton.setMaxSize(120, 30);
        newServerButton.setOnAction(e-> {
            Thread serverThread = new Thread(new Server());
            serverThread.start();
        });

        layoutMicro2.getChildren().addAll(textIP, labelServer, textPort);
        layoutMicro1.getChildren().addAll(buttonLogin, stayLogged);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Does all that jaxafx stuff.

        layoutMain.getChildren().addAll(labelTitle, textUsername, textPassword, labelBlank2, labelServerTitle, layoutMicro2, labelBlank3, layoutMicro1, textOutput, newServerButton);
        Scene sceneLogin = new Scene(layoutMain, 400, 500);
        sceneLogin.getStylesheets().add("com/company/" + Main.style);
        Main.window.setScene(sceneLogin);
        Main.window.show();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Adds functionality to elements of the user interface.

        buttonLogin.setOnAction((e) -> {
            try {
                if (Connections.verifyLogin(textUsername.getText(), textPassword.getText())) {
                    Data.loggedName = textUsername.getText();
                    Data.serverConnection = textIP.getText();
                    Data.serverPort = Integer.parseInt(textPort.getText());
                    if (stayLogged.isSelected()) {
                        Data.loggedPassword = textPassword.getText();
                        Data.stayLogged = true;
                    }
                    try {
                        menuChat.start();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    textOutput.setText("Invalid Login");
                    Thread.sleep(1000);
                    textOutput.setText("");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
}