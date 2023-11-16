package com.example.student_course_selection_system.Model;

import java.util.List;

public class Teacher {
    private String id;
    private String name;
    private String password;
    private String unApprovedCourse;
    public  Teacher(){}
    public Teacher(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUnApprovedCourse() {
        return unApprovedCourse;
    }

    public void setUnApprovedCourse(String unApprovedCourse) {
        this.unApprovedCourse = unApprovedCourse;
    }

    @Override
    public String toString() {
        return  "教师id：'" + id + '\'' +
                ", 姓名：'" + name + '\'' +
                ", 密码：'" + password + '\'';
    }
}
