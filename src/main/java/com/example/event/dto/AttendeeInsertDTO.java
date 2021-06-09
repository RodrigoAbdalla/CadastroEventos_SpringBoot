package com.example.event.dto;

import com.example.event.entities.Attendee;

public class AttendeeInsertDTO {
    private String name;
    private String email;

    public AttendeeInsertDTO() {
	}

    public AttendeeInsertDTO(Long id, String name, String email) {
        setName(name);
        setEmail(email);
	}

    public AttendeeInsertDTO(Attendee attendee) {
        setName(attendee.getName());
        setEmail(attendee.getEmail());
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