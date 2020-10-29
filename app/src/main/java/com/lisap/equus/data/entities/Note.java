package com.lisap.equus.data.entities;

import java.io.Serializable;

public class Note implements Serializable {
    private String noteId;
    private String date;
    private String message;

    public Note() {}

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return "Note{" +
                "noteId='" + noteId+ '\'' +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                '}';
}

    }
