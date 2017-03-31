package com.example.user.assignment3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 3/30/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public  ArrayList<Data> dataList=new ArrayList<Data>();
    private Context ctx;
    private FragmentManager fragmentManager;
    public RecyclerAdapter(Context ctx){
       this.ctx=ctx;

    }
    public Data getListElement(int position){
        if(dataList.size()!=0){
            return dataList.get(position);
        }
        return null;
    }

    public void updateInList(int position,Data data){
        dataList.set(position,data);

    }
    public int getListSize(){
        return dataList.size();
    }

   public void addInList(int position,Data data){
       dataList.add(position,data);
   }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(ctx);
        View view=layoutInflater.inflate(R.layout.row,parent,false);
        ViewHolder holder=new ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imageView.setImageBitmap(dataList.get(position).getImageID());
     holder.first.setText(dataList.get(position).getFirstName());
        holder.last.setText(dataList.get(position).getLastName());
        holder.dob.setText(dataList.get(position).getDateofBirth());
        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment1.startNewFragment(position);

            }
        });



    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView first;
        TextView last;
        TextView dob;
        Button Edit;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.user_img);
            first=(TextView)itemView.findViewById(R.id.first_name);
            last=(TextView)itemView.findViewById(R.id.last_name);
            dob=(TextView)itemView.findViewById(R.id.date_birth);
            Edit=(Button)itemView.findViewById(R.id.edit_btn);

        }
    }
}
