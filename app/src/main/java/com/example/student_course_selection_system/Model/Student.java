package com.example.student_course_selection_system.Model;

import java.util.List;

public class Student {
    private String id;
    private String password;
    private String name;
    String chosenCourse;

    public Student(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChosenCourse() {
        return chosenCourse;
    }

    public void setChosenCourse(String chosenCourse) {
        this.chosenCourse = chosenCourse;
    }
    public Student(){}

    @Override
    public String toString() {
        return  "学生id：'" + id + '\'' +
                ", 密码：'" + password + '\'' +
                ", 姓名：'" + name + '\'' ;
    }
}
