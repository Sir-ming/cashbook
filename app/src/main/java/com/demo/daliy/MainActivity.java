package com.demo.daliy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.daliy.data.Mydb;

public class MainActivity extends AppCompatActivity {
    private EditText uname;
    private EditText pwd;
    private Button login;
    private Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uname = (EditText) findViewById(R.id.uname);
        pwd = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        reg = (Button) findViewById(R.id.reg);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check();
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, regist.class);
                startActivity(intent);
            }
        });
    }

    public void check() {
        Mydb db = new Mydb(MainActivity.this);
        String sql = "select * from user_db where name=?";
        Cursor c = db.find(sql, new String[]{uname.getText().toString().trim()});
        if (c == null) {
            Toast.makeText(MainActivity.this, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
        } else {
            c.moveToNext();
            if (pwd.getText().toString().trim().equals(c.getString(c.getColumnIndex("pwd")))) {
                //存储用户名数据用于后面使用
                SharedPreferences user = getSharedPreferences("user", 0);
                SharedPreferences.Editor editor = user.edit();
                editor.putString("uname", uname.getText().toString());
                editor.commit();
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, mainpage.class);
                startActivity(intent);
            }
        }
    }
}
