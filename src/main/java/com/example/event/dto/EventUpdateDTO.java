package com.example.event.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.event.entities.Event;

public class EventUpdateDTO {

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Double priceTicket;
    
    public EventUpdateDTO() {
	}

    public EventUpdateDTO(Long id, String name, String description, LocalDate startDate, 
                          LocalDate endDate, LocalTime startTime, LocalTime endTime,
                          Double priceTicket
                          ) {
        setName(name);
        setDescription(description);
        setStartDate(startDate);
        setEndDate(endDate);
        setStartTime(startTime);
        setEndTime(endTime);
        setPriceTicket(priceTicket);
	}

    public EventUpdateDTO(Event event) {
        setName(event.getName());
        setDescription(event.getDescription());
        setStartDate(event.getStartDate());
        setEndDate(event.getEndDate());
        setStartTime(event.getStartTime());
        setEndTime(event.getEndTime());
        setPriceTicket(event.getPriceTicket());
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

    public Double getPriceTicket() {
        return priceTicket;
    }

    public void setPriceTicket(Double priceTicket) {
        this.priceTicket = priceTicket;
    }


    

}
