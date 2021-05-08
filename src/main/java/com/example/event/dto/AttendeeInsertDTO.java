package com.example.event.dto;

import com.example.event.entities.Attendee;

public class AttendeeInsertDTO {
    private Double balance;
    
    public AttendeeInsertDTO() {
	}

    public AttendeeInsertDTO(Double balance) {
        setBalance(balance);
	}

    public AttendeeInsertDTO(Attendee attendee) {
        setBalance(attendee.getBalance());
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
