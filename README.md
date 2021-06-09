# ProjetoLab-AC1-Individual

LINK HEROKU: https://poo-facens-2021-s1.herokuapp.com/


INTEGRANTES: 

Rodrigo Abdalla Ramos da Silva	 - 190214 

joão Victor Timo Angelotti Pinto - 190826 


-------------------------------------------------------------

*** POST ***

Post - Places
{
            "name": "Morumbi",
            "adress": "Praça Roberto Gomes Pedrosa, 1 - Morumbi, São Paulo - SP"
}


Post - Events
{
            "name": "TESTE",
            "description": "Shows de rock e atualidades",
            "startDate": "2019-02-01",
            "endDate": "2019-02-02",
            "startTime": "20:30:50",
            "endTime": "22:30:50",
            "emailContact": "rockinrio@rio.com",
            "amountFreeTickets": 2000,
            "amountPayedTickets": 1000,
            "priceTicket": 300,
            "admin": 1
}

Post - Attendees
{
            "name": "Roberto Sebastião da Silva",
            "email": "roberto_sebastiao@outlook.com"
}


Post -  Admins
{
            "name": "Teste",
            "email": "rodrigo_abdalla@outlook.com",
            "phoneNumber": "(15)98156-7870"
}

Post - /events/{id}/tickets 
{
            "type": "Payed",
            "idAttendee": 7
}
-----------------------------------------------------------

***PUT***

Put - Events
{
            "name": "TESTE",
            "description": "Shows de rock e atualidades",
            "startDate": "2019-02-01",
            "endDate": "2019-02-02",
            "startTime": "20:30:50",
            "endTime": "22:30:50",
            "priceTicket": 300
}

Put - Places
{
            "name": "teste"
}


Put - Attendees
{
            "name": "Roberto Sebastião da Silva",
            "email": "roberto_sebastiao@outlook.com"
}


Put -  Admins
{
            "name": "Teste",
            "email": "rodrigo_abdalla@outlook.com",
            "phoneNumber": "(15)98156-7870"
}


-----------------------------
***DELETE***


Delete - /events/{id}/tickets 
{
            "type": "payed",
            "idAttendee": 7
}