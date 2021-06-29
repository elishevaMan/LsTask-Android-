package com.example.lsapplication.model;

import java.io.Serializable;

public class EmployeeModel implements Serializable {
    String firstName, lastName, phone, address, startDate, role, image;

    public EmployeeModel(String firstName, String lastName, String phone, String address, String startDate, String role, String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.startDate = startDate;
      //  image = image;
        this.phone = phone;
        this.role = role;
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
