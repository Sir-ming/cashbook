package com.demo.daliy.data2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.demo.daliy.data.Mydb;

public class ManagementTable {
    private   final static String TABLENAME="inform_db";//表名
    private Mydb db;//数据库管理
    public    ManagementTable(Context context)
    {
        db=new  Mydb(context);
        if(!db.isTableExits(TABLENAME))
        {
            String   createTableSql="CREATE TABLE IF NOT EXISTS " +
                    TABLENAME + " (  id_DB  integer   " +
                    "primary key  AUTOINCREMENT , " +
                    Management.NAME     + "  VARCHAR," +
                    Management.TYPE     + "  VARCHAR," +
                    Management.CATEGORY     + "  VARCHAR," +
                    Management.MONEY     + "  VARCHAR," +
                    Management.DATE     + "  VARCHAR," +
                    Management.Detail+ " VARCHAR)";
            //创建表
            db.creatTable(createTableSql);
        }
    }

    public  boolean  addData(Management mag)
    {

        ContentValues values = new ContentValues();
        values.put(Management.NAME, mag.getName());
        values.put(Management.TYPE, mag.getType());
        values.put(Management.CATEGORY, mag.getCategory());
        values.put(Management.MONEY, mag.getMoney());
        values.put(Management.DATE, mag.getDate());
        values.put(Management.Detail, mag.getDetail());
        return	db.save(TABLENAME, values);
    }

    public Management getUserByID(int id)
    {
        Cursor cursor = null;
        try {
            cursor = db.find(
                    "select * from " + TABLENAME +"   where  "
                            +"id_DB=?" , new String[]{id+""});
            Management temp = new Management();
            cursor.moveToNext();
            temp.setId_DB(cursor.getInt(
                    cursor.getColumnIndex("id_DB")));
            temp.setName(cursor.getString(
                    cursor.getColumnIndex(Management.NAME)));
            temp.setType(cursor.getString(
                    cursor.getColumnIndex(Management.TYPE)));
            temp.setCategory(cursor.getString(
                    cursor.getColumnIndex(Management.CATEGORY)));
            temp.setMoney(cursor.getString(
                    cursor.getColumnIndex(Management.MONEY)));
            temp.setDate(cursor.getString(
                    cursor.getColumnIndex(Management.DATE)));
            temp.setDetail(cursor.getString(
                    cursor.getColumnIndex(Management.Detail)));

            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.closeConnection();
        }
        return null;
    }

    public  boolean updateUser(Management mag)
    {
        ContentValues values = new ContentValues();
        values.put(Management.NAME, mag.getName());
        values.put(Management.TYPE, mag.getType());
        values.put(Management.CATEGORY, mag.getCategory());
        values.put(Management.MONEY, mag.getMoney());
        values.put(Management.DATE, mag.getDate());
        values.put(Management.Detail, mag.getDetail());

        return db.update(TABLENAME,
                values, "  id_DB=? ", new String[]{mag.getId_DB()+""});
    }

    public  boolean  deleteByUser(int id)
    {
        return db.delete(TABLENAME,
                "  id_DB=?", new String[]{id+""});
    }


}
