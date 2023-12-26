/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec6_db;

/**
 *
 * @author NorhanNasr
 */
public class Student {
    
    private int id;
    private String name;
    private double cgpa;
    private String major;
    private String minor;
    public Student(int id, String name, double cgpa, String major,String minor) {
        this.id = id;
        this.name = name;
        this.cgpa = cgpa;
        this.major = major;
        this.minor = minor;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }
    
    
}
