import { useEffect, useState } from 'react';
import type { ServiceSummary } from '../Types/serviceSummary';
import { getServices } from '../api/serviceApi';
import ServiceSummaryCard from '../components/ServiceSummaryCard';
import { Link } from 'react-router-dom';

function Dashboard() {
  const [services, setServices] = useState<ServiceSummary[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        getServices()
        .then(setServices)
        .catch(() => setError('Failed to load services'))
        .finally(() => setLoading(false));
    }, []);

    if (loading) {
        return <div className="p-6">Loading...</div>;
    }

    if (error) {
        return <div className="p-6 text-red-600">{error}</div>;
    }


  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <div className="mx-auto max-w-6xl">
        <h1 className="mb-6 text-3xl font-bold text-gray-900">
          Service Monitor
        </h1>

        <div className="space-y-3">
          {services.map((service: ServiceSummary) => (
            <Link to={`/services/${service.id}`}>
                <ServiceSummaryCard
                    key={service.id}
                    serviceSummary={service}
                />
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Dashboard;