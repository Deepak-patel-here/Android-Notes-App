package com.deepak.notesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.deepak.notesapp.databinding.RecycleListItemBinding;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<NoteTable> allNotes;
    MyViewModel vM;
    Context context;
    clickHandler myclick;
    //constructor
    public MyAdapter(MyViewModel vM, ArrayList<NoteTable> allNotes,Context context,clickHandler myclick) {
        this.vM = vM;
        this.allNotes = allNotes;
        this.context=context;
        this.myclick=myclick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleListItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.recycle_list_item,parent,false);
        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NoteTable noteTable=allNotes.get(position);
        holder.binding.textTitle.setText(noteTable.getTitle());
        holder.binding.content.setText(noteTable.getDescription());
        holder.binding.txtSubtitle.setText(noteTable.getSubTitle());
        holder.binding.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vM.delNewNotes(noteTable);
                Toast.makeText(context, "Notes Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        holder.binding.recycleLinaeraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myclick.onCLick(allNotes.get(position),position);
            }
        });
//        holder.binding.openBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if(!allNotes.isEmpty())return allNotes.size();
        else return 0;
    }

    public void setAllNotes(ArrayList<NoteTable> allNotes) {
        this.allNotes = allNotes;
        notifyDataSetChanged();
    }

    //viewHolder class
    public  static class MyViewHolder extends RecyclerView.ViewHolder{
        RecycleListItemBinding binding;

        public MyViewHolder(@NonNull  RecycleListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
