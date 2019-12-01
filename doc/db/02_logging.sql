-- also holarse-user

-- partitioned table für zugriffsstatistiken
create table logging.accesslog (id bigserial, nodeid bigint, visitorid varchar(255), campaignkeyword varchar(255), campaignname varchar(255), httpstatus int, ipaddress varchar(255), 
                        referer varchar(255), searchword varchar(255), url varchar(2083), useragent varchar(255), bot boolean default false, 
                        accessed timestamp with time zone not null default current_timestamp) partition by range(accessed);

create index on logging.accesslog (accessed);
create index on logging.accesslog(nodeid, accessed);

create table logging.accesslog_2018 partition of logging.accesslog for values from ('2018-01-01') to ('2019-01-01');
create table logging.accesslog_2019 partition of logging.accesslog for values from ('2019-01-01') to ('2020-01-01');
create table logging.accesslog_2020 partition of logging.accesslog for values from ('2020-01-01') to ('2021-01-01');
create table logging.accesslog_2021 partition of logging.accesslog for values from ('2021-01-01') to ('2022-01-01');
create table logging.accesslog_2022 partition of logging.accesslog for values from ('2022-01-01') to ('2023-01-01');
create table logging.accesslog_2023 partition of logging.accesslog for values from ('2023-01-01') to ('2024-01-01');
create table logging.accesslog_2024 partition of logging.accesslog for values from ('2024-01-01') to ('2025-01-01');