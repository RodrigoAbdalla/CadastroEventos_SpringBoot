package com.example.event.entities;


import javax.persistence.Entity;

import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.example.event.dto.AttendeeInsertDTO;

@Entity
@Table(name="TBL_ATTENDEES") //Vai ter tabela para cada entidade???
@PrimaryKeyJoinColumn(name="ID_BASE_USER")
public class Attendee extends BaseUser{
    
    private Double balance;


    public Attendee() {
    }

    public Attendee(AttendeeInsertDTO insertDTO) {
        this.balance = insertDTO.getBalance();
    }
    
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
