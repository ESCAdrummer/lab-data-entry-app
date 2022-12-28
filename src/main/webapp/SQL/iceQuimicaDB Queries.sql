drop database icequimica;
use icequimica; 

update user set isAdmin = 1;

select * from user;
select * from products;
select * from test;
select * from qualityset;

insert into products values (4,"Product 4");
insert into test values (3, "Weight", "gr");
insert into qualityset values (2,3);

select t.id, t.name, t.units from qualityset qs 
join test t on qs.test_id = t.id
join products p on qs.products_id = p.id where p.id = 1 order by name; 

insert into testResults (date, user_id, products_id, test_id, value) values ('2022-12-28', 1, 1, 1, 15.24);

