import type { ServiceSummary } from "../Types/serviceSummary";
import type { HealthCheck } from "../Types/healthCheck";
import type { HealthStatus } from "../Types/healthStatus";

interface ServiceCardProps {
  serviceSummary: ServiceSummary;
}


function ServiceSummaryCard({ serviceSummary }: ServiceCardProps) {
  return (
    <div className="rounded-xl border border-gray-200 bg-white p-5 shadow-sm">
      <div className="mb-4">
        <h2 className="text-lg font-semibold text-gray-900">
          {serviceSummary.name}
        </h2>

        <p className="text-sm text-gray-500">
          {serviceSummary.url}
        </p>
      </div>

      <div className="grid grid-cols-2 gap-3 text-sm">
        <span className="text-gray-500">Status</span>
        <span className="font-medium">{serviceSummary.healthCheck.status}</span>

        <span className="text-gray-500">Status Code</span>
        <span className="font-medium">{serviceSummary.healthCheck.statusCode}</span>

        <span className="text-gray-500">Response Time</span>
        <span className="font-medium">
          {serviceSummary.healthCheck.responseTime} ms
        </span>

        <span className="text-gray-500">Checked At</span>
        <span className="font-medium">
          {serviceSummary.healthCheck.checkedAt.toLocaleString()}
        </span>
      </div>
    </div>
  )
}


export default ServiceSummaryCard