CREATE TABLE health_checks (
    id BIGSERIAL PRIMARY KEY,
    endpoint_id BIGINT NOT NULL REFERENCES monitored_endpoints(id),
    status VARCHAR(20) NOT NULL,
    status_code INT NOT NULL,
    response_time BIGINT NOT NULL,
    checked_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_health_checks_endpoint_checked_at
    ON health_checks (endpoint_id, checked_at);