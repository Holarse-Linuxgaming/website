CREATE TABLE IF NOT EXISTS node_pagevisits (
    id bigserial, -- eigner index, daher anstelle von sequence ein serial, darf wegen partition kein primary key sein
    nodeid integer,
    visitorid varchar(255),
    campaign_keyword varchar(255),
    campaign_name varchar(255),
    httpstatus integer,
    ipaddress varchar(255),
    referer varchar(255),
    searchword varchar(255),
    url varchar(2083),
    useragent varchar(255),
    bot boolean DEFAULT false,
    internal boolean DEFAULT false,
    accessed timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL
) PARTITION BY range(accessed);

---- Index for accesslog
CREATE INDEX IF NOT EXISTS node_pagevisits_idx ON node_pagevisits (accessed);
CREATE INDEX IF NOT EXISTS node_pagevisits_nodeid_idx ON node_pagevisits (nodeid, accessed);

---- Table data for logging (FIXME!)
CREATE TABLE node_pagevisits_2023 PARTITION OF node_pagevisits FOR VALUES FROM ('2023-01-01') TO ('2024-01-01');
CREATE TABLE node_pagevisits_2024 PARTITION OF node_pagevisits FOR VALUES FROM ('2024-01-01') TO ('2025-01-01');
CREATE TABLE node_pagevisits_2025 PARTITION OF node_pagevisits FOR VALUES FROM ('2025-01-01') TO ('2026-01-01');
