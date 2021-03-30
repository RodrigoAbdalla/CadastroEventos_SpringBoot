package com.example.event.controllers;

import java.net.URI;
import java.util.List;

import com.example.event.dto.EventDTO;
import com.example.event.dto.EventInsertDTO;
import com.example.event.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/events")
public class EventController {
    
    @Autowired
    private EventService service;  

    @GetMapping
    public ResponseEntity<List<EventDTO>> getEvents(){
        List <EventDTO> list = service.getEvents();
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
}
