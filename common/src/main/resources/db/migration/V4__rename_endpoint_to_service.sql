ALTER TABLE monitored_endpoints
    RENAME TO services;

ALTER TABLE health_checks
    RENAME COLUMN endpoint_id TO service_id;

ALTER INDEX idx_health_checks_endpoint_checked_at
    RENAME TO idx_health_checks_service_checked_at;