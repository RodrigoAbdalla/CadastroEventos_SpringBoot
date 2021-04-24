package com.example.event.dto;

import com.example.event.entities.Attendee;

public class AttendeeInsertDTO {
    private String name;
    private String email;
    private Double balance;
    
    public AttendeeInsertDTO() {
	}

    public AttendeeInsertDTO(Long id, String name, String email, Double balance) {
        setName(name);
        setEmail(email);
        setBalance(balance);
	}

    public AttendeeInsertDTO(Attendee attendee) {
        setName(attendee.getName());
        setEmail(attendee.getEmail());
        setBalance(attendee.getBalance());
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
