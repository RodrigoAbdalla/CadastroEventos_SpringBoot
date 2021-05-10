package com.example.event.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.example.event.dto.EventDTO;
import com.example.event.dto.EventInsertDTO;
import com.example.event.dto.EventUpdateDTO;
import com.example.event.entities.Event;
import com.example.event.repositories.EventRepository;
import com.example.event.repositories.PlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService {

    @Autowired
    private EventRepository repo;

    @Autowired
    private PlaceRepository placeRepository;

    public Page<EventDTO> getEvents(PageRequest pageRequest, String name, String description, String  startDateString, Float  priceTicket) {



                                                                             // FORMATO DE DATA ACEITO = yyyy-mm-dd  || yyyy/mm/dd  || yyyy.mm.dd 
        if(startDateString.contains("/")){                                  // Logica para trocar os caracteres incorretos, caso nao seja "-"
            startDateString = startDateString.replace("/", "-");
        }
        else if(startDateString.contains(".")){
            startDateString = startDateString.replace(".", "-");
        }
        try{                                                                        // Mapeando o erro para caso o usuario tente colocar uma data nom formato errado
            LocalDate startDate = LocalDate.parse(startDateString.trim());          // TRANSFORMA A STRING RECEBIDA EM UMA VARIAVEL LOCAL DATE 
            Page<Event> list = repo.find(pageRequest, name, description, startDate, priceTicket);     
            return list.map( e -> new EventDTO(e));
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Error trying to Convert to DataType. Please note that Data Format is yyyy/mm/dd");
        }
         
        

        
    }


    public EventDTO getEventById(Long id) {
        Optional<Event> op = repo.findById(id);
        Event event = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        return new EventDTO(event);
    }

    public EventDTO insert(EventInsertDTO insertDTO) {
        if( 
            insertDTO.getName()         == ""    ||                 // Logica para o programa nao aceitar nomes, descrições e nem lugares vazios / nulos
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
            insertDTO.getFreeTickectsSelled()     == null  ||
            insertDTO.getPayedTickectsSelled()     == null  ||
            insertDTO.getPriceTicket()     == null  ||
            insertDTO.getAdmin()     == null
        ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill in all the required fields");
        }
        else if(
            insertDTO.getStartDate().isAfter(insertDTO.getEndDate()) ||         // Logica para nao aceitar que a data final seja maior que a data inicial
                ((insertDTO.getStartDate().isEqual(insertDTO.getEndDate())) 
                && 
                (insertDTO.getStartTime().isAfter(insertDTO.getEndTime())))
            ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "An event cannot start after the end");
        }
        Event entity = new Event(insertDTO);
        entity = repo.save(entity);
        return new EventDTO(entity);
    }

    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } 
        catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    public EventDTO update(Long id, EventUpdateDTO updateDTO) {
        try {
            
            if( 
            updateDTO.getName()         == ""    ||                 // Logica para o programa nao aceitar nomes, descrições e nem lugares vazios / nulos
            updateDTO.getDescription()  == ""    || 
            updateDTO.getName()         == null  || 
            updateDTO.getDescription()  == null  ||
            updateDTO.getStartDate()    == null  ||
            updateDTO.getEndDate()      == null  ||
            updateDTO.getStartTime()    == null  ||
            updateDTO.getEndTime()      == null  ||
            updateDTO.getPriceTicket()     == null  ||
            updateDTO.getAdmin()     == null
            ){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill in all the required fields");
            }
            else if(
                updateDTO.getStartDate().isAfter(updateDTO.getEndDate()) ||         // Logica para nao aceitar que a data final seja maior que a data inicial
                ((updateDTO.getStartDate().isEqual(updateDTO.getEndDate())) 
                && 
                (updateDTO.getStartTime().isAfter(updateDTO.getEndTime())))
            ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "An event cannot start after the end");
            }
            else if(updateDTO.getStartDate().isBefore(LocalDateTime.now().toLocalDate())){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You cannot change the start date to before today");
            }

            Event entity = repo.getOne(id);
            if(entity.getEndDate().isBefore(LocalDateTime.now().toLocalDate()) || (entity.getEndDate().isEqual(LocalDateTime.now().toLocalDate()) && entity.getEndTime().isBefore(LocalDateTime.now().toLocalTime()))){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The event is over, update is not avaiable");
            }
            
        
            
            entity.setName(updateDTO.getName());
            entity.setDescription(updateDTO.getDescription());
            entity.setStartDate(updateDTO.getStartDate());
            entity.setEndDate(updateDTO.getEndDate());
            entity.setStartTime(updateDTO.getStartTime());
            entity.setEndTime(updateDTO.getEndTime());
            entity.setPriceTicket(updateDTO.getPriceTicket());
            entity.setAdmin(updateDTO.getAdmin());
            entity = repo.save(entity);
            return new EventDTO(entity);
        } 
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
            
        }
    }

/*
    public EventDTO addPlaceToEvent(Long idEvent, Long idPlace) {
        Event event = new Event();
        event = repo.findById(idEvent).get();
        event.addPlace(placeRepository.findById(idPlace).get());
        repo.save(event);
        return null;
    }


    public List<PlaceDTO> getPlaces(Long idEvent, PageRequest pageRequest) {
        Page<Place> list = repo.findById(id) (pageRequest, name, description, startDate, priceTicket);     
            return list.map( e -> new EventDTO(e));
        Event event = new Event();
        List <Place> places = new ArrayList<>();
        List <PlaceDTO> placesDTO = new ArrayList<>();
        event = repo.findById(idEvent).get();
        for (Place place : places) {
            placesDTO.add(PlaceDTO(place));
        }
        places = event.getPlaces();
        return places.map( e -> new PlaceDTO(e));
    }
*/
}
