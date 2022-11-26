package com.example.nitjsrstudenthelper.models;

public class FacultyModel {
    String name, department, uri;

    public FacultyModel(){}

    public FacultyModel(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public FacultyModel(String name, String department, String uri) {
        this.name = name;
        this.department = department;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
