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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.daliy.data.Mydb;

public class BingFragment extends Fragment {
    private TextView income;
    private TextView outcome;
    private TextView sum;
    private TextView tips;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bingfragment, container, false);
        income=(TextView)v.findViewById(R.id.incomesum);
        outcome=(TextView)v.findViewById(R.id.outcomesum);
        sum=(TextView)v.findViewById(R.id.sum);
        tips=(TextView)v.findViewById(R.id.tips);

        getIn();

        return v;
    }

    public void getIn(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user",0);
        String uName=sharedPreferences.getString("uname","");
        String utype="收入";
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
            String stype="";
            String money="";
            int x=0;
            int y=0;
            int a=0;
            int b=0;
            String xx="";
            String yy="";
            int i=c.getColumnCount();
            int j=0;
            while(j<c.getCount()) {
                stype =c.getString(c.getColumnIndex("type"));
                money=c.getString(c.getColumnIndex("money"));
                if (stype.equals("收入")) {
                    x=Integer.parseInt(money);
                    a=a+x;
                }

                if(stype.equals("支出")){
                    y=Integer.parseInt(money);
                    b=b+y;
                }
                c.moveToNext();
                j++;
            }
            c.close();
            db.close();
            xx=Integer.toString(a);
            yy=Integer.toString(b);
            income.setText("累计存入："+xx);
            outcome.setText("累计支出："+yy);

            int result=a-b;
            String result1=Integer.toString(result);
            sum.setText("存取盈利："+result1);

            if(result>0){
                tips.setText("评语：继续保持");
            }else{
                tips.setText("你已经破产了");
                tips.setTextSize(60);
            }

        }
        }
    }
}
