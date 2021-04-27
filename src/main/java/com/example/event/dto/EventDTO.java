package com.example.event.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.event.entities.Event;

public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String emailContact;
    private Long amountFreeTickets;
    private Long amountPayedTickets;
    private Long freeTickectsSelled;        // pensar em começar com 0
    private Long payedTickectsSelled;
    private Float priceTicket;
    private Long idAdmin;           // OBRIGATÓRIO PASSAR ESSE ID, MANDAR ERRO SE NÃO FOR PASSADO
    private Long idPlace;
    
    
    public EventDTO() {
	}

    public EventDTO(Long id, String name, String description, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, String emailContact,
    Long amountFreeTickets, Long amountPayedTickets, Long freeTickectsSelled, Long payedTickectsSelled, Float priceTicket, Long idAdmin, Long idPlace) {
        setId(id);
        setName(name);
        setDescription(description);
        setStartDate(startDate);
        setEndDate(endDate);
        setStartTime(startTime);
        setEndTime(endTime);
        setEmailContact(emailContact);
        setAmountFreeTickets(amountFreeTickets);
        setAmountPayedTickets(amountPayedTickets);
        setFreeTickectsSelled(freeTickectsSelled);
        setPayedTickectsSelled(payedTickectsSelled);
        setPriceTicket(priceTicket);
        setIdAdmin(idAdmin);
        setIdPlace(idPlace);
	}

    public EventDTO(Event event) {
        setId(event.getId());
        setName(event.getName());
        setDescription(event.getDescription());
        setStartDate(event.getStartDate());
        setEndDate(event.getEndDate());
        setStartTime(event.getStartTime());
        setEndTime(event.getEndTime());
        setEmailContact(event.getEmailContact());
        setAmountFreeTickets(event.getAmountFreeTickets());
        setAmountPayedTickets(event.getAmountPayedTickets());
        setFreeTickectsSelled(event.getFreeTickectsSelled());
        setPayedTickectsSelled(event.getPayedTickectsSelled());
        setPriceTicket(event.getPriceTicket());
        setIdAdmin(event.getIdAdmin());
        setIdPlace(event.getIdPlace());
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public Long getAmountFreeTickets() {
        return amountFreeTickets;
    }

    public void setAmountFreeTickets(Long amountFreeTickets) {
        this.amountFreeTickets = amountFreeTickets;
    }

    public Long getAmountPayedTickets() {
        return amountPayedTickets;
    }

    public void setAmountPayedTickets(Long amountPayedTickets) {
        this.amountPayedTickets = amountPayedTickets;
    }

    public Long getFreeTickectsSelled() {
        return freeTickectsSelled;
    }

    public void setFreeTickectsSelled(Long freeTickectsSelled) {
        this.freeTickectsSelled = freeTickectsSelled;
    }

    public Long getPayedTickectsSelled() {
        return payedTickectsSelled;
    }

    public void setPayedTickectsSelled(Long payedTickectsSelled) {
        this.payedTickectsSelled = payedTickectsSelled;
    }

    public Float getPriceTicket() {
        return priceTicket;
    }

    public void setPriceTicket(Float priceTicket) {
        this.priceTicket = priceTicket;
    }

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Long getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(Long idPlace) {
        this.idPlace = idPlace;
    }
    

}
