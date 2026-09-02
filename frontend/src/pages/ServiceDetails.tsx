import { useParams } from "react-router-dom";
import type { ServiceHistory } from "../Types/serviceHistory";
import ServiceHistoryCard from "../components/ServiceHistoryCard";
import { useState, useEffect } from "react";
import { getServiceHistory } from "../api/serviceApi";

function ServiceDetails() {
  const { id } = useParams();

  const [serviceHistory, setServiceHistory] =
    useState<ServiceHistory | null>(null);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!id) {
      setError("Invalid service ID");
      setLoading(false);
      return;
    }

    const serviceId = Number(id);

    if (Number.isNaN(serviceId)) {
      setError("Invalid service ID");
      setLoading(false);
      return;
    }

    const now = new Date();

    const twentyFourHoursAgo = new Date(
      now.getTime() - 24 * 60 * 60 * 1000
    );

    getServiceHistory(serviceId, twentyFourHoursAgo, now)
      .then(setServiceHistory)
      .catch(() => setError("Failed to load service history"))
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) {
    return <div className="p-6">Loading...</div>;
  }

  if (error) {
    return <div className="p-6 text-red-600">{error}</div>;
  }

  if (!serviceHistory) {
    return <div className="p-6">No service history found.</div>;
  }

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <div className="mx-auto max-w-6xl">
        <h1 className="mb-6 text-3xl font-bold text-gray-900">
          Service History
        </h1>

        <ServiceHistoryCard serviceHistory={serviceHistory} />
      </div>
    </div>
  );
}

export default ServiceDetails;