package com.example.event.dto;

import com.example.event.entities.Attendee;

public class AttendeeDTO {
    private Double balance;


    public AttendeeDTO() {
	}

    public AttendeeDTO(Double balance) {
        setBalance(balance);
	}

    public AttendeeDTO(Attendee attendee) {
        setBalance(attendee.getBalance());
    }

   
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
