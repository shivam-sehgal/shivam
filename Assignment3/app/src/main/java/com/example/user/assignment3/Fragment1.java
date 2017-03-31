package com.example.user.assignment3;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class Fragment1 extends Fragment  {

  Button btn;
    public static RecyclerView RECYCLERVIEW;
     private RecyclerAdapter recyclerAdapter;
public static FragmentManager fragmentManager;
    private Data recievedData;
    public Fragment1() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



    }

  @Override
  public  void onStart (){
      super.onStart();
      Data data;

      if(((FirstPage)getActivity()).getData()!=null) {
          if(recyclerAdapter.getListSize()==((FirstPage)getActivity()).getPosition()){
          data = ((FirstPage) getActivity()).getData();
          int position=((FirstPage)getActivity()).getPosition();
          recyclerAdapter.addInList(position,data);
          recyclerAdapter.notifyDataSetChanged();}
          else{

              data = ((FirstPage) getActivity()).getData();
              int position=((FirstPage)getActivity()).getPosition();
              recyclerAdapter.updateInList(position,data);
              recyclerAdapter.notifyDataSetChanged();

          }


      }
      else{

          Toast.makeText(getContext(),"i m called",Toast.LENGTH_SHORT).show();
      }

  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragment1,container,false);
        btn=(Button)view.findViewById(R.id.add_info);
        RECYCLERVIEW=(RecyclerView)view.findViewById(R.id.recycler);
        //RecyclerAdapter.dataList.add(new Data("sge","edfdf","efefef"));
        recyclerAdapter=new RecyclerAdapter(getActivity());
        RECYCLERVIEW.setLayoutManager(new LinearLayoutManager(getContext()));
        RECYCLERVIEW.setAdapter(recyclerAdapter);
        fragmentManager=getFragmentManager();
        return view;

    }
    public static void startNewFragment(int position){
       Bundle bundle=new Bundle() ;
        bundle.putInt("position",position);
        EditFormF editfragment=new EditFormF();
        editfragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.bt1_fragment,editfragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFormFragment();
            }
        });

    }

public void startFormFragment(){
    FormFragment formFragment=new FormFragment();
    int pos=recyclerAdapter.getListSize();
    ((FirstPage)getActivity()).setPosition(pos);
    FragmentManager fragmentManager=getFragmentManager();
    android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.bt1_fragment,formFragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();




}


}
