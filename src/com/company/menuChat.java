package com.company;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class menuChat {

    static BufferedReader input;
    static PrintWriter output;
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(50);
    JTextArea messageArea = new JTextArea(16, 50);

    public static void start() throws Exception {
        // Make connection and initialize streams
        Socket socket = new Socket(Data.serverConnection, Data.serverPort);
        input = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);

        new menuChat();
    }

    public menuChat() throws IOException {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception ignored){}
        VBox layout = new VBox();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);

        VBox layoutWrap = new VBox();

        VBox noConnection = new VBox();
        noConnection.setAlignment(Pos.CENTER);
        noConnection.setPadding(new Insets(50, 0, 0, 0));
        noConnection.setSpacing(25);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Alert alertAbout = new Alert(Alert.AlertType.INFORMATION);
        alertAbout.setTitle("About");
        alertAbout.setHeaderText("About");
        alertAbout.setContentText("Version: " + Main.version);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        MenuBar bar = new MenuBar();

        Menu menuFile = new Menu("File");
        MenuItem mf1 = new MenuItem("Settings");
        mf1.setOnAction(e -> Popups.windowSettings());
        MenuItem mf3 = new MenuItem("Disconnect");
        mf3.setOnAction(e -> {
            menuLogin.menu();
            Data.stayLogged = false;
            Data.loggedPassword = "";
        });
        MenuItem mf4 = new MenuItem("Exit");
        mf4.setOnAction(e -> Platform.exit());
        menuFile.getItems().addAll(mf1, mf3, mf4);

        Menu menuEdit = new Menu("Edit");
        MenuItem me1 = new MenuItem("Account");
        me1.setOnAction(e -> Popups.windowAccount());
        menuEdit.getItems().addAll(me1);

        Menu menuHelp = new Menu("Help");
        MenuItem mh1 = new MenuItem("Source Code");
        mh1.setOnAction(e -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/ArekZajac/Communications-Service-Client"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
        MenuItem mh2 = new MenuItem("About");
        mh2.setOnAction(e -> Popups.windowAbout());
        menuHelp.getItems().addAll(mh1, mh2);

        bar.getMenus().addAll(menuFile, menuEdit, menuHelp);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Label loginMessage = new Label();
        loginMessage.setText("Successfully logged in as " + Data.loggedName + "\n ");

        Button chatStart = new Button("Enter Chat");
        chatStart.setOnAction(e -> {
            Main.window.close();
            try {
                chattingWindow();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        layout.getChildren().addAll(loginMessage, chatStart);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        javafx.scene.image.Image imageError = new Image("file:Images/no-connection.png");
        ImageView iE = new ImageView(imageError);
        iE.setPreserveRatio(true);
        iE.setFitHeight(100);
        iE.setCache(true);

        javafx.scene.control.Label connectionMessage = new javafx.scene.control.Label("NO CONNECTION ESTABLISHED TO SERVER");
        connectionMessage.setFont(new javafx.scene.text.Font(Main.defFont, 20));
        javafx.scene.control.Label connectionMessage2 = new Label("Go to 'File -> Connection' to establish a connection to a server.");
        connectionMessage2.setFont(new Font(Main.defFont, 12));
        noConnection.getChildren().addAll(iE, connectionMessage, connectionMessage2);
        if (Connections.serverConnection()) {
            layoutWrap.getChildren().addAll(bar, layout);
        } else {
            layoutWrap.getChildren().addAll(bar, noConnection);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Scene sceneMain = new Scene(layoutWrap, 400, 500);
        sceneMain.getStylesheets().add("com/company/" + Main.style);
        Main.window.setScene(sceneMain);
        Main.window.show();
    }

    private void run() throws IOException {
        String line = input.readLine();
        String out = "";
        if (line.startsWith("#SUBMITNAME#")) {
            output.println(Data.loggedName);
        } else if (line.startsWith("#ACCEPTEDNAME#")) {
            textField.setEditable(true);
        } else if (line.startsWith("#MESSAGE#")) {
            System.out.println(line);
            messageArea.append(Data.loggedName + ": " + Data.decrypt(line.substring(9 + Data.loggedName.length() + 3), Main.shift) + "\n");
        }
        run();
    }

    private void chattingWindow() throws IOException {
        //Disables the user from being able to edit the chat output.
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.pack();
        //Start the window centered on the monitor.
        frame.setLocationRelativeTo(null);

        //When the enter key is pressed it sends the message and clears the message box.
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                output.println(Data.loggedName + ": " + Data.encrypt(textField.getText(), -Main.shift));
                textField.setText("");
            }
        });

        //The application exits if the frame gets closed.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        run();
    }

}
