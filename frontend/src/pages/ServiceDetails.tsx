import { useParams } from 'react-router-dom';
import type { ServiceHistory } from '../Types/serviceHistory';
import ServiceHistoryCard from '../components/ServiceHistoryCard';

function ServiceDetails() {
    const { id } = useParams();
    const payload: any = {"id":1,"name":"Google","url":"https://www.google.com","healthChecks":[{"id":139,"status":"UP","statusCode":200,"responseTime":161,"checkedAt":"2026-09-01T18:48:53.788774Z"},{"id":138,"status":"UP","statusCode":200,"responseTime":835,"checkedAt":"2026-09-01T18:47:53.885629Z"},{"id":135,"status":"UP","statusCode":200,"responseTime":192,"checkedAt":"2026-09-01T18:39:03.386848Z"},{"id":134,"status":"UP","statusCode":200,"responseTime":769,"checkedAt":"2026-09-01T18:38:03.457620Z"},{"id":101,"status":"UP","statusCode":200,"responseTime":638,"checkedAt":"2026-08-31T21:44:08.535361Z"},{"id":68,"status":"UP","statusCode":200,"responseTime":779,"checkedAt":"2026-08-31T21:40:52.702098Z"}]}
    
    const serviceHistory: ServiceHistory = {
        ...payload,
        healthChecks: payload.healthChecks.map((healthCheck) => ({
            ...healthCheck,
            checkedAt: new Date(healthCheck.checkedAt),
        })),
    };
    
    return (
    <div className="min-h-screen bg-gray-100 p-8">
      <div className="mx-auto max-w-6xl">
        <h1 className="mb-6 text-3xl font-bold text-gray-900">
          {serviceHistory.name}
        </h1>
        <ServiceHistoryCard serviceHistory={serviceHistory}/>
      </div>
    </div>
  );
}

export default ServiceDetails;