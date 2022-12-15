package com.example.practice;

public class Transactions {
    private int id;
    private String catName;
    private String type;
    private double cost;
    private String date;
    private String descriptionTrans;

    public Transactions(int id, String catName, String type, double cost, String date, String descriptionTrans) {
        this.id = id;
        this.catName = catName;
        this.type = type;
        this.cost = cost;
        this.date = date;
        this.descriptionTrans = descriptionTrans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescriptionTrans() {
        return descriptionTrans;
    }

    public void setDescriptionTrans(String descriptionTrans) {
        this.descriptionTrans = descriptionTrans;
    }
}
