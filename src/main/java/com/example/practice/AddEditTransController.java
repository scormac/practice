package com.example.practice;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddEditTransController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button clearButon;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField costField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField descField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField typeOpField;
    @FXML
    void clickClearButton(ActionEvent event) {
        comboBox.valueProperty().setValue(null);
        typeOpField.clear();
        costField.clear();
        descField.clear();
        datePicker.setValue(null);
    }

    @FXML
    void clickSaveButton(ActionEvent event) {
        String catName = comboBox.getValue();
        String typeOp = typeOpField.getText();
        String cost = costField.getText();
        String desc = descField.getText();
        String date = String.valueOf(datePicker.getValue());
        int id = 0;

        try {
            ConnectDB connectDB = new ConnectDB();
            Connection con = connectDB.getConnection();
            Statement stmn = con.createStatement();
            ResultSet res = stmn.executeQuery("SELECT * FROM categories WHERE name = '" + catName + "';");

            if (res.next()) {
                id = res.getInt("id");
            }
            stmn.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            ConnectDB connectDB = new ConnectDB();
            Connection con = connectDB.getConnection();
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO transactions VALUES (DEFAULT, " + id + ", '" + typeOp + "', " + cost + ", '" +
                    date + "', '" + desc + "');");
            statement.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void initialize() {

        try {
            ConnectDB connectDB = new ConnectDB();
            Connection con = connectDB.getConnection();
            Statement statement = con.createStatement();
            ObservableList<String> comboRes = FXCollections.observableArrayList();
            ResultSet res = statement.executeQuery("SELECT name FROM categories;");
            while (res.next()) {
                comboRes.add(res.getString("name"));
                comboBox.setItems(comboRes);
            }
        } catch (Exception e) {
            System.out.println(e);
        }



    }

}
