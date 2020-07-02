package com.demo.daliy;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.daliy.data2.Management;
import com.demo.daliy.data2.ManagementTable;


public class updateInform extends AppCompatActivity {
    private EditText type;
    private EditText category;
    private EditText money;
    private EditText date;
    private EditText detail;
    private Button  update;
    private Button  back;
    private  Management m;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        type=(EditText)findViewById(R.id.et_type2);
        category=(EditText)findViewById(R.id.et_category2);
        money=(EditText)findViewById(R.id.et_money2);
        date=(EditText)findViewById(R.id.et_date2);
        detail=(EditText)findViewById(R.id.et_detail2);
        update=(Button)findViewById(R.id.update);
        back=(Button)findViewById(R.id.backagain2);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              change();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(updateInform.this,mainpage.class);
                startActivity(intent);
            }
        });
        showdata();
    }
    public void showdata(){
        Bundle localBundle = getIntent().getExtras();
        int id=localBundle.getInt("user_ID");
        ManagementTable mt=new ManagementTable(this);
        m=mt.getUserByID(id+1);
        type.setText(m.getType());
        category.setText(m.getCategory());
        money.setText(m.getMoney());
        date.setText(m.getDate());
        detail.setText(m.getDetail());
    }
    public void change(){
        SharedPreferences sharedPreferences=getSharedPreferences("user",0);
        String uName=sharedPreferences.getString("uname","");
        m.setName(uName);
        m.setType(type.getText().toString());
        m.setCategory(category.getText().toString());
        m.setMoney(money.getText().toString());
        m.setDate(date.getText().toString());
        m.setDetail(detail.getText().toString());
        ManagementTable mt=new ManagementTable(this);
        if(mt.updateUser(m)){
            Toast.makeText(updateInform.this,"修改成功",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(updateInform.this,mainpage.class);
            startActivity(intent);
        }else{
            Toast.makeText(updateInform.this,"修改失败",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "删除");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case 1://删除
                    delete();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public  void delete()
    {
        Bundle localBundle = getIntent().getExtras();
        final int id=localBundle.getInt("user_ID");
        final ManagementTable managementTable=new ManagementTable(this);
        new AlertDialog.Builder(updateInform.this).setTitle("提示").setMessage("确定要删除吗").
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(managementTable.deleteByUser(id+1)){
                            Toast.makeText(updateInform.this,"删除成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(updateInform.this,mainpage.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(updateInform.this,"删除失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        }).show();
    }
}
