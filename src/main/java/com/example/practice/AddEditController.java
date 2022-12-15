package com.example.practice;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEditController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button clearButon;

    @FXML
    private TextField descField;

    @FXML
    private TextField nameField;

    @FXML
    private Button saveButton;

    @FXML
    void clickClearButton(ActionEvent event) {
        nameField.clear();
        descField.clear();
    }

    @FXML
    void clickSaveButton(ActionEvent event) {
        String name = nameField.getText();
        String desc = descField.getText();

        ConnectDB connectDB = new ConnectDB();
        Connection con = connectDB.getConnection();
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO categories VALUES (DEFAULT, '" + name + "', '" + desc + "');");
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    void initialize() {


    }

}
