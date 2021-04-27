package com.example.event.dto;

import com.example.event.entities.Place;

public class PlaceUpdateDTO {
    
    private String name;
    
    
    public PlaceUpdateDTO() {
	}

    public PlaceUpdateDTO(String name) {
        setName(name);
	}

    public PlaceUpdateDTO(Place place) {
        setName(place.getName());
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    

}
