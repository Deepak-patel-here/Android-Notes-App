package com.deepak.notesapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class NoteTable {
    @ColumnInfo(name = "note_id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "note_title")
    private String title;

    @ColumnInfo(name = "note_description")
    private String description;

    @ColumnInfo(name = "note_subtitle")
    private String subTitle;

    //constructor

    public NoteTable(String title, String description,String subTitle) {
        this.title = title;
        this.description = description;
        this.subTitle=subTitle;
    }

    public NoteTable() {
    }

    //getter and setter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
