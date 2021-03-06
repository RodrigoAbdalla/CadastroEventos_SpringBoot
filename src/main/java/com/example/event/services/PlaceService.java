package com.example.event.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.example.event.dto.PlaceDTO;
import com.example.event.dto.PlaceInsertDTO;
import com.example.event.dto.PlaceUpdateDTO;
import com.example.event.entities.Place;
import com.example.event.repositories.PlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlaceService{

    @Autowired
    private PlaceRepository repo;

    public Page<PlaceDTO> getPlaces(PageRequest pageRequest, String name, String adress) {
        Page<Place> list = repo.find(pageRequest, name, adress);     
        return list.map( e -> new PlaceDTO(e));
    }


    public PlaceDTO getPlaceById(Long id) {
        Optional<Place> op = repo.findById(id);
        Place place = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));
        return new PlaceDTO(place);
    }

    public PlaceDTO insert(PlaceInsertDTO insertDTO) {
        
        // Logica para o programa nao aceitar nomes e endereços vazios / nulos
        if( 
            insertDTO.getName()         == ""    ||                 
            insertDTO.getAdress()       == ""    ||  
            insertDTO.getName()         == null  || 
            insertDTO.getAdress()       == null 
        )
        {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill in all the required fields");
        }
        
        Place entity = new Place (insertDTO);
        entity = repo.save(entity);
        return new PlaceDTO(entity);
    }

    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } 
        catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
        // Tratamento para caso o lugar já possua um evento cadastrado, 
        // neste caso, só é possivel excluindo todas as associações primeiro, com o DELETE /events/{id}/places/{id} (Disponível apenas na AF)
        catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This Place has an Event. To remove a place, you need to first delete the associated events.");
        }
    }

    public PlaceDTO update(Long id, PlaceUpdateDTO updateDTO) {
        try {
            Place entity = repo.getOne(id);
            entity.setName(updateDTO.getName());
            entity = repo.save(entity);
            return new PlaceDTO(entity);
        } 
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
    }

}
