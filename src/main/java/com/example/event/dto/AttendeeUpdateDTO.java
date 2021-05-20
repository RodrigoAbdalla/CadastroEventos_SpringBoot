package com.example.event.dto;

import com.example.event.entities.Attendee;

public class AttendeeUpdateDTO {

    private String name;
    private String email;

    public AttendeeUpdateDTO() {
	}

    public AttendeeUpdateDTO(Long id, String name, String email) {
        setName(name);
        setEmail(email);
	}

    public AttendeeUpdateDTO(Attendee event) {
        setName(event.getName());
        setEmail(event.getEmail());
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