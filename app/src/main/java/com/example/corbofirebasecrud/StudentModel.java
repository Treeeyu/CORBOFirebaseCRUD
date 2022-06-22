package com.example.corbofirebasecrud;

public class StudentModel {
    String fname,lname,email,course;
    Double grade;


    public StudentModel(){}
    public StudentModel(String fname, String lname, String email, String course, Double grade) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.course = course;
        this.grade = grade;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }
}
