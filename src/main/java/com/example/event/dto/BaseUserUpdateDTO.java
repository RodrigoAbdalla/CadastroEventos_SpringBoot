package com.example.event.dto;

import com.example.event.entities.BaseUser;

public class BaseUserUpdateDTO {

    private String name;
    private String email;
    
    public BaseUserUpdateDTO() {
	}

    public BaseUserUpdateDTO(Long id, String name, String email, String phoneNumber) {
        setName(name);
        setEmail(email);
	}

    public BaseUserUpdateDTO(BaseUser base_user) {
        setName(base_user.getName());
        setEmail(base_user.getEmail());
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

}
