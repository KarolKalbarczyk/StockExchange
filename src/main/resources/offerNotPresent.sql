insert into account_(id,login) values (0,'acc0');
insert into account_(id,login) values (1,'acc1');
insert into account_(id,login) values (2,'acc2');
insert into account_(id,login) values (3,'acc3');
insert into trader(id,name,wealth,value,dtype,account_id) values (3,'comp','4','3','Company',0);
insert into trader(id,name,wealth,dtype,account_id) values (2,'person','60','Person',1);
insert into share_(id,trader_id,company_id,test) values (7,2,3,6);
insert into trader(id,name,wealth,dtype,account_id) values (5,'person2','40','Person',2);
insert into trader(id,name,wealth,value,dtype,account_id) values (6,'comp2','4','3','Company',3);