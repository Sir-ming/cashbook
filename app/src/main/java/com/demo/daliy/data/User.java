package com.demo.daliy.data;

public class User {
    public   final static String  NAME="name";
    public   final static String  PWD="pwd";

    private  String name; //用户名
    private  String pwd;//
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

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }
}
