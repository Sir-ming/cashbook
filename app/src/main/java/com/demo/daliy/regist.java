package com.demo.daliy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.demo.daliy.data.Mydb;
import com.demo.daliy.data.User;
import com.demo.daliy.data.utable;

public class regist extends AppCompatActivity {
    private EditText uname;
    private EditText pwd;
    private Button back;
    private Button reg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        uname=(EditText)findViewById(R.id.uname1);
        pwd=(EditText)findViewById(R.id.password1);
        reg=(Button)findViewById(R.id.reg1);
        back=(Button)findViewById(R.id.back1);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(regist.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void reg() {
        if (!uname.getText().toString().trim().equals("") && !pwd.getText().toString().trim().equals("")) {
            User u = new User();
            u.setName(uname.getText().toString());
            u.setPwd(pwd.getText().toString());
            utable ut = new utable(regist.this);
            Mydb db = new Mydb(regist.this);
            String sql = "select * from user_db where name=" + uname.getText().toString().trim();
            Cursor c = db.find(sql, null);
            if (c != null && c.getCount() >= 1) {
                Toast.makeText(regist.this, "用户名已存在!!", Toast.LENGTH_SHORT).show();
            } else {
                if (ut.addData(u)) {
                    Toast.makeText(regist.this, "注册成功!!", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(regist.this, mainpage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(regist.this, "注册失败!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        } else {
            Toast.makeText(regist.this, "请先输入数据!!", Toast.LENGTH_SHORT).show();
        }

    }
}