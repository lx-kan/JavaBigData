create table t_tripdata(
tripduration int,
starttime string,
stoptime string,
start_station_id int,
start_station_name string,
start_station_latitude double,
start_station_longitude double,
end_station_id int,
end_station_name string,
end_station_latitude double,
end_station_longitude double,
bikeid int,
usertype string,
birth_year int,
gender int
)ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';
load data inpath '/upload/201909bikedata.csv'
overwrite into table t_tripdata;


insert into t_year_month_day_trip_count
select year(starttime) as year,month(starttime) as month,day(starttime) as day, count(*)
from t_tripdata
group by year(starttime),month(starttime),day(starttime)
order by day;


create table t_year_month_day_trip_count(
year string,
month string,
day string,
count int
)ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';

--创建dw库
create database bike_dw;

create table bike_dw.t_tripdata(
tripduration int,
starttime string,
stoptime string,
start_station_id int,
start_station_name string,
start_station_latitude double,
start_station_longitude double,
end_station_id int,
end_station_name string,
end_station_latitude double,
end_station_longitude double,
bikeid int,
usertype string,
birth_year int,
gender int
)ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';

--从bike_ods.t_tripdata表中导入数据到bike_dw.t_tripdata表
insert into bike_dw.t_tripdata
select * from bike_ods.t_tripdata
where tripduration <= 3636
and (2019-birth_year) <= 80
and month(starttime) = 9;

--统计每天每小时不同性别、不同类型、不同年龄的用户骑行数量
create table bike_dw.t_hour_gender_usertype_age_trip_count(
startdata string,
hour int,
gender int,
usertype string,
age int,
count int
)ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';

--编写查询语句
insert into bike_dw.t_hour_gender_usertype_age_trip_count
select
substr(starttime,1,10) as startdata,
hour(starttime) as hour,
gender,
usertype,
(2019-birth_year) as age,
count(*) as count
from bike_dw.t_tripdata
where month(starttime) = 10
group by substr(starttime,1,10),hour(starttime),gender,usertype,(2019-birth_year);

--统计每天每小时的用户骑行数量
create table bike_dw.t_hour_trip_count(
startdata string,
hour int,
count int
)ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';

--编写查询语句
insert into bike_dw.t_hour_trip_count
select startdata, hour, sum(count) as count
from bike_dw.t_hour_gender_usertype_age_trip_count
where month(startdata) = 10
group by startdata, hour;

--统计每天每小时不同性别、的用户骑行数量
create table bike_dw.t_hour_gender_trip_count(
startdata string,
hour int,
gender int,
count int
)ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';

--编写查询语句
insert into bike_dw.t_hour_gender_trip_count
select startdata, hour, gender, sum(count) as count
from bike_dw.t_hour_gender_usertype_age_trip_count
where month(startdata) = 10
group by startdata, hour, gender;


--查询t_tripdata的最后两行数据
select * from t_tripdata
order by starttime desc
limit 2;

select * from t_hour_trip_count
order by startdata desc
limit 2;


