package com.example.event.dto;

import com.example.event.entities.BaseUser;

public class BaseUserDTO {
    private Long id;
    private String name;
    private String email;


    public BaseUserDTO() {
	}

    public BaseUserDTO(Long id, String name, String email, String phoneNumber) {
        setId(id);
        setName(name);
        setEmail(email);
	}

    public BaseUserDTO(BaseUser base_user) {
        setId(base_user.getId());
        setName(base_user.getName());
        setEmail(base_user.getEmail());
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
