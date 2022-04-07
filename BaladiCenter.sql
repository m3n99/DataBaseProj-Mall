drop  database BaladiCenter;
create database BaladiCenter;
use BaladiCenter;

CREATE TABLE Payment(
PId int primary key,
PAdate varchar(32),
typeOFpa varchar(40)
);

Create table Bill (
BiId int primary key, 
StartDate varchar(20),
EndDate  varchar(20)
);

Create table Tenant(
TaSsn int  primary key,
TaName   varchar(30),
PhoneNum varchar(15),
BirthDe  varchar(20),
AddressOfBank  varchar(30),
TypeOfShop varchar(30),
BankAccount varchar(30)
);
select * from roomre;
Create table Bill_Line (
Bill_id int primary key, 
water int ,
parking int ,
electricity int ,
rent_room int ,
maintenance int 
);

Create table RoomRe (
Rid int primary key,
SpaceOfRoom int, 
FloorNum int, 
ParkingNum int ,
 TaSsn int,
 S_Date varchar(20),
 E_Date varchar(20),
Foreign key (TaSsn) references Tenant(TaSsn)
);

select * from bill;
Create Table Employee( 
EmSsn int  primary key,
EmName   varchar(30),
PhoneNum varchar(15),
BirthDe  varchar(20),
Address  varchar(20),
Timework   int,
Salary int,
EmployeeType varchar(40) not null ,
Rid int ,
Foreign key (Rid) references RoomRe(Rid)
);

CREATE TABLE EmWorkRo(
EmSsn int ,
Rid int ,
primary key(EmSsn,Rid),
Foreign key (EmSsn) references Employee(EmSsn),
Foreign key (Rid) references RoomRe(Rid)
);

CREATE TABLE EmGeneratesPayment(
EmSsn int ,
PId int ,
primary key(EmSsn,PId),
Foreign key (EmSsn) references Employee(EmSsn),
Foreign key (PId) references Payment(PId)
);

CREATE TABLE EmRecivablesPayment(
EmSsn int ,
PId int ,
primary key(EmSsn,PId),
Foreign key (EmSsn) references Employee(EmSsn),
Foreign key (PId) references Payment(PId)
);

Create table GenerateB(
Bill_id int ,
BiId int ,
primary key (Bill_id, BiId),
Foreign key (Bill_id) References Bill_Line(Bill_id), 
Foreign key (BiId) references Bill(BiId)
);

Create table Pay(
PId int ,
TaSsn int ,
BiId int ,
primary key (PId,TaSsn,BiId),
Foreign key (PId) references Payment(PId),
Foreign key (TaSsn) references Tenant(TaSsn),
Foreign key (BiId) references Bill(BiId)
);

INSERT INTO Payment VALUES 
(112,'2020-4-15','Kash'),
(113,'2020-7-15','Bank'),
(114,'2020-8-15','Check'),
(115,'2020-10-15','Kash'),
(116,'2020-11-15','Bank');
UPDATE Payment set PAdate = 'sadfdg' WHERE PId = 112;
select * from Payment;
INSERT INTO Bill VALUES 
(1,'2020-5-1','2020-6-15'),
(2,'2010-5-15','2009-8-30'),
(3,'2007-5-4','2009-5-16'),
(4,'2009-5-13','2009-8-22'),
(5,'2013-8-1','2013-8-30');

"INSERT INTO Bill VALUES 
(1,'2020-5-1','2020-6-15');"

INSERT INTO Tenant VALUES 
(1180000,'Kim Namjoon', '056-1234567', '1996-5-3','  Ramallah, Ain Misbah ','Shose','00253-263-1565'),
(1182223,'Kim Seokjin', '052-1596325', '1989-3-21',' Al Ahliya Branch - Ramallah','Cafe','12533-229-1167'),
(1181110,'Jung Hoseok ', '059-0024753', '1977-3-25',' Al Irsal Street Branch','Resturant','00145-981-2371'),
(1180969,'Suga Yoongi', '056-4569871', '1992-2-12',' Al Irsal Street Branch','Gift','00245-9111-1163'),
(1180220,'Kim Taehyung', '054-1258963', '1988-8-17',' Ramallah,Al Manara','lawyers Office','01256-134-0065');


INSERT INTO Tenant VALUES 
(1181564,'Kim Namjoon', '056-1234567', '1996-5-3','  Ramallah, Ain Misbah ','Shose','00253-263-1565');
select * from tenant;
delete from tenant where TaSsn= 1181564;

INSERT INTO Bill_Line VALUES 
(1,100,200,500,2000, 100),
(2,300,300,300,1000, 100),
(3,20,100,200,1500, 100),
(4,50,100,100,1250, 100),
(5,120,100,250,2500, 100);

INSERT INTO RoomRe VALUES 
(1,10,1, 1,1180000 ,'2019-12-16','2020-4-5'),
(2,20,1, 1,1182223 ,'2020-4-20','2020-2-20'),
(4,100,2, 2,1180969,'2020-2-1','2020-2-10'),
(5,78,3,2,1180220 ,'2020-8-13','2020-9-13');

INSERT INTO RoomRe VALUES 
(6,10,1, 1,1180000 ,'2019-12-16','2020-4-5');

INSERT INTO Employee VALUES 
 (1170000,'Mohammad Aborou', '056-9025803', '1999-12-16',' Obere Str. 57',9,2500,'security',1),
 (1172223,'Maria Anders', '052-1593687', '1989-1-20',' 120 Hanover Sq.',8,2500,'security',1),
 (1171110,'Antonio Moreno', '059-933603', '1970-5-7',' Berguvsvägen  8',7,2500,'Accountant',2),
(1170969,'Ahmad Shamasnah', '056-6045823', '1992-1-22',' 24, place Kléber',10,2560,'Repairs',4);




select * from EmWorkRo;
 
INSERT INTO EmWorkRo VALUES 
(1170000,1),
(1172223,1),
(1171110,2),
(1170969,4);

INSERT INTO EmGeneratesPayment VALUES 
(1170000,112),
(1172223,113),
(1171110,114),
(1170969,115);

select * from GenerateB;

INSERT INTO EmRecivablesPayment VALUES 
(1170000,113),
(1172223,112),
(1171110,115),
(1170969,114);

INSERT INTO GenerateB VALUES 
(1,1),
(2,2),
(3,3),
(4,4),
(5,5);


INSERT INTO Pay VALUES 
(112,1180000 ,1),
(113,1182223 ,2),
(116,1181110 ,3),
(114,1180969 ,4),
(115,1181110 ,5);
delete from Pay where PId= 112;

select distinct e.EmName from employee e
where e.emssn in ( select e.emssn from EmGeneratesPayment eg ,employee e
where eg.emssn = e.emssn  and eg.PId in (select p.PId from Payment p
where p.typeofpa = 'bank' and padate = '2020-7-15'));

select distinct t.TaName from Tenant t
 where typeofshop = 'shose' and t.TaSsn in ( select r.tassn from Tenant t, roomre r 
 where t.TaSsn = r.TaSsn and s_date = '2019-12-16'
);

