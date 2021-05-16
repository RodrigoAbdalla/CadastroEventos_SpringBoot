package com.example.event.dto;

import com.example.event.entities.Attendee;

public class AttendeeUpdateDTO {

    private String name;
    private String email;
    private Double balance;

    public AttendeeUpdateDTO() {
	}

    public AttendeeUpdateDTO(Long id, String name, String email, Double balance) {
        setName(name);
        setEmail(email);
        setBalance(balance);
	}

    public AttendeeUpdateDTO(Attendee event) {
        setName(event.getName());
        setEmail(event.getEmail());
        setBalance(event.getBalance());
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