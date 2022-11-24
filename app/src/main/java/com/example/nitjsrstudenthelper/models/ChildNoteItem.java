package com.example.nitjsrstudenthelper.models;

import android.net.Uri;

public class ChildNoteItem {
    String title, fileName, size, id;
    Uri uri;

    public ChildNoteItem(String title, String fileName, String size, String id, Uri uri) {
        this.title = title;
        this.fileName = fileName;
        this.size = size;
        this.id = id;
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
