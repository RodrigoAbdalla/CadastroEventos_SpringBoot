package com.example.event.dto;

public class EventDTO {
    private String name;
    private String description;
    private String place;
    private String emailContact;

    public EventDTO() {
	}

    public EventDTO(String name, String description, String place, String emailContact) {
        setName(name);
        setPlace(place);
        setDescription(description);
        setEmailContact(emailContact);
	}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public String getEmailContact() {
        return emailContact;
    }
    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }
    

}
