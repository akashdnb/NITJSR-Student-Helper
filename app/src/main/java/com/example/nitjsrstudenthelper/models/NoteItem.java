package com.example.nitjsrstudenthelper.models;

import java.util.List;

public class NoteItem {
    String title, branch, semester;
    private List<ChildNoteItem> childItemList;

    public NoteItem(){}

    public NoteItem(String title, String branch, String semester) {
        this.title = title;
        this.branch = branch;
        this.semester = semester;
    }

    public NoteItem(String title){
        this.title=title;
    }

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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
