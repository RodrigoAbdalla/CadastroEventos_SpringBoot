package com.example.event.dto;

import java.util.List;

import com.example.event.entities.Event;

public class EventTicketDTO {
    private Long amountFreeTickets;
    private Long amountPayedTickets;
    private Long amountPayedTicketsSold;
    private Long amountFreeTicketsSold;
    private List<TicketsDTO> listTicketsDTO;

    public EventTicketDTO(Long amountFreeTickets, Long amountPayedTickets, Long amountPayedTicketsSold, Long amountFreeTicketsSold, List<TicketsDTO> listTicketsDTO) {
        setAmountFreeTickets(amountFreeTickets);
        setAmountPayedTickets(amountPayedTickets);
        setAmountFreeTicketsSold(amountFreeTicketsSold);
        setAmountPayedTicketsSold(amountPayedTicketsSold);
        setListTicketsDTO(listTicketsDTO);
	}

    public EventTicketDTO(Event event, Long amountPayedTicketsSold, Long amountFreeTicketsSold, List<TicketsDTO> listTicketsDTO) {
        setAmountFreeTickets(event.getAmountFreeTickets());
        setAmountPayedTickets(event.getAmountPayedTickets());
        setAmountFreeTicketsSold(amountFreeTicketsSold);
        setAmountPayedTicketsSold(amountPayedTicketsSold);
        setListTicketsDTO(listTicketsDTO);
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
    public Long getAmountPayedTicketsSold() {
        return amountPayedTicketsSold;
    }
    public void setAmountPayedTicketsSold(Long amountPayedTicketsSold) {
        this.amountPayedTicketsSold = amountPayedTicketsSold;
    }
    public Long getAmountFreeTicketsSold() {
        return amountFreeTicketsSold;
    }
    public void setAmountFreeTicketsSold(Long amountFreeTicketsSold) {
        this.amountFreeTicketsSold = amountFreeTicketsSold;
    }

    public List<TicketsDTO> getListTicketsDTO() {
        return listTicketsDTO;
    }

    public void setListTicketsDTO(List<TicketsDTO> listTicketsDTO) {
        this.listTicketsDTO = listTicketsDTO;
    }

    
}
