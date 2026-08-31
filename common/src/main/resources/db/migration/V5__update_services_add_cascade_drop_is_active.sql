ALTER TABLE services
    RENAME TO monitored_services;

ALTER TABLE monitored_services
    DROP COLUMN is_active;

ALTER TABLE health_checks
    DROP CONSTRAINT health_checks_endpoint_id_fkey;

ALTER TABLE health_checks
    ADD CONSTRAINT health_checks_service_id_fkey
    FOREIGN KEY (service_id)
    REFERENCES monitored_services(id)
    ON DELETE CASCADE;