package com.example.event.dto;

import com.example.event.entities.Admin;

public class AdminDTO {
    private String phoneNumber;


    public AdminDTO() {
	}

    public AdminDTO(String phoneNumber) {
        setPhoneNumber(phoneNumber);
	}

    public AdminDTO(Admin admin) {
        setPhoneNumber(admin.getPhoneNumber());
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
