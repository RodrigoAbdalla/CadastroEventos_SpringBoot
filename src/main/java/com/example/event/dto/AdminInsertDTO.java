package com.example.event.dto;

import com.example.event.entities.Admin;

public class AdminInsertDTO {
    private String phoneNumber;
    
    public AdminInsertDTO() {
	}

    public AdminInsertDTO(String phoneNumber) {
        setPhoneNumber(phoneNumber);
	}

    public AdminInsertDTO(Admin admin) {
        setPhoneNumber(admin.getPhoneNumber());
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
