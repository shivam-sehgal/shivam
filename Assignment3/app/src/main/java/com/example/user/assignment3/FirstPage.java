package com.example.user.assignment3;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstPage extends AppCompatActivity implements FormFragment.DataPassListener {
private Data data;
    private int position;
    private Button button2;
    private Button button1;
    private  Button2FirstFragment fragment2=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        startFirstFragment();
       button1=(Button)findViewById(R.id.button_1);
        button2=(Button)findViewById(R.id.button_2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              button1Pressed();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              button2Pressed();
            }
        });
    }

    public void startFirstFragment(){

        Fragment1 fragment1=new Fragment1();
        FragmentManager fragmentManager=getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.bt1_fragment,fragment1,"first fragment");


        fragmentTransaction.commit();

    }

    public void setData(Data data){

        this.data=data;
    }
    public Data getData(){

        return data;
    }

    public void setPosition(int position){

        this.position=position;
    }
    public int getPosition(){

        return position;
    }


    @Override
    public void passData(Data data) {
        this.data=data;

    }
    public void button2Pressed(){
        FragmentManager fragmentManager=getSupportFragmentManager();

        android.support.v4.app.Fragment currentFragment = fragmentManager.findFragmentById(R.id.bt1_fragment);




        if (fragment2 == null) {
            fragment2 = new Button2FirstFragment();

            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.bt1_fragment, fragment2,"Button 2 fragment");

            fragmentTransaction.commit();
        }

         else {
            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Button 2 fragment")).commit();
        }
        if(fragmentManager.findFragmentByTag("first fragment") != null){
            //if the other fragment is visible, hide it.
            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("first fragment")).commit();
        }

    }

    public void button1Pressed() {
        FragmentManager fragmentManager=getSupportFragmentManager();

        android.support.v4.app.Fragment currentFragment = fragmentManager.findFragmentById(R.id.bt1_fragment);
        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("first fragment")).commit();
        if(fragmentManager.findFragmentByTag("Button 2 fragment") != null){
            //if the other fragment is visible, hide it.
            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Button 2 fragment")).commit();
        }


    }}

