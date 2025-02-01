package com.deepak.notesapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.deepak.notesapp.databinding.ActivityMainBinding;
import com.deepak.notesapp.databinding.ReadModeBinding;
import com.deepak.notesapp.databinding.RecycleListItemBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements clickHandler {
    public final int INSERT_CODE=1;
    public final int UPDATE_CODE=2;
    MyViewModel vM;
    NoteDatabase database;
    ArrayList<NoteTable> myList=new ArrayList<>();
    ActivityMainBinding binding;
    MyAdapter adapter;
    ArrayList<NoteTable> myArr=new ArrayList<>();
    private int clickPosition=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            getWindow().getDecorView().setSystemUiVisibility(0);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                getWindow().setNavigationBarDividerColor(getResources().getColor(R.color.secondaryText));
            }
        }
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        vM=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MyViewModel.class);
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, addNewTask.class);
                startActivityForResult(i,INSERT_CODE);
            }
        });
        RecyclerView recyclerView=binding.recyclerView;
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        database=NoteDatabase.getInstance(this);
        vM.getEveryNotes().observe(this, new Observer<List<NoteTable>>() {
            @Override
            public void onChanged(List<NoteTable> noteTables) {
                myArr.clear();
                for(NoteTable n:noteTables){
                    Log.v("first",n.getTitle());
                    myArr.add(n);
                }
                adapter.notifyDataSetChanged();
            }
        });
        adapter=new MyAdapter(vM,myArr,this,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCLick(NoteTable noteTable, int pos) {
        Dialog dialog=new Dialog(this);
        noteTable=myArr.get(pos);
        ReadModeBinding readModeBinding=DataBindingUtil.inflate(getLayoutInflater(),R.layout.read_mode,null,false);
        dialog.setContentView(readModeBinding.getRoot());
        readModeBinding.titletxt.setText(noteTable.getTitle());
        readModeBinding.subtitle.setText(noteTable.getSubTitle());
        readModeBinding.content.setText(noteTable.getDescription());
        readModeBinding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT// Height
        );
        dialog.show();


    }
}