package com.demo.daliy.data2;

public class Management {
    public   final static String  NAME="name";
    public   final static String  TYPE="type";
    public   final static String  CATEGORY="category";
    public   final static String  MONEY="money";
    public   final static String  DATE="date";
    public   final static String  Detail="detail";

    private  String name; //用户名
    private  String type;//
    private  String category;//
    private  String money;//
    private  String date;//
    private  String detail;
    private  int id_DB=-1;//数据库主键id

    public void setId_DB(int id_DB) {
        this.id_DB = id_DB;
    }

    public int getId_DB() {
        return id_DB;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoney() {
        return money;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}
