package com.deepak.notesapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {
    @Insert
    public void insert(NoteTable noteTable);

    @Update
    public void update(NoteTable noteTable);

    @Delete
    public void delete(NoteTable noteTable);

    @Query("SELECT * FROM notes_table")
    public LiveData<List<NoteTable>> getAllNotes();
}
