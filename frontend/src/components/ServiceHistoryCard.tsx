import type { ServiceHistory } from "../Types/serviceHistory";

interface ServiceHistoryCardProps {
  serviceHistory: ServiceHistory;
}

function ServiceHistoryCard({ serviceHistory }: ServiceHistoryCardProps) {
  return (
    <div className="overflow-hidden rounded-xl border border-gray-200 bg-white shadow-sm">
      <div className="border-b border-gray-200 p-5">
        <h2 className="text-lg font-semibold text-gray-900">
          {serviceHistory.name}
        </h2>

        <p className="text-sm text-gray-500">
          {serviceHistory.url}
        </p>
      </div>

      <div className="grid grid-cols-4 gap-4 border-b border-gray-200 bg-gray-50 px-5 py-3 text-xs font-semibold uppercase tracking-wide text-gray-500">
        <span>Status</span>
        <span>Status Code</span>
        <span>Response Time</span>
        <span>Checked At</span>
      </div>

      <div className="divide-y divide-gray-100">
        {serviceHistory.healthChecks.map((healthCheck) => {
          const isUp = healthCheck.status === "UP";

          return (
            <div
              key={healthCheck.id}
              className="grid grid-cols-4 items-center gap-4 px-5 py-4 text-sm hover:bg-gray-50"
            >
              <div>
                <span
                  className={`rounded-full px-3 py-1 text-xs font-semibold ${
                    isUp
                      ? "bg-green-100 text-green-700"
                      : "bg-red-100 text-red-700"
                  }`}
                >
                  {healthCheck.status}
                </span>
              </div>

              <span className="font-medium text-gray-700">
                {healthCheck.statusCode}
              </span>

              <span className="font-medium text-gray-700">
                {healthCheck.responseTime} ms
              </span>

              <span className="text-gray-500">
                {(healthCheck.checkedAt.toLocaleString())}
              </span>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default ServiceHistoryCard;