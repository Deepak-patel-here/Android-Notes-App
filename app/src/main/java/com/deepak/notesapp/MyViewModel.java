package com.deepak.notesapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MyViewModel extends AndroidViewModel {
    NoteRepository repository;
    LiveData<List<NoteTable>> allNotes;

    //constructor

    public MyViewModel(@NonNull Application application) {
        super(application);
        this.repository = new NoteRepository(application);
    }

    //select
    public LiveData<List<NoteTable>> getEveryNotes(){
        allNotes= repository.getNotesAll();
        return allNotes;
    }

    public void addNewNotes(NoteTable noteTable){repository.addNotes(noteTable);}
    public void delNewNotes(NoteTable noteTable){repository.delNotes(noteTable);}
    public void updateNotes(NoteTable noteTable){repository.updNotes(noteTable);}
}
