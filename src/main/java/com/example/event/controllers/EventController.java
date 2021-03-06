package com.example.event.controllers;

import java.net.URI;
import com.example.event.dto.EventDTO;
import com.example.event.dto.EventInsertDTO;
import com.example.event.dto.EventTicketDTO;
import com.example.event.dto.EventUpdateDTO;
import com.example.event.dto.TicketInsertDeletelDTO;
import com.example.event.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/events")
public class EventController {
    
    @Autowired
    private EventService service;  

    @GetMapping
    public ResponseEntity<Page<EventDTO>> getEvents(

        // Paginação
        @RequestParam(value = "page",         defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction",    defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy",      defaultValue = "id") String orderBy,
        @RequestParam(value = "name",         defaultValue = "") String name,
        @RequestParam(value = "description",      defaultValue = "") String description,        
        @RequestParam(value = "startDate",      defaultValue = "0000-01-01") String  startDateString, // FORMATO ACEITO = yyyy-mm-dd  || yyyy/mm/dd  || yyyy.mm.dd,
        @RequestParam(value = "priceTicket",      defaultValue = "9999999999999") Double  priceTicket,        // Filtro para receber os eventos com o preço do ticket menor ou igual ao solicitado
        @RequestParam(value = "place",      defaultValue = "") String place                             // Filtro para fazer receber apenas os eventos que contenham os places descritos
        ){
         
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
        Page <EventDTO> list = service.getEvents(pageRequest, name.trim(), description.trim(), startDateString,  priceTicket, place);
        
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id){
        EventDTO dto = service.getEventById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
	public ResponseEntity<EventDTO> insert(@RequestBody EventInsertDTO insertDto){
		EventDTO dto = service.insert(insertDto); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

    @DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id); 
		return ResponseEntity.noContent().build();
	}
    
    @PutMapping("{id}")
	public ResponseEntity<EventDTO> update(@RequestBody EventUpdateDTO updateDto, @PathVariable Long id){
		EventDTO dto = service.update(id, updateDto); 
		return ResponseEntity.ok().body(dto);
	}

    @PostMapping("{idEvent}/places/{idPlace}")
	public ResponseEntity<Void> addPlaceToEvent(@PathVariable Long idEvent, @PathVariable Long idPlace){
		service.addPlaceToEvent(idEvent, idPlace); 
		return ResponseEntity.noContent().build();      
	}

    @DeleteMapping("{idEvent}/places/{idPlace}")
	public ResponseEntity<Void> removeLinkPlaceEvent(@PathVariable Long idEvent, @PathVariable Long idPlace){
		service.removeLinkPlaceEvent(idEvent, idPlace); 
		return ResponseEntity.noContent().build();      
	}

    @GetMapping("{id}/tickets")
    public ResponseEntity<EventTicketDTO> getTicketsByEvent(@PathVariable Long id){
        EventTicketDTO eventTicket = service.getTicketsByEvent(id);
        return ResponseEntity.ok().body(eventTicket);
    }


    @DeleteMapping("{id}/tickets")
	public ResponseEntity<Void> removeTicket(@PathVariable Long id, @RequestBody TicketInsertDeletelDTO deleteTicket){
		service.removeTicket(id, deleteTicket); 
		return ResponseEntity.noContent().build();      
	}
    

    @PostMapping("{id}/tickets")
	public ResponseEntity<Void> addTicketToEvent(@PathVariable Long id, @RequestBody TicketInsertDeletelDTO insertTicket){
		service.addTicketToEvent(id, insertTicket); 
		return ResponseEntity.noContent().build();      
	}
}
