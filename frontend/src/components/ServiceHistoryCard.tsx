import type { ServiceHistory } from "../Types/serviceHistory";

interface ServiceHistoryCardProps {
  serviceHistory: ServiceHistory;
}

function ServiceHistoryCard({ serviceHistory }: ServiceHistoryCardProps) {
  return (
    <div className="rounded-xl border border-gray-200 bg-white shadow-sm">
      <div className="border-b border-gray-200 p-5">
        <h2 className="text-lg font-semibold text-gray-900">
          {serviceHistory.name}
        </h2>

        <p className="text-sm text-gray-500">
          {serviceHistory.url}
        </p>
      </div>

      <div className="divide-y divide-gray-100">
        {serviceHistory.healthChecks.map((healthCheck) => (
          <div
            key={healthCheck.id}
            className="grid grid-cols-4 gap-4 px-5 py-4 text-sm"
          >
            <span className="font-medium">
              {healthCheck.status}
            </span>

            <span className="text-gray-600">
              {healthCheck.statusCode}
            </span>

            <span className="text-gray-600">
              {healthCheck.responseTime} ms
            </span>

            <span className="text-gray-500">
              {healthCheck.checkedAt.toLocaleString()}
            </span>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ServiceHistoryCard;