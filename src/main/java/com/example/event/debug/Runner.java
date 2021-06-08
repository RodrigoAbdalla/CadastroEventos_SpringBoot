package com.example.event.debug;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import com.example.event.entities.Admin;
import com.example.event.entities.Attendee;
import com.example.event.entities.Event;
import com.example.event.entities.Place;
import com.example.event.entities.Ticket;
import com.example.event.entities.TicketType;
import com.example.event.repositories.AdminRepository;
import com.example.event.repositories.AttendeeRepository;
import com.example.event.repositories.EventRepository;
import com.example.event.repositories.PlaceRepository;
import com.example.event.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class Runner implements CommandLineRunner{

    @Autowired
    private AdminRepository adminRepository;
    
  
    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private EventRepository eventRepository;
  
    @Autowired
    private PlaceRepository placeRepository;
    
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        
        System.out.println("*************** Inicio do Runner! ************");

        createAdmins();
        createAttendees();
        createPlaces();
        createEvents();
        linkPlacesAndEvent();
        createTickets();
    
        
        System.out.println("*************** Fim do Runner! ************");

    }







    private void createTickets() {
        Ticket t1 = new Ticket();
        t1.setDate(Instant.now());

        // Tira 1 na quantidade de tickets free disponiveis 
        Event e1 = new Event();
        e1 = eventRepository.findById(1L).get();
        e1.setAmountFreeTickets(e1.getAmountFreeTickets() - 1);

        t1.setEvent(eventRepository.findById(1L).get());
        t1.setAttendee(attendeeRepository.findById(4L).get());
        t1.setPrice(0.0);
        t1.setType(TicketType.FREE);
        ticketRepository.save(t1);
        eventRepository.save(e1);
        
        Ticket t2 = new Ticket();
        t2.setDate(Instant.now());
        t2.setEvent(eventRepository.findById(1L).get());
        t2.setAttendee(attendeeRepository.findById(5L).get());
        t2.setPrice(100.0);
        t2.setType(TicketType.PAYED);
        ticketRepository.save(t2);


        Ticket t3 = new Ticket();
        t3.setDate(Instant.now());
        t3.setEvent(eventRepository.findById(1L).get());
        t3.setAttendee(attendeeRepository.findById(6L).get());
        t3.setPrice(100.0);
        t3.setType(TicketType.PAYED);
        ticketRepository.save(t3);


        Ticket t4 = new Ticket();
        t4.setDate(Instant.now());
        t4.setEvent(eventRepository.findById(2L).get());
        t4.setAttendee(attendeeRepository.findById(4L).get());
        t4.setPrice(0.0);
        t4.setType(TicketType.FREE);
        ticketRepository.save(t4);


        Ticket t5 = new Ticket();
        t5.setDate(Instant.now());
        t5.setEvent(eventRepository.findById(2L).get());
        t5.setAttendee(attendeeRepository.findById(5L).get());
        t5.setPrice(50.0);
        t5.setType(TicketType.PAYED);
        ticketRepository.save(t5);


        Ticket t6 = new Ticket();
        t6.setDate(Instant.now());
        t6.setEvent(eventRepository.findById(2L).get());
        t6.setAttendee(attendeeRepository.findById(6L).get());
        t6.setPrice(50.0);
        t6.setType(TicketType.PAYED);
        ticketRepository.save(t6);


        Ticket t7 = new Ticket();
        t7.setDate(Instant.now());
        t7.setEvent(eventRepository.findById(3L).get());
        t7.setAttendee(attendeeRepository.findById(4L).get());
        t7.setPrice(0.0);
        t7.setType(TicketType.FREE);
        ticketRepository.save(t7);


        Ticket t8 = new Ticket();
        t8.setDate(Instant.now());
        t8.setEvent(eventRepository.findById(3L).get());
        t8.setAttendee(attendeeRepository.findById(5L).get());
        t8.setPrice(1000.0);
        t8.setType(TicketType.PAYED);
        ticketRepository.save(t8);


        Ticket t9 = new Ticket();
        t9.setDate(Instant.now());
        t9.setEvent(eventRepository.findById(3L).get());
        t9.setAttendee(attendeeRepository.findById(6L).get());
        t9.setPrice(1000.0);
        t9.setType(TicketType.PAYED);
        ticketRepository.save(t9);
    }







    private void linkPlacesAndEvent() {
        Event event = new Event();
        event = eventRepository.findById(1L).get();
        event.addPlace(placeRepository.findById(2L).get());
        eventRepository.save(event);

        event = eventRepository.findById(1L).get();
        event.addPlace(placeRepository.findById(1L).get());
        eventRepository.save(event);
        
        event = eventRepository.findById(2L).get();
        event.addPlace(placeRepository.findById(1L).get());
        eventRepository.save(event);

        event = eventRepository.findById(3L).get();
        event.addPlace(placeRepository.findById(2L).get());
        eventRepository.save(event);

        event = eventRepository.findById(4L).get();
        event.addPlace(placeRepository.findById(1L).get());
        eventRepository.save(event);

        event = eventRepository.findById(4L).get();
        event.addPlace(placeRepository.findById(2L).get());
        eventRepository.save(event);
    }




    private void createPlaces() {
        Place p1 = new Place();
        p1.setAdress("Praça Roberto Gomes Pedrosa, 1 - Morumbi, São Paulo - SP");
        p1.setName("Morumbi");

        Place p2 = new Place();
        p2.setAdress("Av. Dr. Américo Figueiredo, 76 - Jardim Simus, Sorocaba - SP");
        p2.setName("Tammy Pastelaria");
        
        Place p3 = new Place();
        p3.setAdress("Av. Miguel Ignácio Curi, 111 - Artur Alvim, São Paulo");
        p3.setName("Neo Química Arena");


        placeRepository.save(p1);
        placeRepository.save(p2);
        placeRepository.save(p3);
    }




    private void createEvents() {
        LocalDate date;
        LocalTime time;
        Event e1 = new Event();
        e1.setAdmin(adminRepository.findById(1L).get());
        e1.setAmountFreeTickets(100L);
        e1.setAmountPayedTickets(1000L);
        e1.setDescription("Shows de rock e atualidades");
        e1.setEmailContact("rockinrio@rio.com");
        date = LocalDate.parse("2019-02-02");
        e1.setEndDate(date);
        time = LocalTime.parse("22:30:50");
        e1.setEndTime(time);
        date = LocalDate.parse("2019-02-01");
        e1.setStartDate(date);
        time = LocalTime.parse("20:30:50");
        e1.setStartTime(time);
        e1.setName("Rock In Rio");
        e1.setPriceTicket(300.0);
        

        Event e2 = new Event();
        e2.setAdmin(adminRepository.findById(1L).get());
        e2.setAmountFreeTickets(1000L);
        e2.setAmountPayedTickets(10000L);
        e2.setDescription("Campeonato Sul-Americano");
        e2.setEmailContact("conmebol@outlook.com");
        date = LocalDate.parse("2021-02-02");
        e2.setEndDate(date);
        time = LocalTime.parse("06:30:50");
        e2.setEndTime(time);
        date = LocalDate.parse("2020-02-01");
        e2.setStartDate(date);
        time = LocalTime.parse("14:31:50");
        e2.setStartTime(time);
        e2.setName("Libertadores");
        e2.setPriceTicket(800.0);

        Event e3 = new Event();
        e3.setAdmin(adminRepository.findById(2L).get());
        e3.setAmountFreeTickets(10020L);
        e3.setAmountPayedTickets(100400L);
        e3.setDescription("Campeonato Paulista");
        e3.setEmailContact("fpf@outlook.com");
        date = LocalDate.parse("2021-07-02");
        e3.setEndDate(date);
        time = LocalTime.parse("19:30:50");
        e3.setEndTime(time);
        date = LocalDate.parse("2021-02-15");
        e3.setStartDate(date);
        time = LocalTime.parse("20:30:50");
        e3.setStartTime(time);
        e3.setName("Paulistao");
        e3.setPriceTicket(1900.0);

        Event e4 = new Event();
        e4.setAdmin(adminRepository.findById(3L).get());
        e4.setAmountFreeTickets(1020L);
        e4.setAmountPayedTickets(10000L);
        e4.setDescription("Campeonato mais aguardado do ano! Selecoes se encontram e jogam o melhor futebol!");
        e4.setEmailContact("fifa@outlook.com");
        date = LocalDate.parse("2022-11-02");
        e4.setEndDate(date);
        time = LocalTime.parse("21:30:50");
        e4.setEndTime(time);
        date = LocalDate.parse("2022-06-01");
        e4.setStartDate(date);
        time = LocalTime.parse("20:30:50");
        e4.setStartTime(time);
        e4.setName("Copa Do Mundo");
        e4.setPriceTicket(4900.0);

        eventRepository.save(e1);
        eventRepository.save(e2);
        eventRepository.save(e3);
        eventRepository.save(e4);
    }




    private void createAttendees() {
        Attendee a1 = new Attendee();
        a1.setBalance(0.0);
        a1.setEmail("roberto_sebastiao@outlook.com");
        a1.setName("Roberto Sebastião da Silva");

        Attendee a2 = new Attendee();
        a2.setBalance(100.0);
        a2.setEmail("ninjasupremo1243@gmail.com");
        a2.setName("Betinho Marcelo");

        Attendee a3 = new Attendee();
        a3.setBalance(0.0);
        a3.setEmail("nicolecristina@outlook.com");
        a3.setName("Nicole Cristina");

        Attendee a4 = new Attendee();
        a4.setBalance(0.0);
        a4.setEmail("jesromeu@outlook.com");
        a4.setName("Jeromeu Cassio");

        attendeeRepository.save(a1);
        attendeeRepository.save(a2);
        attendeeRepository.save(a3);
        attendeeRepository.save(a4);
    }




    private void createAdmins() {
        Admin a1 = new Admin();
        a1.setEmail("rodrigo_abdalla@outlook.com");
        a1.setName("Rodrigo Abdalla");
        a1.setPhoneNumber("(15)98156-7870");

        Admin a2 = new Admin();
        a2.setEmail("joao_angelotti@outlook.com");
        a2.setName("João Angelotti");
        a2.setPhoneNumber("(15)7070-7070");
    
        Admin a3 = new Admin();
        a3.setEmail("pedroso_teobaldo@hotmail.com");
        a3.setName("Teobaldo Pedroso");
        a3.setPhoneNumber("(15)98505-3070");

        
        adminRepository.save(a1);
        adminRepository.save(a2);
        adminRepository.save(a3);
    }

    




    
}