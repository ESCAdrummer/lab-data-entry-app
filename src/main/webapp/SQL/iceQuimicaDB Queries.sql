-- drop database icequimica;
use icequimica; 

update user set isAdmin = 1;

select * from user;
select * from products;
select * from test;
select * from qualitySet;
select * from testResults;

insert into products values (4,"Product 4");
insert into test values (3, "Weight", "gr");
insert into qualityset values (2,3);

select t.id, t.name, t.units from qualitySet qs 
join test t on qs.test_id = t.id
join products p on qs.products_id = p.id where p.id = 1 order by name; 

insert into testResults (date, user_id, products_id, test_id, value) values ('2022-12-28', 1, 1, 1, 15.24);

select name as productName from testResults TR 
join products P on TR.products_id = P.id 
group by productName order by productName;

select Concat(U.firstName," ", U.lastName) as fullName from testResults TR
join user U on TR.user_id = U.id
group by fullName order by fullName;

select TR.id as id, TR.date as date, U.username as username, P.name as product, T.name as test, value from testResults TR 
join user U on TR.user_id = U.id
join products P on TR.products_id = P.id
join test T on TR.test_id = T.id
where date between '2022-12-27' and '2022-12-31' 
order by date desc, product asc, id asc, username asc;