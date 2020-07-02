package com.demo.daliy.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class utable {

    private   final static String TABLENAME="user_db";//表名
    private   Mydb   db;//数据库管理

    public    utable(Context context)
    {
        db=new  Mydb(context);
        if(!db.isTableExits(TABLENAME))
        {
            String   createTableSql="CREATE TABLE IF NOT EXISTS " +
                    TABLENAME + " (  id_DB  integer   " +
                    "primary key  AUTOINCREMENT , " +
                    User.NAME     + "  VARCHAR," +
                    User.PWD+ " VARCHAR)";
            //创建表
            db.creatTable(createTableSql);
        }
    }

    public  boolean  addData(User user)
    {

        ContentValues values = new ContentValues();
        values.put(User.NAME, user.getName());
        values.put(User.PWD, user.getPwd());
        return	db.save(TABLENAME, values);
    }


    public User getUserByID(int id)
    {
        Cursor cursor = null;
        try {
            cursor = db.find(
                    "select * from " + TABLENAME +"   where  "
                            +"id_DB=?" , new String[]{id+""});
            User temp = new User();
            cursor.moveToNext();
            temp.setId_DB(cursor.getInt(
                    cursor.getColumnIndex("id_DB")));
            temp.setName(cursor.getString(
                    cursor.getColumnIndex(User.NAME)));
            temp.setPwd(cursor.getString(
                    cursor.getColumnIndex(User.PWD)));


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

    /**
     * 修改联系人信息
     */
    public  boolean updateUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put(User.NAME, user.getName());
        values.put(User.PWD, user.getPwd());
        return db.update(TABLENAME,
                values, "  id_DB=? ", new String[]{user.getId_DB()+""});
    }

    public  boolean  deleteByUser(User user)
    {
        return db.delete(TABLENAME,
                "  id_DB=?", new String[]{user.getId_DB()+""});
    }
}
