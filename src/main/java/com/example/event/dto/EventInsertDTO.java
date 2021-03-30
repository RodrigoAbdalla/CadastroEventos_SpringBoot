package com.example.event.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.event.entities.Event;

public class EventInsertDTO {
    private String name;
    private String description;
    private String place;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String emailContact;
    
    public EventInsertDTO() {
	}

    public EventInsertDTO(Long id, String name, String description, String place, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, String emailContact) {
        setName(name);
        setPlace(place);
        setDescription(description);
        setStartDate(startDate);
        setEndDate(endDate);
        setStartTime(startTime);
        setEndTime(endTime);
        setEmailContact(emailContact);
	}

    public EventInsertDTO(Event event) {
        setName(event.getName());
        setPlace(event.getPlace());
        setDescription(event.getDescription());
        setStartDate(event.getStartDate());
        setEndDate(event.getEndDate());
        setStartTime(event.getStartTime());
        setEndTime(event.getEndTime());
        setEmailContact(event.getEmailContact());
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    

}
