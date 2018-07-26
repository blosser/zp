/// часть 1



INSERT INTO `zp_shop`
(`ID`, `PARENT_ID`, `NAME`, `CODE`) 
VALUE (1,null,'Пятерочка',null);

INSERT INTO `zp_owner`
(`ID`, `NAME`) 
VALUE (1, 'Райт Плэйс Инвест');

INSERT INTO `zp_owner`
(`ID`, `NAME`) 
VALUE (2, 'Партнер');

INSERT INTO `zp_batch`
(`ID`, `NAME`, `PERIOD`) 
VALUE (1, 'Исходные 12.2010', str_to_date('01.12.2010','%d.%m.%Y'));
INSERT INTO `zp_batch`
(`ID`, `NAME`, `PERIOD`) 
VALUE (2, 'Исходные 01.2011', str_to_date('01.01.2011','%d.%m.%Y'));
INSERT INTO `zp_batch`
(`ID`, `NAME`, `PERIOD`) 
VALUE (3, 'Исходные 02.2011', str_to_date('01.02.2011','%d.%m.%Y'));
INSERT INTO `zp_batch`
(`ID`, `NAME`, `PERIOD`) 
VALUE (4, 'Исходные 03.2011', str_to_date('01.03.2011','%d.%m.%Y'));
INSERT INTO `zp_batch`
(`ID`, `NAME`, `PERIOD`) 
VALUE (5, 'Исходные 04.2011', str_to_date('01.04.2011','%d.%m.%Y'));

INSERT INTO `zp_attr`
(`ID`, `NAME`) 
VALUE (1, 'совместитель');
INSERT INTO `zp_attr`
(`ID`, `NAME`) 
VALUE (2, 'за смену');
INSERT INTO `zp_attr`
(`ID`, `NAME`) 
VALUE (3, 'за 20 и более');

INSERT INTO `zp_attr_value`
(`ID`, `ATTR_ID`, `SD`, `FD`, `VALUE`) 
VALUE (1, 1, str_to_date('01.12.2010','%d.%m.%Y'), str_to_date('31.12.2030','%d.%m.%Y'), 1050);

INSERT INTO `zp_attr_value`
(`ID`, `ATTR_ID`, `SD`, `FD`, `VALUE`) 
VALUE (2, 2, str_to_date('01.12.2010','%d.%m.%Y'), str_to_date('31.12.2030','%d.%m.%Y'), 1100);

INSERT INTO `zp_attr_value`
(`ID`, `ATTR_ID`, `SD`, `FD`, `VALUE`) 
VALUE (3, 3, str_to_date('01.12.2010','%d.%m.%Y'), str_to_date('31.12.2030','%d.%m.%Y'), 1150);

INSERT INTO zp_client
(`NAME`,`LOGIN`,`PASSWORD`,`ADMIN`) 
VALUE ('admin','admin','1','1');

INSERT INTO zp_client
(`NAME`,`LOGIN`,`PASSWORD`,`ADMIN`) 
VALUE ('evild','evild','1','1');

INSERT INTO zp_client
(`NAME`,`LOGIN`,`PASSWORD`,`ADMIN`) 
VALUE ('zlo','zlo','1','1');

SET @rank=-1;
insert into zp_cal
(`rank`,`d`,`period`)
select @rank:=@rank+1 AS rank,
       date_format(str_to_date('01.01.2010','%d.%m.%Y') + interval @rank day,'%Y-%m-%d') d,
       date_format(date_format(str_to_date('01.01.2010','%d.%m.%Y') + interval @rank day,'%Y-%m-%d') - interval 0 month,'%Y-%m-01') period
from
(select 1 a union all select 2 a union all select 3 a union all select 4 a) a,
(select 1 a union all select 2 a union all select 3 a union all select 4 a) b,
(select 1 a union all select 2 a union all select 3 a union all select 4 a) c,
(select 1 a union all select 2 a union all select 3 a union all select 4 a) d,
(select 1 a union all select 2 a union all select 3 a union all select 4 a) e,
(select 1 a union all select 2 a union all select 3 a union all select 4 a) f
limit 2000;


/// часть 2


update zp_man_value set active = 1;
update zp_man_value set batch_id = 1 where date_format(sd - interval 0 month,'%Y-%m-01')=str_to_date('01.12.2010','%d.%m.%Y');
update zp_man_value set batch_id = 2 where date_format(sd - interval 0 month,'%Y-%m-01')=str_to_date('01.01.2011','%d.%m.%Y');
update zp_man_value set batch_id = 3 where date_format(sd - interval 0 month,'%Y-%m-01')=str_to_date('01.02.2011','%d.%m.%Y');
update zp_man_value set batch_id = 4 where date_format(sd - interval 0 month,'%Y-%m-01')=str_to_date('01.03.2011','%d.%m.%Y');
update zp_man_value set batch_id = 5 where date_format(sd - interval 0 month,'%Y-%m-01')=str_to_date('01.04.2011','%d.%m.%Y');
update zp_man_value set value = 1 where value_type = 1;

insert into zp_paid
(`PERIOD`, `NAME`)
select distinct sd,batch_name
  from zp_man_value
 where batch_name is not null
 order by sd, batch_name;

INSERT INTO `zp_man_paid`
(`MAN_ID`, `PAID_ID`, `COMMENT`) 
select man_id,
       (select id from zp_paid p where p.period = v.sd and p.name = v.batch_name) paid_id,
       comment
  from zp_man_value v
 where batch_name is not null
 order by sd, batch_name;

insert into zp_shop
(`PARENT_ID`, `CODE`)
select distinct 1, shop_code
  from zp_man_value
 where shop_code is not null
 order by cast(shop_code as unsigned);

update zp_man_value v
set shop_id = (select id from zp_shop s where s.code = v.shop_code)
where shop_code is not null;

delete from zp_man_value where value_type = 5;

alter table zp_man_value drop column shop_code;
alter table zp_man_value drop column batch_name;
alter table zp_man_value drop column comment;