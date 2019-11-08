insert into trader(id,name,wealth,value,dtype) values (0,'comp0',10,4,'Company');
insert into trader(id,name,wealth,value,dtype) values (1,'comp1',8,6,'Company');
insert into trader(id,name,wealth,value,dtype) values (2,'comp2',6,8,'Company');
insert into trader(id,name,wealth,value,dtype) values (3,'comp3',4,10,'Company');
insert into trader(id,name,wealth,value,dtype) values (4,'comp3',2,12,'Person');
insert into trader(id,name,wealth,value,dtype) values (5,'comp3',1,14,'Person');
insert into share_(id,trader_id,company_id,test) values (0,0,0,10);
insert into share_(id,trader_id,company_id,test) values (1,4,1,8);
insert into share_(id,trader_id,company_id,test) values (2,5,2,6);
insert into share_(id,trader_id,company_id,test) values (3,3,3,4);
insert into share_(id,trader_id,company_id,test) values (4,5,3,12);
insert into offer(id,share_id,trader_id,cost) values (0,0,0,40);
insert into offer(id,share_id,trader_id,cost) values (1,1,4,50);
insert into offer(id,share_id,trader_id,cost) values (2,2,5,60);
insert into offer(id,share_id,trader_id,cost) values (3,3,3,70);

