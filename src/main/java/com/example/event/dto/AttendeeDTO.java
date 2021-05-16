package com.example.event.dto;

import com.example.event.entities.Attendee;

public class AttendeeDTO {
    private Long id;            //JOÃO: Vai ter esse ID aqui????
    private String name;
    private String email;
    private Double balance;


    public AttendeeDTO() {
	}

    public AttendeeDTO(Long id, String name, String email) {
        setId(id);
        setName(name);
        setEmail(email);
        setBalance(balance);
	}

    public AttendeeDTO(Attendee attendee) {
        setId(attendee.getId());
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
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

}