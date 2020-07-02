package com.demo.daliy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.daliy.data.Mydb;
import com.demo.daliy.data2.Management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailFragment extends Fragment {
    List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
    int[] image_expense = new int[]{R.drawable.up, R.drawable.down }; //存储图片
    private Management mag[];
    public DetailFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detaillist, container, false);
//        String[] expense_category = new String[] {"工资", "eating"};
//        String[] expense_money = new String[] {"3000", "150"};


//        for(int i=0;i< image_expense.length;i++)
//        {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("image_expense", image_expense[i]);
//            map.put("expense_category", expense_category[i]);
//            map.put("expense_money", expense_money[i]);
//            listitem.add(map);
//        }
        getData();
        SimpleAdapter simpleAdapter=new SimpleAdapter(
                getActivity(),
                listitem,
                R.layout.detailfragment,
                new String[]{"expense_category", "expense_money", "image_expense"},
                new int[]{R.id.tv_category,R.id.tv_money,R.id.image});
        ListView listView = (ListView) v.findViewById(R.id.lv_expense);
        listView.setAdapter(simpleAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                    Intent intent=new Intent(getActivity(),updateInform.class);
                    intent.putExtra("user_ID", position);
                    System.out.println(position);
                    startActivity(intent);


            }
        });


        return v;
    }

    public void getData(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user",0);
        String uName=sharedPreferences.getString("uname","");

        if(uName.isEmpty()){
            new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("你还没有登录，请先登录").
                    setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(getActivity(),MainActivity.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            }).show();
        }else{
            Mydb db=new Mydb(getActivity());
            String TABLENAME="inform_db";
            if(db.isTableExits("inform_db")){
            Cursor c=db.find("select * from " + TABLENAME +"   where  "
                    +"name=?" , new String[]{uName+""});



                c.moveToFirst();
                String strtype="";
                int i=c.getColumnCount();
                int j=0;
                while(j<c.getCount()) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    strtype = c.getString(c.getColumnIndex("type"));

                    map.put("expense_category", c.getString(c.getColumnIndex("category")));
                    if (strtype.equals("收入")) {
                        map.put("image_expense", image_expense[0]);
                        map.put("expense_money", "+" + c.getString(c.getColumnIndex("money")));
                    }

                    if(strtype.equals("支出")){
                        map.put("image_expense", image_expense[1]);
                        map.put("expense_money", "-" + c.getString(c.getColumnIndex("money")));
                    }
                    c.moveToNext();
                    listitem.add(map);
                    j++;

                }
                c.close();
                db.close();
            }


            }

    }
}
