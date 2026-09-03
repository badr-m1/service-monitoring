import type { ServiceSummary } from "../Types/serviceSummary";
import type { ServiceHistory } from "../Types/serviceHistory";
import type { ServiceRequest } from "../Types/serviceRequest";

const API_URL = "/api/services";

export async function createService(serviceRequest: ServiceRequest): Promise<ServiceSummary> {
    const response = await fetch(`${API_URL}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(serviceRequest),
    });

    if (!response.ok) {
        throw new Error("Failed to create service");
    }

    return response.json();
}

export async function updateService(id: number, name: string): Promise<ServiceSummary> {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(name),
    });

    if (!response.ok) {
        throw new Error("Failed to update services");
    }

    return response.json();
}

export async function getAllServices(): Promise<ServiceSummary[]> {
    const response = await fetch(`${API_URL}`);

    if (!response.ok) {
        throw new Error("Failed to fetch services");
    }

    return response.json();
}

export async function getService(id: number): Promise<ServiceSummary> {
    const response = await fetch(`${API_URL}/${id}`);

    if (!response.ok) {
        throw new Error("Failed to fetch service summary");
    }

    return response.json();
}

export async function getServiceHistory(id: number, startTime: Date, endTime: Date): Promise<ServiceHistory> {
    const startTimeStr = startTime.toISOString();
    const endTimeStr = endTime.toISOString();

    const response = await fetch(`${API_URL}/${id}/history??startTime=${startTimeStr}&endTime=${endTimeStr}`);

    if (!response.ok) {
        throw new Error("Failed to fetch service history");
    }

    return response.json();
}

export async function deleteService(id: number): Promise<void> {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
    });

    if (!response.ok) {
        throw new Error("Failed to delete service");
    }
}


export async function getUpdateInterval(): Promise<number> {
    const response = await fetch(`${API_URL}/config`);

    if (!response.ok) {
        throw new Error("Failed to retrive update interval");
    }

    return response.json()
}