package com.example.event.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.event.dto.EventInsertDTO;

@Entity
@Table(name="TBL_EVENT")
public class Event implements Serializable{

    /*
    Dados: id, name, description, startDate, endDate, startTime, endTime, emailContact, amountFreeTickets, amountPayedTickets, 
    freeTickectsSelled, payedTickectsSelled, priceTicket.
    Ao criar um evento passar o id do usuário administrador no corpo da resquisição.
    Validar alteração da data do evento.
    */ 
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Long freeTicketsSelled;
    private Long payedTicketsSelled;
    private Float priceTicket;

    @ManyToOne
    @JoinColumn(name="ID_ADMIN")
    private Admin admin;           // OBRIGATÓRIO PASSAR ESSE ID, MANDAR ERRO SE NÃO FOR PASSADO

    @ManyToMany
    private List <Place> places = new ArrayList<>();           // Não é obrigatorio, e talvez nao tenha no insert, só no update

    @ManyToOne
    @JoinColumn(name ="ID_TICKET")              //JOÃO: Adicionei o ID do Ticket
    private Ticket ticket;

    public Event() {
    }

    public Event(EventInsertDTO insertDTO) {
        this.name = insertDTO.getName();
        this.description = insertDTO.getDescription();
        this.startDate = insertDTO.getStartDate();
        this.endDate = insertDTO.getEndDate();
        this.startTime = insertDTO.getStartTime();
        this.endTime = insertDTO.getEndTime();
        this.emailContact = insertDTO.getEmailContact();
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public Long getFreeTicketsSelled() {
        return freeTicketsSelled;
    }

    public void setFreeTicketsSelled(Long freeTickectsSelled) {
        this.freeTicketsSelled = freeTickectsSelled;
    }

    public Long getPayedTicketsSelled() {
        return payedTicketsSelled;
    }

    public void setPayedTicketsSelled(Long payedTickectsSelled) {
        this.payedTicketsSelled = payedTickectsSelled;
    }

    public Float getPriceTicket() {
        return priceTicket;
    }

    public void setPriceTicket(Float priceTicket) {
        this.priceTicket = priceTicket;
    }


    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void addPlace(Place place) {
        this.places.add(place);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Event other = (Event) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
