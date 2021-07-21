# Este projeto foi desenvolvido para entrega final da matéria de programação orientada a objetos II, na FACENS.

## Link no Heroku: https://poo-facens-2021-s1.herokuapp.com/

## Modelo Conceitual do projeto:

![ModeloConceitual](https://github.com/RodrigoAbdalla/ProjetoLab-AC1-Individual/blob/main/ModeloConceitual.png)

#Enunciado do Projeto:

Desenvolver um sistema para controlar eventos.

Um evento pode ser criado por qualquer usuário administrador. Ao criar um evento o usuário administrador deverá definir a quantidade de ingressos gratuitos, quantidade de ingressos pagos, valor do ingresso pago.

Um evento poderá ser realizado em um ou mais lugares. E um lugar poderá ser usado por zero ou mais eventos, porém em datas e horários diferentes. Ao alterar o local ou data de um evento, verificar se isso é possível. Não será possível alterar as informações do evento após a sua realização. Um evento que já tenha ingressos vendidos não poderá ser removido. Um local não poderá ser removido se ele já foi usado por um evento.

Um participante poderá fazer a sua inscrição (adquirir ingressos) em qualquer evento cadastrado, respeitando o limite de participantes de cada evento ou a data de realização do evento. Não é possível adquirir um ingresso de um evento que ocorreu no passado.

Existem dois tipos de ingressos: Pago e Gratuito. Um ingresso pago deverá ter o valor pago no momento da compra. O valor do ingresso pago pode ser alterado a qualquer momento. Porém os valores dos ingressos pagos já vendidos não deverão ser alterados. Armazenar a data de venda dos ingressos e caso um ingresso seja removido/devolvido, esse poderá ser vendido novamente para o evento. O valor do ingresso pago entrará como saldo para do participante que comprou o ingresso. Não será possível remover/devolver um ingresso a partir data de início do evento.

---------------------------------------------------------------

## Base das Rotas do Sistema:

---

GET, POST, DELETE e PUT

/admins

Manutenção de usuário admins.

---

GET, POST, DELETE e PUT

/attendees

Manutenção de usuários participantes.

---

GET, POST, DELETE e PUT

/places

Manutenção de local de eventos.

---

GET, POST, DELETE e PUT

/events

Manutenção de eventos:

Ao criar um evento passar o id do usuário administrador no corpo da resquisição.

---

POST e DELETE

/events/{id}/places/{id}

Associar ou remover um local a um evento.

Validar evento e local.

Validar disponibilidade.

---

GET

/events/{id}/tickets

Devolve a lista de ingressos de um evento, tendo o tipo do ingresso e nome dos participantes.

Devolve o total de ingressos pagos, total de ingressos gratuitos, total de ingressos pagos já vendidos, total de ingressos gratuitos já vendidos.

---

POST e DELETE

/events/{id}/tickets

Vende um ingresso para um evento.

Passar o id do participante no corpo da requisição.

Passar se o ingresso é pago ou gratuito no corpo da requisição.

Na devolução de um ingresso pago, criar saldo para o participante.

Validar se é possível fazer a venda.

---

## Abaixo segue exemplos de JSON para colocar no Postman:
 
### POST 

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

### PUT

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
### DELETE

Delete - /events/{id}/tickets 
{
            "type": "payed",
            "idAttendee": 7
}

 

 
