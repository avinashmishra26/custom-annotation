/**
 * Created by avinash on 2/21/20.
 */

package com.learn.custom.annotation.model;

import com.learn.custom.annotation.Init;
import com.learn.custom.annotation.JsonElement;
import com.learn.custom.annotation.JsonRoot;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@JsonRoot
public class Employee {

    @JsonElement
    private int id;

    @JsonElement(name = "emp_name")
    private String name;

    private String address;

    @JsonElement
    private int dobYear;


    @JsonElement
    private int getAge(){
        Calendar calendar = Calendar.getInstance(Locale.US);
        calendar.setTime(new Date());
        return (calendar.get(Calendar.YEAR)- getDobYear());
    }

    @JsonElement
    private Date currentDate;

    public Employee(int id, String name, String address, int dob_year){
        this.id=id;
        this.name=name;
        this.address=address;
        this.dobYear=dob_year;
    }

    @Init
    private void initialize(){
        this.currentDate = new Date();
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getDobYear() {
        return dobYear;
    }

    public Date getCurrentDate() {
        return currentDate;
    }
}
