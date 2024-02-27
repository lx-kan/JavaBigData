-- 创建库
CREATE database nybikedb default charset utf8;


USE nybikedb;

-- 创建t_hour_trip_count
CREATE TABLE t_hour_trip_count (
startdata char(10),
hour INT,
count INT
);

-- 创建t_hour_gender_trip_count
CREATE TABLE t_hour_gender_trip_count (
startdata char(10),
hour INT,
gender INT,
count INT
);

create table t_hour_top5_start_top3_end(
startdate char(10),
hour int,
start_station_id int,
end_station_id int,
count int,
start_rank int,
end_rank int
);

create table t_station_info(
station_id int,
station_name varchar(255),
station_latitude double,
station_longitude double
);