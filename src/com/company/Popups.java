package com.company;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


class Popups {

    private static String style = "StyleSheet.css";

    static void windowSettings() {

        Stage settingsStage = new Stage();
        settingsStage.initModality(Modality.APPLICATION_MODAL);
        settingsStage.setTitle("Settings");
        settingsStage.getIcons().add(new Image("file:Images/settings.png"));
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setAlignment(Pos.TOP_LEFT);

        CheckBox optionTimes = new CheckBox("Display message times");
        optionTimes.setSelected(Data.messageTimes);
        CheckBox optionNotifications = new CheckBox("Enable desktop notifications");
        optionNotifications.setSelected(Data.notifications);
        CheckBox optionFlashing = new CheckBox("Enable taskbar flashing");
        optionFlashing.setSelected(Data.flashing);
        CheckBox optionBRMode = new CheckBox("Enable battle royale mode");
        optionBRMode.setSelected(Data.BRMode);


        Button buttonApply = new Button("Apply Settings");
        Label isApplied = new Label("");
        buttonApply.setOnAction(e -> {
            Data.messageTimes = optionTimes.isSelected();
            Data.notifications = optionNotifications.isSelected();
            Data.flashing = optionFlashing.isSelected();
            Data.BRMode = optionBRMode.isSelected();
            isApplied.setText("✓");
            settingsStage.close();
        });

        layout.getChildren().addAll(optionTimes, optionNotifications, optionFlashing, optionBRMode, buttonApply, isApplied);
        Scene scene = new Scene(layout, 300, 400);
        scene.getStylesheets().add("com/company/" + style);
        settingsStage.setScene(scene);
        settingsStage.showAndWait();
    }

    static void windowAccount() {
        Stage accountStage = new Stage();
        accountStage.initModality(Modality.APPLICATION_MODAL);
        accountStage.setTitle("Account");
        VBox layout = new VBox();
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setSpacing(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        HBox usernameCombo = new HBox();
        usernameCombo.setSpacing(10);
        Label usernameLabel = new Label("Change Username:");
        TextField usernameInput = new TextField();
        usernameInput.setPromptText("New Username");
        Button usernameSelect = new Button("✓");
        usernameCombo.getChildren().addAll(usernameInput, usernameSelect);

        HBox passwordCombo = new HBox();
        passwordCombo.setSpacing(10);
        Label passwordLabel = new Label("Change Password:");
        TextField newPassword = new TextField();
        newPassword.setPromptText("New Password");
        Button passwordSelect = new Button("✓");
        passwordCombo.getChildren().addAll(newPassword, passwordSelect);

        layout.getChildren().addAll(usernameLabel, usernameCombo, passwordLabel, passwordCombo);
        Scene scene = new Scene(layout, 300, 400);
        scene.getStylesheets().add("com/company/" + style);
        accountStage.setScene(scene);
        accountStage.showAndWait();
    }

    static void windowAbout() {
        Stage aboutStage = new Stage();
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.setTitle("About");
        aboutStage.getIcons().add(new Image("file:Images/aboutIcon.png"));
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);
        layout.setPadding(new Insets(10, 10, 10, 10));

        Label versionLabel = new Label("Version:\n" + Main.version);
        Label nameLabel = new Label("Made by Arek Zajac");

        layout.getChildren().addAll(versionLabel, nameLabel);
        Scene scene = new Scene(layout, 400, 250);
        scene.getStylesheets().add("com/company/StyleSheet.css");
        aboutStage.setScene(scene);
        aboutStage.showAndWait();
    }

}
