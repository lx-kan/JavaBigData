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

--统计每天每小时不同性别、不同类型、不同年龄的用户骑行数量
create table bike_dw.t_hour_station_gender_usertype_age_trip_count(
startdate string,
hour int,
start_station_id int,
end_station_id int,
gender int,
usertype string,
age int,
count int
)ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';

--编写查询语句
insert into bike_dw.t_hour_station_gender_usertype_age_trip_count
select
substr(starttime,1,10) as startdate,
hour(starttime) as hour,
start_station_id,
end_station_id,
gender,
usertype,
(2019-birth_year) as age,
count(*) as count
from bike_dw.t_tripdata
where month(starttime) = 9 or month(starttime) = 10
group by substr(starttime,1,10),hour(starttime),start_station_id,end_station_id,gender,usertype,(2019-birth_year);

--统计每天每小时不同性别、不同类型、不同年龄的用户骑行数量
create table bike_dw.t_hour_station_trip_count(
startdate string,
hour int,
start_station_id int,
end_station_id int,
count int
)ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';

insert into bike_dw.t_hour_station_trip_count
select
startdate,
hour,
start_station_id,
end_station_id,
sum(count) as count
from bike_dw.t_hour_station_gender_usertype_age_trip_count
where month(startdate) = 9 or month(startdate) = 10
group by startdate,hour,start_station_id,end_station_id;

-- 需求:统计每天每小时前五个热门起始站点对应的的前五个热门终止站点
-- 统计每天每小时前五个热门起始站点对应的id
-- 创建结果表
create table bike_dw.t_hour_station_top5_start_station_id(
startdate string,
hour int,
start_station_id int,
sumCount int,
rank int
)ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';

-- 统计数据并将结果写入表
insert into bike_dw.t_hour_station_top5_start_station_id
select t2.startdate,t2.hour,t2.start_station_id,t2.sumCount,t2.rowNum as rank
from
(select t1.startdate,t1.hour,t1.start_station_id,t1.sumCount,
row_number() over(partition by t1.startdate,t1.hour order by t1.sumCount desc) as rowNum
from
(select startdate,hour,start_station_id,sum(count) as sumCount
from bike_dw.t_hour_station_trip_count
group by startdate,hour,start_station_id) as t1) as t2
where t2.rowNum <6;

--然后统计每个人们起始站点对应的前三个热门终止站点、
--创建结果表
create table bike_dw.t_hour_start_top3_end_station_id(
startdate string,
hour int,
start_station_id int,
end_station_id int,
count int,
rank int
)ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';


insert into bike_dw.t_hour_start_top3_end_station_id
select * from
(select startdate,hour,start_station_id,end_station_id,Count,
row_number() over(partition by startdate,hour,start_station_id order by Count desc) as rowNum
from bike_dw.t_hour_station_trip_count) as t1
where t1.rowNum <4;

--最后统计每小时前五个热门起始站点对应的前三个热门终止站点ID
--创建结果表
create table bike_dw.t_hour_top5_start_top3_end(
startdate string,
hour int,
start_station_id int,
end_station_id int,
count int,
start_rank int,
end_rank int
)ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';

insert into bike_dw.t_hour_top5_start_top3_end
select t2.startdate,t2.hour,t2.start_station_id,t2.end_station_id,t2.count,t1.rank,t2.rank
from bike_dw.t_hour_station_top5_start_station_id t1
inner join bike_dw.t_hour_start_top3_end_station_id t2
on t1.startdate = t2.startdate and t1.hour = t2.hour and t1.start_station_id = t2.start_station_id;

--统计站点的基础信息
--创建结果表
create table bike_dw.t_station_info(
station_id int,
station_name string,
station_latitude double,
station_longitude double
)ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';

--编写查询语句
insert into bike_dw.t_station_info
select distinct
start_station_id,start_station_name,start_station_latitude,start_station_longitude
from bike_dw.t_tripdata;

-- 在终端中使用merge into前应该先执行如下命令
set hive.support.concurrency = true;
set hive.enforce.bucketing = true;
set hive.exec.dynamic.partition.mode = nonstrict;
set hive.txn.manager = org.apache.hadoop.hive.ql.lockmgr.DbTxnManager;
set hive.compactor.initiator.on = true;
set hive.compactor.worker.threads = 1;
set hive.auto.convert.join=false;
set hive.merge.cardinality.check=false;

-- 使用merge into 实现无则插入，有则不处理
-- 新增了15行
MERGE INTO bike_dw.t_station_info AS t1 USING
(select distinct
end_station_id, end_station_name, end_station_latitude, end_station_longitude
from bike_dw.t_tripdata) AS t2
ON t1.station_id=t2.end_station_id
WHEN NOT MATCHED THEN INSERT VALUES(
t2.end_station_id, t2.end_station_name, t2.end_station_latitude, t2.end_station_longitude);



