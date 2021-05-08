package com.example.event.controllers;

import java.net.URI;

import com.example.event.dto.BaseUserDTO;
import com.example.event.dto.BaseUserInsertDTO;
import com.example.event.dto.BaseUserUpdateDTO;
import com.example.event.services.BaseUserService;

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
@RequestMapping("/base_users")
public class BaseUserController {
    
    @Autowired
    private BaseUserService service;  

    @GetMapping
    public ResponseEntity<Page<BaseUserDTO>> getBaseUsers(

        @RequestParam(value = "page",         defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction",    defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy",      defaultValue = "id") String orderBy,
        @RequestParam(value = "name",         defaultValue = "") String name,
        @RequestParam(value = "email",      defaultValue = "") String email
    ){
         
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
        Page <BaseUserDTO> list = service.getBaseUsers(pageRequest, name.trim(), email.trim());
        
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<BaseUserDTO> getBaseUserById(@PathVariable Long id){
        BaseUserDTO dto = service.getBaseUserById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
	public ResponseEntity<BaseUserDTO> insert(@RequestBody BaseUserInsertDTO insertDto){
		BaseUserDTO dto = service.insert(insertDto); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

    @DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id); 
		return ResponseEntity.noContent().build();
	}
    
    @PutMapping("{id}")
	public ResponseEntity<BaseUserDTO> update(@RequestBody BaseUserUpdateDTO updateDto, @PathVariable Long id){
		BaseUserDTO dto = service.update(id, updateDto); 
		return ResponseEntity.ok().body(dto);
	}
}
