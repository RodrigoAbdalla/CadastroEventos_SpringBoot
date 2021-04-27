package com.example.event.dto;

import com.example.event.entities.Place;

public class PlaceDTO {
    private Long id;
    private String name;
    private String adress;
    
    
    public PlaceDTO() {
	}

    public PlaceDTO(Long id, String name, String adress) {
        setId(id);
        setName(name);
        setAdress(adress);
	}

    public PlaceDTO(Place place) {
        setId(place.getId());
        setName(place.getName());
        setAdress(place.getAdress());
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    

}
