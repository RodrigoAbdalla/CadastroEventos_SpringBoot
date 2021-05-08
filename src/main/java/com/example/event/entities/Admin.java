package com.example.event.entities;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.example.event.dto.AdminInsertDTO;

@Entity
@Table(name="TBL_ADMINS") //Vai ter tabela para cada entidade???
@PrimaryKeyJoinColumn(name="ID_BASE_USER")
public class Admin extends BaseUser{

    private String phoneNumber;

    @OneToMany(mappedBy = "admin")
    private List <Event> events = new ArrayList<>(); 


    public Admin() {
    }

    public Admin(AdminInsertDTO insertDTO) {
        this.phoneNumber = insertDTO.getPhoneNumber();
    }
   
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

}
