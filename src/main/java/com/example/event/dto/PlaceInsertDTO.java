package com.example.event.dto;

import com.example.event.entities.Place;

public class PlaceInsertDTO {
   
    private String name;
    private String adress;
    
    
    public PlaceInsertDTO() {
	}

    public PlaceInsertDTO(String name, String adress) {
        setName(name);
        setAdress(adress);
	}

    public PlaceInsertDTO(Place place) {
        setName(place.getName());
        setAdress(place.getAdress());
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    

}
