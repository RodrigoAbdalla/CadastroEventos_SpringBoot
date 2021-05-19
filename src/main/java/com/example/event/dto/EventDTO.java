package com.example.event.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.event.entities.Admin;
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
    private Double priceTicket;
    private AdminDTO admin;           // OBRIGATÓRIO PASSAR ESSE ID, MANDAR ERRO SE NÃO FOR PASSADO
    
    
    public EventDTO() {
	}

    public EventDTO(Long id, String name, String description, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, String emailContact,
    Long amountFreeTickets, Long amountPayedTickets, Double priceTicket, Admin admin) {
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
        setPriceTicket(priceTicket);
        setAdmin(new AdminDTO(admin));
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
        setPriceTicket(event.getPriceTicket());
        setAdmin(new AdminDTO(event.getAdmin()));
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


    public Double getPriceTicket() {
        return priceTicket;
    }

    public void setPriceTicket(Double priceTicket) {
        this.priceTicket = priceTicket;
    }


    public AdminDTO getAdmin() {
        return admin;
    }

    public void setAdmin(AdminDTO admin) {
        this.admin = admin;
    }

}
