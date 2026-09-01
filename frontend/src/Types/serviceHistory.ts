import type { HealthCheck } from "./healthCheck";

export interface ServiceHistory {
    id: number;
    name: string;
    url: string;
    healthChecks: HealthCheck[];
}