import { Link } from "react-router-dom";
import type { ServiceSummary } from "../Types/serviceSummary";
import Popup from "./Popup";
import { useState } from "react";
import UpdateServiceForm from "./UpdateServiceForm";

interface ServiceCardProps {
  serviceSummary: ServiceSummary;
  onUpdate: (id: number, name: string) => void;
  onDelete: (id: number) => void;
}
function formatLastChecked(iso: string): string {
  const diffMs = Date.now() - new Date(iso).getTime();
  const diffSec = Math.round(diffMs / 1000);
  if (diffSec < 60) return `${diffSec}s ago`;
  const diffMin = Math.round(diffSec / 60);
  if (diffMin < 60) return `${diffMin}m ago`;
  const diffHr = Math.round(diffMin / 60);
  if (diffHr < 24) return `${diffHr}h ago`;
  return new Date(iso).toLocaleDateString();
}

function ServiceSummaryCard({
  serviceSummary,
  onUpdate,
  onDelete,
}: ServiceCardProps) {
  const [showUpdatePopup, setShowUpdatePopup] = useState(false);

  const isUp = serviceSummary.healthCheck.status === "UP";

  return (
    <div className="rounded-xl border border-gray-200 bg-white p-5 shadow-sm">
      {showUpdatePopup && (
        <Popup
          title="Update service name"
          onClose={() => setShowUpdatePopup(false)}
        >
          <UpdateServiceForm
            onSubmit={(name) => {
              onUpdate(serviceSummary.id, name);
              setShowUpdatePopup(false);
            }}
          />
        </Popup>
      )}

      <div className="mb-5 flex items-start justify-between">
        <Link to={`/services/${serviceSummary.id}`}>
          <div>
            <h2 className="text-lg font-semibold text-gray-900 hover:text-blue-600">
              {serviceSummary.name}
            </h2>

            <p className="text-sm text-gray-500">
              {serviceSummary.url}
            </p>
          </div>
        </Link>

        <span
          className={`rounded-full px-3 py-1 text-xs font-semibold ${
            isUp
              ? "bg-green-100 text-green-700"
              : "bg-red-100 text-red-700"
          }`}
        >
          {serviceSummary.healthCheck.status}
        </span>
      </div>

      <div className="grid grid-cols-3 gap-4 border-t border-gray-100 pt-4">
        <div>
          <p className="text-xs text-gray-500">Status Code</p>
          <p className="mt-1 font-medium text-gray-900">
            {serviceSummary.healthCheck.statusCode}
          </p>
        </div>

        <div>
          <p className="text-xs text-gray-500">Response Time</p>
          <p className="mt-1 font-medium text-gray-900">
            {serviceSummary.healthCheck.responseTime} ms
          </p>
        </div>

        <div>
          <p className="text-xs text-gray-500">Last Checked</p>
          <p className="mt-1 font-medium text-gray-900">
            {formatLastChecked(serviceSummary.healthCheck.checkedAt.toLocaleString())}
          </p>
        </div>
      </div>

      <div className="mt-5 flex justify-end gap-3 border-t border-gray-100 pt-4">
        <button
          onClick={() => setShowUpdatePopup(true)}
          className="rounded-lg border border-gray-300 px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50"
        >
          Change Name
        </button>

        <button
          onClick={() => onDelete(serviceSummary.id)}
          className="rounded-lg bg-red-500 px-4 py-2 text-sm font-medium text-white hover:bg-red-600"
        >
          Remove
        </button>
      </div>
    </div>
  );
}

export default ServiceSummaryCard;