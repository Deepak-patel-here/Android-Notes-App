package com.deepak.notesapp;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {
    NoteDAO noteDAO;
    ExecutorService executorService;
    Handler handler;

    //constuctor

    public NoteRepository(Application application) {
        NoteDatabase noteDatabase=NoteDatabase.getInstance(application);
        this.noteDAO= noteDatabase.getNoteDao();
        executorService= Executors.newSingleThreadExecutor();
        handler=new Handler(Looper.getMainLooper());
    }

    //CRUD

    public void addNotes(NoteTable noteTable){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                noteDAO.insert(noteTable);
            }
        });

    }
    public void updNotes(NoteTable noteTable){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                noteDAO.update(noteTable);
            }
        });
    }
    public void delNotes(NoteTable noteTable){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                noteDAO.delete(noteTable);
            }
        });
    }
    public LiveData<List<NoteTable>> getNotesAll(){
        return noteDAO.getAllNotes();
    };
}
