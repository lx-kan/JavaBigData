-- 创建库
CREATE database nybikedb default charset utf8;


USE nybikedb;

-- 创建t_hour_trip_count
CREATE TABLE t_hour_trip_count (
startdata char(10),
hour INT,
count INT
);

-- 创建t_hour_trip_count
CREATE TABLE t_hour_gender_trip_count (
startdata char(10),
hour INT,
gender INT,
count INT
);