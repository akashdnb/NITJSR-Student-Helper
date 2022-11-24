package com.example.nitjsrstudenthelper.models;

import java.util.List;

public class NoteItem {
    String title;
    private List<ChildNoteItem> childItemList;

    public NoteItem(String title, List<ChildNoteItem> childItemList) {
        this.title = title;
        this.childItemList = childItemList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChildNoteItem> getChildItemList() {
        return childItemList;
    }

    public void setChildItemList(List<ChildNoteItem> childItemList) {
        this.childItemList = childItemList;
    }
}
