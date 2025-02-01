package com.deepak.notesapp;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.deepak.notesapp.databinding.ActivityAddNewTaskBinding;

public class addNewTask extends AppCompatActivity {
    NoteTable noteTable;
    MyViewModel viewModel;
    private NoteTable aleradyPresent;
    ImageView doneBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
        getWindow().setStatusBarColor(getResources().getColor(R.color.searchIcon));
        getWindow().getDecorView().setSystemUiVisibility(0);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.searchIcon));
        viewModel=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MyViewModel.class);
        ActivityAddNewTaskBinding newTaskBinding= DataBindingUtil.setContentView(this,R.layout.activity_add_new_task);
        noteTable=new NoteTable();
        newTaskBinding.setNotes(noteTable);
        doneBtn=newTaskBinding.doneBtn;
//        if(getIntent().getBooleanExtra("updated",false)){
//            aleradyPresent=(NoteTable) getIntent().getSerializableExtra("NOTES");
//            update_Notes(newTaskBinding);
//        }
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotesToDb(newTaskBinding);
            }
        });
        newTaskBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void update_Notes(ActivityAddNewTaskBinding newTaskBinding){
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTaskBinding.titleEdt.setText(aleradyPresent.getTitle());
                newTaskBinding.subtitle.setText(aleradyPresent.getSubTitle());
                newTaskBinding.content.setText(aleradyPresent.getDescription());
                String title=newTaskBinding.titleEdt.getText().toString();
                String noteDes=newTaskBinding.content.getText().toString();
                String subTitle=newTaskBinding.subtitle.getText().toString();
                if(!title.trim().isEmpty() && !noteDes.trim().isEmpty() && !subTitle.trim().isEmpty()){
                    noteTable.setTitle(title);
                    Log.v("check",title+noteDes+subTitle);
                    noteTable.setDescription(noteDes);
                    noteTable.setSubTitle(subTitle);
                    noteTable.setId(aleradyPresent.getId());
                    viewModel.updateNotes(noteTable);
                    finish();
                }
            }
        });
    }
    public void addNotesToDb(ActivityAddNewTaskBinding newTaskBinding){
        String title=newTaskBinding.titleEdt.getText().toString();
        String noteDes=newTaskBinding.content.getText().toString();
        String subTitle=newTaskBinding.subtitle.getText().toString();
        if(title.trim().isEmpty()){
            title="No Title";
        } else if (subTitle.trim().isEmpty()) {
            subTitle="No Sub-Title";
        }
        if(!title.trim().isEmpty() && !noteDes.trim().isEmpty() && !subTitle.trim().isEmpty()){
            noteTable.setTitle(title);
            Log.v("check",title+noteDes+subTitle);
            noteTable.setDescription(noteDes);
            noteTable.setSubTitle(subTitle);
            viewModel.addNewNotes(noteTable);
        }else{
            Toast.makeText(addNewTask.this, "please Fill the fields", Toast.LENGTH_SHORT).show();
        }
        hideKeyboard();
        finish();
    }
    public void hideKeyboard(){
        View view=this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm=(InputMethodManager) getSystemService(addNewTask.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}