import type { HealthCheck } from "./healthCheck";

export interface ServiceSummary{
    id: number;
    name: string;
    url: string;
    healthCheck: HealthCheck;
}