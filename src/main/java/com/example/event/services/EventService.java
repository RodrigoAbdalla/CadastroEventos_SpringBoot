package com.example.event.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.example.event.dto.EventDTO;
import com.example.event.dto.EventInsertDTO;
import com.example.event.dto.EventTicketDTO;
import com.example.event.dto.EventUpdateDTO;
import com.example.event.dto.TicketInsertDeletelDTO;
import com.example.event.dto.TicketsDTO;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private EventRepository repo;

    @Autowired 
    private TicketRepository ticketRepository;

    @Autowired 
    private AttendeeRepository attendeeRepository;

    @Autowired
    private AdminRepository adminRepository;
    public Page<EventDTO> getEvents(PageRequest pageRequest, String name, String description, String  startDateString, Double  priceTicket, String place) {

        // FORMATO DE DATA ACEITO = yyyy-mm-dd  || yyyy/mm/dd  || yyyy.mm.dd 

        // Logica para trocar os caracteres incorretos, caso nao seja "-"
        if(startDateString.contains("/")){                                  
            startDateString = startDateString.replace("/", "-");
        }
        else if(startDateString.contains(".")){
            startDateString = startDateString.replace(".", "-");
        }
        

        // Mapeando o erro para caso o usuario tente colocar uma data nom formato errado

        try{      
            // TRANSFORMA A STRING RECEBIDA EM UMA VARIAVEL LOCAL DATE                                                                  
            LocalDate startDate = LocalDate.parse(startDateString.trim());          
            Page<Event> list = repo.find(pageRequest, name, description, startDate, priceTicket, place);
            return list.map( e -> new EventDTO(e));
        }
        catch(DateTimeParseException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Error trying to Convert to DataType. Please note that Data Format is yyyy/MM/dd");
        }
         
        

        
    }


    public EventDTO getEventById(Long id) {
        Optional<Event> op = repo.findById(id);
        Event event = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        return new EventDTO(event);
    }

    public EventDTO insert(EventInsertDTO insertDTO) {
        
        // Logica para o programa nao aceitar nomes, descri????es e nem lugares vazios / nulos
        if( 
            insertDTO.getName()         == ""    ||                 
            insertDTO.getDescription()  == ""    || 
            insertDTO.getName()         == null  || 
            insertDTO.getDescription()  == null  ||
            insertDTO.getStartDate()    == null  ||
            insertDTO.getEndDate()      == null  ||
            insertDTO.getStartTime()    == null  ||
            insertDTO.getEndTime()      == null  ||
            insertDTO.getEmailContact() == null ||
            insertDTO.getAmountFreeTickets()     == null  ||
            insertDTO.getAmountPayedTickets()     == null  ||
            insertDTO.getPriceTicket()     == null  ||
            insertDTO.getAdmin()     == null
        ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill in all the required fields");
        }
        // Logica para nao aceitar que a data final seja maior que a data inicial
        else if(
            insertDTO.getStartDate().isAfter(insertDTO.getEndDate()) ||         
                ((insertDTO.getStartDate().isEqual(insertDTO.getEndDate())) 
                && 
                (insertDTO.getStartTime().isAfter(insertDTO.getEndTime())))
            ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "An event cannot start after the end");
        }
        // Logica para nao aceitar que eventos no passado sejam adicionados
        else if(insertDTO.getEndDate().isBefore(LocalDateTime.now().toLocalDate()) || (insertDTO.getEndDate().isEqual(LocalDateTime.now().toLocalDate()) && insertDTO.getEndTime().isBefore(LocalDateTime.now().toLocalTime()))){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You can't add an event that is over");
        }  
        try {
            Event entity = new Event(insertDTO);
            // Coleta o numero do ADMIN que est?? guardado no inserDTO e procura no repository
            entity.setAdmin(adminRepository.findById(insertDTO.getAdmin()).get());
            entity = repo.save(entity);
            return new EventDTO(entity);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
        
    }

    public void delete(Long id) {
        try {
            Event entity = repo.findById(id).get();
            if((entity.getStartDate().isBefore(LocalDate.now()) || entity.getStartDate().isEqual(LocalDate.now())) & (entity.getEndDate().isAfter(LocalDate.now()) || (entity.getEndDate().isEqual(LocalDate.now()) & entity.getEndTime().compareTo(LocalTime.now()) != -1 )))
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The event is already happening, delete is not avaiable");
    
            repo.deleteById(id);
        } 
        catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
        // Tratamento para caso o evento j?? possua um lugar cadastrado, 
        // neste caso, s?? ?? possivel excluindo todas as associa????es primeiro, com o DELETE /events/{id}/places/{id} (Dispon??vel apenas na AF)
        catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The event has associations, please be sure that all places associations are removed and none tickets sold");
        }
        
    }

    public EventDTO update(Long id, EventUpdateDTO updateDTO) {
        try {
            // Logica para o programa nao aceitar nomes, descri????es e nem lugares vazios / nulos
            if( 
            updateDTO.getName()         == ""    ||                 
            updateDTO.getDescription()  == ""    || 
            updateDTO.getName()         == null  || 
            updateDTO.getDescription()  == null  ||
            updateDTO.getStartDate()    == null  ||
            updateDTO.getEndDate()      == null  ||
            updateDTO.getStartTime()    == null  ||
            updateDTO.getEndTime()      == null  ||
            updateDTO.getPriceTicket()     == null
            ){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill in all the required fields");
            }
            // Logica para nao aceitar que a data final seja maior que a data inicial
            else if(
                updateDTO.getStartDate().isAfter(updateDTO.getEndDate()) ||         
                ((updateDTO.getStartDate().isEqual(updateDTO.getEndDate())) 
                && 
                (updateDTO.getStartTime().isAfter(updateDTO.getEndTime())))
            ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "An event cannot start after the end");
            }
            // Logica para nao aceitar mudan??as de data do evento para antes da data atual.
            else if(updateDTO.getStartDate().isBefore(LocalDateTime.now().toLocalDate())){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You cannot change the start date to before today");
            }

            // Logica para n??o aceitar mudan??as para eventos que j?? terminaram ou que est??o em andamento
            Event entity = repo.getOne(id);
            if(entity.getEndDate().isBefore(LocalDateTime.now().toLocalDate()) || (entity.getEndDate().isEqual(LocalDateTime.now().toLocalDate()) && entity.getEndTime().isBefore(LocalDateTime.now().toLocalTime()))){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The event is over, update is not avaiable");
            }
            else if((entity.getStartDate().isBefore(LocalDate.now()) || entity.getStartDate().isEqual(LocalDate.now())) & (entity.getEndDate().isAfter(LocalDate.now()) || (entity.getEndDate().isEqual(LocalDate.now()) & entity.getEndTime().compareTo(LocalTime.now()) != -1 )))
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The event is already happening, update is not avaiable");
        
            
            entity.setName(updateDTO.getName());
            entity.setDescription(updateDTO.getDescription());
            entity.setStartDate(updateDTO.getStartDate());
            entity.setEndDate(updateDTO.getEndDate());
            entity.setStartTime(updateDTO.getStartTime());
            entity.setEndTime(updateDTO.getEndTime());
            entity.setPriceTicket(updateDTO.getPriceTicket());
            entity = repo.save(entity);
            return new EventDTO(entity);
        } 
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }


    public EventDTO addPlaceToEvent(Long idEvent, Long idPlace) {

        // Verifica????o se existe o Event com o ID solicitado
        Optional<Event> opEvent = repo.findById(idEvent);
        Event event = opEvent.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        // Verifica????o se existe o Place com o ID solicitado
        Optional<Place> opPlace = placeRepository.findById(idPlace);
        Place place = opPlace.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));

        // Verifica????o da disponibilidade das datas 
        List <Event> listEvents = new ArrayList<>();
        listEvents = place.getEvents();
        for (Event e : listEvents) {
            // Verifica????o para n??o permitir que o mesmo evento seja adicionado ao mesmo lugar
            if(e.getId() == event.getId()){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You have already link this place to this event");
            }
            // Verifica????o para n??o permitir que dois eventos que possuem hor??rios simult??neos aconte??am  no mesmo lugar. 
            if (

                (event.getStartDate().isAfter(e.getStartDate()) & event.getStartDate().isBefore(e.getEndDate()) & event.getStartTime().isAfter(e.getStartTime()) & event.getStartTime().isBefore(e.getEndTime())) ||
                (event.getStartDate().isAfter(e.getStartDate()) & event.getStartDate().isBefore(e.getEndDate()) & event.getEndTime().isAfter(e.getStartTime()) & event.getEndTime().isBefore(e.getEndTime())) ||
                (event.getStartDate().isAfter(e.getStartDate()) & event.getStartDate().isBefore(e.getEndDate()) & event.getStartTime().isBefore(e.getStartTime()) & event.getEndTime().isAfter(e.getEndTime()))  ||
                (event.getStartDate().isAfter(e.getStartDate()) & event.getStartDate().isBefore(e.getEndDate()) & event.getStartTime().compareTo(e.getStartTime())== 0)  ||
                (event.getStartDate().isAfter(e.getStartDate()) & event.getStartDate().isBefore(e.getEndDate()) & event.getEndTime().compareTo(e.getEndTime())== 0)  ||

                (event.getEndDate().isAfter(e.getStartDate()) & event.getEndDate().isBefore(e.getEndDate()) & event.getStartTime().isAfter(e.getStartTime()) & event.getStartTime().isBefore(e.getEndTime())) ||
                (event.getEndDate().isAfter(e.getStartDate()) & event.getEndDate().isBefore(e.getEndDate()) & event.getEndTime().isAfter(e.getStartTime()) & event.getEndTime().isBefore(e.getEndTime())) ||
                (event.getEndDate().isAfter(e.getStartDate()) & event.getEndDate().isBefore(e.getEndDate()) & event.getStartTime().isBefore(e.getStartTime()) & event.getEndTime().isAfter(e.getEndTime())) ||
                (event.getEndDate().isAfter(e.getStartDate()) & event.getEndDate().isBefore(e.getEndDate()) & event.getStartTime().compareTo(e.getStartTime())== 0)  ||
                (event.getEndDate().isAfter(e.getStartDate()) & event.getEndDate().isBefore(e.getEndDate()) & event.getEndTime().compareTo(e.getEndTime())== 0)  ||

                (event.getEndDate().isEqual(e.getStartDate())  & event.getStartTime().isAfter(e.getStartTime()) & event.getStartTime().isBefore(e.getEndTime())) ||
                (event.getEndDate().isEqual(e.getStartDate())  & event.getEndTime().isAfter(e.getStartTime()) & event.getEndTime().isBefore(e.getEndTime())) ||
                (event.getEndDate().isEqual(e.getStartDate())  & event.getStartTime().isBefore(e.getStartTime()) & event.getEndTime().isAfter(e.getEndTime())) ||
                (event.getEndDate().isEqual(e.getStartDate())  & event.getStartTime().compareTo(e.getStartTime())== 0)  ||
                (event.getEndDate().isEqual(e.getStartDate())  & event.getEndTime().compareTo(e.getEndTime())== 0)  ||

                (event.getStartDate().isEqual(e.getStartDate())  & event.getStartTime().isAfter(e.getStartTime()) & event.getStartTime().isBefore(e.getEndTime())) ||
                (event.getStartDate().isEqual(e.getStartDate())  & event.getEndTime().isAfter(e.getStartTime()) & event.getEndTime().isBefore(e.getEndTime())) ||
                (event.getStartDate().isEqual(e.getStartDate())  & event.getStartTime().isBefore(e.getStartTime()) & event.getEndTime().isAfter(e.getEndTime())) ||
                (event.getStartDate().isEqual(e.getStartDate())  & event.getStartTime().compareTo(e.getStartTime())== 0)  ||
                (event.getStartDate().isEqual(e.getStartDate())  & event.getEndTime().compareTo(e.getEndTime())== 0)  ||

                (event.getEndDate().isEqual(e.getEndDate())  & event.getStartTime().isAfter(e.getStartTime()) & event.getStartTime().isBefore(e.getEndTime())) ||
                (event.getEndDate().isEqual(e.getEndDate())  & event.getEndTime().isAfter(e.getStartTime()) & event.getEndTime().isBefore(e.getEndTime())) ||
                (event.getEndDate().isEqual(e.getEndDate())  & event.getStartTime().isBefore(e.getStartTime()) & event.getEndTime().isAfter(e.getEndTime())) ||
                (event.getEndDate().isEqual(e.getEndDate())  & event.getStartTime().compareTo(e.getStartTime())== 0)  ||
                (event.getEndDate().isEqual(e.getEndDate())  & event.getEndTime().compareTo(e.getEndTime())== 0)  ||

                (event.getStartDate().isEqual(e.getEndDate())  & event.getStartTime().isAfter(e.getStartTime()) & event.getStartTime().isBefore(e.getEndTime())) ||
                (event.getStartDate().isEqual(e.getEndDate())  & event.getEndTime().isAfter(e.getStartTime()) & event.getEndTime().isBefore(e.getEndTime())) ||
                (event.getStartDate().isEqual(e.getEndDate())  & event.getStartTime().isBefore(e.getStartTime()) & event.getEndTime().isAfter(e.getEndTime())) ||
                (event.getStartDate().isEqual(e.getEndDate())  & event.getStartTime().compareTo(e.getStartTime())== 0)  ||
                (event.getStartDate().isEqual(e.getEndDate())  & event.getEndTime().compareTo(e.getEndTime())== 0)  
            ){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "An Event will happen in the same place at the same time");
            }
            if((event.getStartDate().isBefore(LocalDate.now()) || event.getStartDate().isEqual(LocalDate.now())) & (event.getEndDate().isAfter(LocalDate.now()) || (event.getEndDate().isEqual(LocalDate.now()) & event.getEndTime().compareTo(LocalTime.now()) != -1 )))
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You can't add a place to an event that is already happening");
            else if(event.getEndDate().isBefore(LocalDate.now()) || (event.getEndDate().isEqual(LocalDate.now()) && event.getEndTime().isBefore(LocalTime.now()))){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You can't add a place to an event that is over");
            }
            
        }
        event.addPlace(place);
        repo.save(event);
        return null;
    }


    public void removeLinkPlaceEvent(Long idEvent, Long idPlace) {
        // Verifica????o se existe o Event com o ID solicitado
        Optional<Event> opEvent = repo.findById(idEvent);
        Event event = opEvent.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        // Verifica????o se existe o Place com o ID solicitado
        Optional<Place> opPlace = placeRepository.findById(idPlace);
        Place place = opPlace.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));

        // Verifica????o para garantir que existe uma conex??o com o Evento e Lugar solicitados
        place = event.getPlaceById(idPlace);
        if (place == null)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This event has no link with this place.");
        
        
        // Verifica????o para n??o permitir que um lugar seja exclu??do do evento se o evento j?? tiver come??ado ou ja tiver acabado.
        if((event.getStartDate().isBefore(LocalDate.now()) || event.getStartDate().isEqual(LocalDate.now())) & (event.getEndDate().isAfter(LocalDate.now()) || (event.getEndDate().isEqual(LocalDate.now()) & event.getEndTime().compareTo(LocalTime.now()) != -1 )))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You can't change the place of a event that is already happening");
        else if(event.getEndDate().isBefore(LocalDate.now()) || (event.getEndDate().isEqual(LocalDate.now()) && event.getEndTime().isBefore(LocalTime.now()))){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You can't change the place of an event that is over");
        }
        // Remove a conex??o do evento e lugar
        event.removePlace(place);
        repo.save(event);
    }


    public EventTicketDTO getTicketsByEvent(Long id) {
        // Coleta o Evento solicitado
        Optional<Event> op = repo.findById(id);
        Event event = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        // Coleta todos os tickets pertencentes ao Evento solicitado
        List<Ticket> listTicket = ticketRepository.findByEvent(id);                 

        // Coleta a quantidade de tickets vendidos, por tipo.
        Long amountFreeTicketsSold = Long.valueOf(ticketRepository.findFreeTicketsSold(id).size());
        Long amountPayedTicketsSold = Long.valueOf(ticketRepository.findPayedTicketsSold(id).size());

        // Transforma a lista de tickets no DTO desejado
        List<TicketsDTO> listTicketsDTO = new ArrayList<>();
        for (Ticket ticket : listTicket) {
            listTicketsDTO.add(new TicketsDTO(ticket));
        }

        // Retorna o DTO do evento 
        return new EventTicketDTO(event, amountPayedTicketsSold, amountFreeTicketsSold, listTicketsDTO);
    }


    public void removeTicket(Long id, TicketInsertDeletelDTO deleteTicket) {
        // Coleta o Evento solicitado
        Optional<Event> op = repo.findById(id);
        Event event = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        // Verfica????o de dados
        if( 
            
            deleteTicket.getAttendee()  == null    || 
            deleteTicket.getType() == null
        ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill in all the required fields");
        }
        else if(     
            deleteTicket.getType().toUpperCase().compareTo("FREE") != 0 &&
            deleteTicket.getType().toUpperCase().compareTo("PAYED") != 0
        ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Ticket Type can only be 'FREE' OR 'PAYED'");
        }

        // Verifica????o se o Evento j?? est?? ocorrendo ou ?? passado
        if((event.getStartDate().isBefore(LocalDate.now()) || event.getStartDate().isEqual(LocalDate.now())) & (event.getEndDate().isAfter(LocalDate.now()) || (event.getEndDate().isEqual(LocalDate.now()) & event.getEndTime().compareTo(LocalTime.now()) != -1 )))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You can't refund a ticket of an event that is already running");
        else if(event.getEndDate().isBefore(LocalDate.now()) || (event.getEndDate().isEqual(LocalDate.now()) && event.getEndTime().isBefore(LocalTime.now()))){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You can't refund a ticket of an event that is over");
        }

        // Coleta o Attendee solicitado
        Optional<Attendee> opAttende = attendeeRepository.findById(deleteTicket.getAttendee());
        Attendee attendee = opAttende.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found"));
        List<Ticket> listTickets = new ArrayList<>();


        // Procura no Banco de dados algum ticket com a especifica????o (s?? tem como retornar 0 ou 1, pois no insertTicket n??o ?? permitido o mesmo ticket para o mesmo attendee/evento)
        // Faz as mudan??as necess??rias (Adiciona balance, aumenta a quantidade de tickets dispon??veis, etc)
        try {
            if(deleteTicket.getType().toUpperCase().compareTo("FREE") == 0){
                listTickets = ticketRepository.findTickets(id, deleteTicket.getAttendee(), TicketType.FREE);
                event.setAmountFreeTickets(event.getAmountFreeTickets() + 1);
            }
            else{
                listTickets = ticketRepository.findTickets(id, deleteTicket.getAttendee(), TicketType.PAYED);
                event.setAmountPayedTickets(event.getAmountPayedTickets() + 1);
                attendee.setBalance(attendee.getBalance() + listTickets.get(0).getPrice());
            }
            ticketRepository.deleteById(listTickets.get(0).getId());
        } 
        catch (IndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "None ticket were found with this especifications");
        }
        
        //Atualiza as entidades que foram mudadas. 
        attendeeRepository.save(attendee);
        repo.save(event);


    }


    public void addTicketToEvent(Long id, TicketInsertDeletelDTO insertTicket) {
        // Coleta o Evento solicitado
        Optional<Event> op = repo.findById(id);
        Event event = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        // Verifica????o se o evento j?? acabou.
        if(event.getEndDate().isBefore(LocalDate.now()) || (event.getEndDate().isEqual(LocalDate.now()) && event.getEndTime().isBefore(LocalTime.now()))){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You can't sell a ticket of an event that is over");
        }

        // Verifica????o de dados
        if( 
            
            insertTicket.getAttendee()  == null    || 
            insertTicket.getType() == null
        ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill in all the required fields");
        }
        else if(     
            insertTicket.getType().toUpperCase().compareTo("FREE") != 0 &&
            insertTicket.getType().toUpperCase().compareTo("PAYED") != 0
        ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Ticket Type can only be 'FREE' OR 'PAYED'");
        }

        // Verifica se ?? poss??vel vender.
        if(insertTicket.getType().toUpperCase().compareTo("FREE") == 0 && event.getAmountFreeTickets() == 0){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This Event has no free tickets avaiable");
        }
        if(insertTicket.getType().toUpperCase().compareTo("PAYED") == 0 && event.getAmountPayedTickets() == 0){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This Event has no payed tickets avaiable");
        }

        


        // Coleta o Attendee solicitado
        Optional<Attendee> opAttende = attendeeRepository.findById(insertTicket.getAttendee());
        Attendee attendee = opAttende.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found"));

        // Verifica se o Attendee j?? possui um ingresso do mesmo evento e mesmo tipo
        List<Ticket> listTicket = ticketRepository.findByAttendee(insertTicket.getAttendee());
        System.out.println(listTicket);
        
        for (Ticket ticket : listTicket) {
            System.out.println(ticket.getType().toString().compareTo(insertTicket.getType()) == 0);
            System.out.println(ticket.getType().toString());
            System.out.println(insertTicket.getType());
            if(ticket.getType().toString().compareTo(insertTicket.getType().toUpperCase()) == 0  && ticket.getEvent().getId() == id){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "An Attendee can not have two identical tickets (The same event and type)");
            }
        }

        // Diminui a quantidade de ticket disponivel no evento
        if(insertTicket.getType().toUpperCase().compareTo("FREE") == 0){
            event.setAmountFreeTickets(event.getAmountFreeTickets() - 1);
        }
        else{
            event.setAmountPayedTickets(event.getAmountPayedTickets() - 1);
        }


        Ticket ticket = new Ticket(insertTicket, attendee, event);
        ticketRepository.save(ticket);
        repo.save(event);
        attendeeRepository.save(attendee);
    }

}
