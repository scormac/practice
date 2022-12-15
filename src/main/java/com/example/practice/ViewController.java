package com.example.practice;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButtonCat;

    @FXML
    private Button addButtonTrans;

    @FXML
    private TableColumn<Transactions, String> catTrans;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TableColumn<Transactions, Double> costTrans;

    @FXML
    private Button deleteButtonCat;

    @FXML
    private TableColumn<Transactions, String> dateTrans;

    @FXML
    private TableColumn<Transactions, String> descTrans;

    @FXML
    private TableColumn<Categories, String> descriptionColumnCat;

    @FXML
    private Button refreshButtonTrans;

    @FXML
    private Button refreshButtonCat;

    @FXML
    private Button editButtonCat;

    @FXML
    private TableColumn<Categories, Integer> idColumnCat;

    @FXML
    private TableColumn<Transactions, Integer> idTrans;

    @FXML
    private TableColumn<Categories, String> nameColumnCat;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Categories> tableCat;

    @FXML
    private TableView<Transactions> tableTrans;

    @FXML
    private TableColumn<Transactions, String> typeTrans;

    @FXML
    void clickAddButtonCat(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("add-edit.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Реєстрація / Редагування доходів / витрат");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void clickDeleteButtonCat(ActionEvent event) {
        Categories row = tableCat.getSelectionModel().getSelectedItem();
        if (row != null){
            try {
                ConnectDB connection = new ConnectDB();
                Connection con = connection.getConnection();
                Statement stdel = con.createStatement();
                int id = row.getId();
                stdel.executeUpdate("DELETE FROM categories WHERE id = " + id + ";");
            } catch (SQLException e){
                System.out.println(e);
            }
        }
        tableCat.setItems(getDataCat());
    }

    @FXML
    void clickEditButtonCat(ActionEvent event) {

    }

    @FXML
    void initialize() {
        idColumnCat.setCellValueFactory(new PropertyValueFactory<Categories, Integer>("id"));
        nameColumnCat.setCellValueFactory(new PropertyValueFactory<Categories, String>("name"));
        descriptionColumnCat.setCellValueFactory(new PropertyValueFactory<Categories, String>("description"));

        tableCat.setItems(getDataCat());

        idTrans.setCellValueFactory(new PropertyValueFactory<Transactions, Integer>("id"));
        catTrans.setCellValueFactory(new PropertyValueFactory<Transactions, String>("catName"));
        typeTrans.setCellValueFactory(new PropertyValueFactory<Transactions, String>("type"));
        costTrans.setCellValueFactory(new PropertyValueFactory<Transactions, Double>("cost"));
        dateTrans.setCellValueFactory(new PropertyValueFactory<Transactions, String>("date"));
        descTrans.setCellValueFactory(new PropertyValueFactory<Transactions, String>("descriptionTrans"));

        tableTrans.setItems(getDataTrans());
        search();
        loadComboBox();
    }

    public ObservableList<Categories> getDataCat(){
        ObservableList<Categories> data = FXCollections.observableArrayList();

        try {
            ConnectDB connectDB = new ConnectDB();
            Connection con = connectDB.getConnection();
            Statement statement = con.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM categories;");

            while (result.next()) {
                data.add(new Categories(result.getInt("id"), result.getString("name"), result.getString("description")));
            }

            statement.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }

        return data;
    }

    public ObservableList<Transactions> getDataTrans(){
        ObservableList<Transactions> dataTrans = FXCollections.observableArrayList();

        try {
            ConnectDB connectDB = new ConnectDB();
            Connection con = connectDB.getConnection();
            Statement statement = con.createStatement();

            ResultSet result = statement.executeQuery("SELECT transactions.id, categories.name, type_op, cost, " +
                    "date, transactions.description \n" +
                    "FROM transactions\n" +
                    "JOIN categories\n" +
                    "ON categories.id = transactions.category;");

            while (result.next()) {
                dataTrans.add(new Transactions(result.getInt("transactions.id"),
                        result.getString("categories.name"), result.getString("type_op"),
                        result.getDouble("cost"), result.getString("date"),
                        result.getString("transactions.description")));
            }

            statement.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }

        return dataTrans;
    }

    @FXML
    void clickAddButtonTrans(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addTrans.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Реєстрація / Редагування доходів / витрат");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void clickRefreshTrans() {
        tableTrans.setItems(getDataTrans());
    }


    @FXML
    void clickRefreshCat() {
        tableCat.setItems(getDataCat());
        search();
    }

    ObservableList<Categories> dataList;
    void search(){
        dataList = getDataCat();
        FilteredList<Categories> catFilteredList = new FilteredList<>(dataList, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            catFilteredList.setPredicate(categories -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (categories.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Categories> sortedData = new SortedList<>(catFilteredList);
        sortedData.comparatorProperty().bind(tableCat.comparatorProperty());
        tableCat.setItems(sortedData);
    }

    @FXML
    void clickDeleteButtonTrans() {
        Transactions row = tableTrans.getSelectionModel().getSelectedItem();
        if (row != null){
            try {
                ConnectDB connection = new ConnectDB();
                Connection con = connection.getConnection();
                Statement stdel = con.createStatement();
                int id = row.getId();
                stdel.executeUpdate("DELETE FROM transactions WHERE id = " + id + ";");
            } catch (SQLException e){
                System.out.println(e);
            }
        }
        tableCat.setItems(getDataCat());
    }

    void loadComboBox(){
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

