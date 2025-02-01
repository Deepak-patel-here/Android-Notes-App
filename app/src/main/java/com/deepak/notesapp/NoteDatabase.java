package com.deepak.notesapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NoteTable.class},version = 2)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDAO getNoteDao();
    //singleton pattern
    private static NoteDatabase noteDatabase;
    public static synchronized NoteDatabase getInstance(Context context){
        if(noteDatabase==null){
            noteDatabase= Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class,"Notes_db")
                    .fallbackToDestructiveMigration().build();
        }
        return noteDatabase;

    }
}
