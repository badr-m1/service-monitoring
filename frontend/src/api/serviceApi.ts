import type { ServiceSummary } from "../Types/serviceSummary";
import type { ServiceHistory } from "../Types/serviceHistory";
import type { ServiceRequest } from "../Types/serviceRequest";
import type { Service } from "../Types/Service";

const API_URL = "http://localhost:8080/api";

export async function createService(serviceRequest: ServiceRequest): Promise<Service> {
    const response = await fetch(`${API_URL}/services`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(serviceRequest),
    });
        if (!response.ok) {
        throw new Error("Failed to fetch service");
    }

    return response.json();
}

export async function getServices(): Promise<ServiceSummary[]> {
    const response = await fetch(`${API_URL}/services`);

    if (!response.ok) {
        throw new Error("Failed to fetch services");
    }

    return response.json();
}

export async function getService(id: number): Promise<ServiceSummary> {
    const response = await fetch(`${API_URL}/services/${id}`);

    if (!response.ok) {
        throw new Error("Failed to fetch service");
    }

    return response.json();
}

export async function getServiceHistory(id: number): Promise<ServiceSummary> {
    const response = await fetch(`${API_URL}/services/${id}/history`);

    if (!response.ok) {
        throw new Error("Failed to fetch service");
    }

    return response.json();
}