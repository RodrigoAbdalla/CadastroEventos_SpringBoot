package com.example.event.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.event.dto.EventDTO;
import com.example.event.entities.Event;
import com.example.event.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService {

    @Autowired
    private EventRepository repo;

    public List<com.example.event.dto.EventDTO> getEvents() {
        List<Event> list = repo.findAll();
        return toDTOList(list);
    }

    public EventDTO getEventById(Long id) {
        Optional<Event> op = repo.findById(id);
        Event event = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
        return new EventDTO(event);
    }

    private List<EventDTO> toDTOList(List<Event> list) {
        List<EventDTO> listDTO = new ArrayList<>();

        for (Event e : list) {
            listDTO.add(new EventDTO(e.getId(), e.getName(), e.getDescription(), e.getPlace(), e.getStartDate(), e.getEndDate(),
                    e.getStartTime(), e.getEndTime()));
        }
        return listDTO;
    }

}
