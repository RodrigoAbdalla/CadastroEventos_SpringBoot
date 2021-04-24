package com.example.event.dto;

import com.example.event.entities.Admin;

public class AdminUpdateDTO {

    private String name;
    private String email;
    private String phoneNumber;
    
    public AdminUpdateDTO() {
	}

    public AdminUpdateDTO(Long id, String name, String email, String phoneNumber) {
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
	}

    public AdminUpdateDTO(Admin admin) {
        setName(admin.getName());
        setEmail(admin.getEmail());
        setPhoneNumber(admin.getPhoneNumber());
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    

}
