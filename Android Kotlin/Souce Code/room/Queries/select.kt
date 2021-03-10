select * from categories
select * from products
select * from users

//where
select * from categories where id=2

//where + and
select * from products where categoryId>1 and price>1000

//order by
select * from categories order by id asc
select * from categories order by id desc


//order by vclause
select id,name from categories order by name asc


//order by multiple condi
select * from categories order by id desc, name asc

select * from categories order by 1 desc