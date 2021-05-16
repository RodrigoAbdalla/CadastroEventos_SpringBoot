package com.example.event.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.example.event.dto.AdminDTO;
import com.example.event.dto.AdminInsertDTO;
import com.example.event.dto.AdminUpdateDTO;
import com.example.event.entities.Admin;
import com.example.event.repositories.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repo;

    public Page<AdminDTO> getAdmins(PageRequest pageRequest, String name, String email, String phoneNumber) {


        try{                                                
            Page<Admin> list = repo.find(pageRequest, name, email, phoneNumber);     
            return list.map( e -> new AdminDTO(e));
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Error trying to Convert to DataType. Please note that Data Format is yyyy/mm/dd");
        }
  
    }


    public AdminDTO getAdminById(Long id) {
        Optional<Admin> op = repo.findById(id);
        Admin admin = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));
        return new AdminDTO(admin);
    }

    public AdminDTO insert(AdminInsertDTO insertDTO) {
        if( 
            insertDTO.getName()         == ""    ||                 // Logica para o programa nao aceitar nomes, descrições e nem lugares vazios / nulos
            insertDTO.getEmail()        == ""    || 
            insertDTO.getPhoneNumber()  == ""    || 
            insertDTO.getName()         == null  || 
            insertDTO.getEmail()        == null  || 
            insertDTO.getPhoneNumber()  == null
            
        ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please fill in all the required fields");
        }
        
        Admin entity = new Admin(insertDTO);
        entity = repo.save(entity);
        return new AdminDTO(entity);
    }

    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } 
        catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }

    public AdminDTO update(Long id, AdminUpdateDTO updateDTO) {
        try {
            Admin entity = repo.getOne(id);
            entity.setName(updateDTO.getName());
            entity.setEmail(updateDTO.getEmail());
            entity.setPhoneNumber(updateDTO.getPhoneNumber());

            entity = repo.save(entity);
            return new AdminDTO(entity);
        } 
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }

}
