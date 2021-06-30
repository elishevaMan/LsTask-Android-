package com.example.lsapplication.model;

import java.io.Serializable;

public class EmployeeModel implements Serializable {
    String firstName, lastName, phone, address, startDate, role, image;
    Boolean IsChange=false;



    public EmployeeModel(String firstName, String lastName, String phone, String address, String startDate, String role, String image,Boolean isChange) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.startDate = startDate;
        this.image = image;
        this.phone = phone;
        this.role = role;
        this.IsChange= isChange;
    }

    public Boolean getChange() {
        return IsChange;
    }

    public void setChange(Boolean change) {
        IsChange = change;
    }

    public EmployeeModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
