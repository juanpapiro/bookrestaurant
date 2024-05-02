insert into tb_restaurants (id,name,type_of_cuisine,capacity,date_create)
       values ('118cd4e6-73fb-43a4-bc36-ed777289c28f','Restaurante da Mama','Italiana',20,'2024-03-31T10:00:00');
insert into tb_address (cd_address,street,number,neighborhood,city,uf,cep,restaurant_id)
       values ('7ca823d9-b52e-4f39-beb9-9e6219b2fc8d','Rua Urca',10,'Jardim São Vicente','Embu das Artes','SP','06826-270','118cd4e6-73fb-43a4-bc36-ed777289c28f');
insert into tb_openinghours (cd_opening_hour,day_of_the_week_code,day_of_the_week,hour_open,hour_close,restaurant_id)
       values ('589be076-6308-47c3-80ca-e0f5a8870835',1,'DOMINGO','10:00:00','22:00:00','118cd4e6-73fb-43a4-bc36-ed777289c28f');