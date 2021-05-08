package com.example.event.dto;

import com.example.event.entities.Attendee;

public class AttendeeUpdateDTO {
    private Double balance;
    
    public AttendeeUpdateDTO() {
	}

    public AttendeeUpdateDTO(Double balance) {
        setBalance(balance);
	}

    public AttendeeUpdateDTO(Attendee attendee) {
        setBalance(attendee.getBalance());
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
    

}
