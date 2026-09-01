import type { HealthStatus } from "./healthStatus";

export interface HealthCheck{
    id: number;
    statusCode: number;
    status: HealthStatus;
    responseTime: number;
    checkedAt: Date;
}