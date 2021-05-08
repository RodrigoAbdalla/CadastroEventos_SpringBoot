package com.example.event.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.example.event.dto.BaseUserDTO;
import com.example.event.dto.BaseUserInsertDTO;
import com.example.event.dto.BaseUserUpdateDTO;
import com.example.event.entities.BaseUser;
import com.example.event.repositories.BaseUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BaseUserService {

    @Autowired
    private BaseUserRepository repo;

    public Page<BaseUserDTO> getBaseUsers(PageRequest pageRequest, String name, String email) {


        try{                                                
            Page<BaseUser> list = repo.find(pageRequest, name, email);     
            return list.map( e -> new BaseUserDTO(e));
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Error trying to Convert to DataType. Please note that Data Format is yyyy/mm/dd");
        }
  
    }


    public BaseUserDTO getBaseUserById(Long id) {
        Optional<BaseUser> op = repo.findById(id);
        BaseUser base_user = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "BaseUser not found"));
        return new BaseUserDTO(base_user);
    }

    public BaseUserDTO insert(BaseUserInsertDTO insertDTO) {
        if( 
            insertDTO.getName()         == ""    ||                 // Logica para o programa nao aceitar nomes, descrições e nem lugares vazios / nulos
            insertDTO.getEmail()        == ""    || 
            insertDTO.getName()         == null  || 
            insertDTO.getEmail()        == null
        ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill in all the required fields");
        }
        
        BaseUser entity = new BaseUser(insertDTO);
        entity = repo.save(entity);
        return new BaseUserDTO(entity);
    }

    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } 
        catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BaseUser not found");
        }
    }

    public BaseUserDTO update(Long id, BaseUserUpdateDTO updateDTO) {
        try {
            BaseUser entity = repo.getOne(id);
            entity.setName(updateDTO.getName());
            entity.setEmail(updateDTO.getEmail());

            entity = repo.save(entity);
            return new BaseUserDTO(entity);
        } 
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BaseUser not found");
        }
    }

}
