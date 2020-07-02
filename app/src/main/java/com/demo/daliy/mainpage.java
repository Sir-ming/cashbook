package com.demo.daliy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class mainpage extends AppCompatActivity {
    private TextView detail;
    private TextView bing;
    private Button rem;
    private ViewPager vp;
    private DetailFragment df;
    private BingFragment bf;
    private AlertDialog alertDialog_add;
    List<Fragment> fragmentList=new ArrayList<Fragment>();
    myFragmentAdapt fragmentAdapt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainframe);
        initContent();
        fragmentAdapt=new myFragmentAdapt(this.getSupportFragmentManager(),fragmentList);
        vp.setOffscreenPageLimit(2);
        vp.setAdapter(fragmentAdapt);
        vp.setCurrentItem(0);
        detail.setTextColor(Color.parseColor("#1ba0e1"));

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    ChangeColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items={"收入","支出"};
                AlertDialog.Builder alert=new AlertDialog.Builder(mainpage.this);
                alert.setTitle("请输入类别:");
                alert.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(mainpage.this,Informations.class);
                        intent.putExtra("strtype",which);
                        startActivity(intent);
                        alertDialog_add.dismiss();
                    }
                });
                alertDialog_add=alert.create();
                alertDialog_add.show();
            }
        });

    }


    private void initContent(){
        detail=(TextView)findViewById(R.id.detail);
        bing=(TextView)findViewById(R.id.bing);
        vp=(ViewPager)findViewById(R.id.mvp);
        rem=(Button)findViewById(R.id.btnadd) ;
        df=new DetailFragment();
        bf=new BingFragment();
        fragmentList.add(df);
        fragmentList.add(bf);
    }

    public  class myFragmentAdapt extends FragmentPagerAdapter{
        List<Fragment> fragmentList=new ArrayList<Fragment>();
        public myFragmentAdapt(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList=fragmentList;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
    private void ChangeColor(int position){
        if (position == 0) {
            detail.setTextColor(Color.parseColor("#1ba0e1"));
            bing.setTextColor(Color.parseColor("#000000"));
        } else if (position == 1) {
            bing.setTextColor(Color.parseColor("#1ba0e1"));
            detail.setTextColor(Color.parseColor("#000000"));
        }
    }
}
