package com.demo.daliy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



import com.demo.daliy.data2.Management;
import com.demo.daliy.data2.ManagementTable;

public class Informations extends AppCompatActivity {
    private EditText cate;
    private EditText mon;
    private EditText time;
    private EditText implement;
    private Button save;
    private Button back;
    private int type = 0;
    private String li;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertdata);
        cate=(EditText)findViewById(R.id.et_category);
        mon=(EditText)findViewById(R.id.et_money);
        time=(EditText)findViewById(R.id.et_date);
        implement=(EditText)findViewById(R.id.et_detail);
        save=(Button)findViewById(R.id.save);
        back=(Button)findViewById(R.id.backagain);
        final Intent in=getIntent();
        type=in.getIntExtra("strtype",0);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInto();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Informations.this,mainpage.class);
                startActivity(intent);
            }
        });
    }

    public void saveInto(){
        SharedPreferences sharedPreferences=getSharedPreferences("user",0);
        String uName=sharedPreferences.getString("uname","");

        if(uName.isEmpty()){
            new AlertDialog.Builder(this).setTitle("提示").setMessage("你还没有登录，请先登录").
                    setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(Informations.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            }).show();
        }
        if(type==0)
        {
            li="收入";
        }else{
            li="支出";
        }
        Management m=new Management();
        m.setName(uName);
        m.setType(li);
        m.setCategory(cate.getText().toString());
        m.setMoney(mon.getText().toString());
        m.setDate(time.getText().toString());
        m.setDetail(implement.getText().toString());
        ManagementTable mt=new ManagementTable(this);
        if(mt.addData(m)){
            Toast.makeText(Informations.this, "保存成功!!", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(Informations.this, mainpage.class);
            startActivity(intent);
        }else{
            Toast.makeText(Informations.this, "保存失败!!", Toast.LENGTH_SHORT).show();
        }

    }



}
