package com.example.event.dto;

import com.example.event.entities.Admin;

public class AdminUpdateDTO {

    private String phoneNumber;
    
    public AdminUpdateDTO() {
	}

    public AdminUpdateDTO(String phoneNumber) {
        setPhoneNumber(phoneNumber);
	}

    public AdminUpdateDTO(Admin admin) {
        setPhoneNumber(admin.getPhoneNumber());
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    

}
