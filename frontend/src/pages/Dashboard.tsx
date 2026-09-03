import {useEffect, useState } from 'react';
import type { ServiceSummary } from '../Types/serviceSummary';
import { getAllServices, createService, updateService, deleteService, getUpdateInterval } from '../api/serviceApi';
import ServiceSummaryCard from '../components/ServiceSummaryCard';
import Popup from '../components/Popup';
import CreateServiceFrom from '../components/CreateServiceFrom';

function Dashboard() {
  const [services, setServices] = useState<ServiceSummary[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [showCreatePopup, setShowCreatePopup] = useState<boolean>(false);
  const [updateInterval, setUpdateInterval] = useState<number>(5000)

  const handleGetServices = () => {
    getAllServices()
    .then(setServices)
    .catch(() => setError('Failed to load services'))
    .finally(() => setLoading(false));
  }

  const handleCreateService = (name: string, url : string) => {
    createService({ "name": name, "url": url })
      .then((service) => {
        console.log("created", service);
        setServices((services) => [...services, service]);
        setShowCreatePopup(false);
      })
      .catch((error) => {
        console.error("create failed", error);
      });
  }

  const handleUpdateService = (id : number, name : string) => {
    updateService(id, name)
      .then((service) => {
        console.log("updated", service);
        handleGetServices();
      })
      .catch((error) => {
        console.error("update failed", error);
      });
  }

  const handleDeleteService = (id : number) => {
    deleteService(id)
      .then((service) => {
        console.log("deleted", service);
        handleGetServices();
      })
      .catch((error) => {
        console.error("delete failed", error);
      });
  }

  useEffect(() => {
    handleGetServices()
    getUpdateInterval()
    .then((interval) => setUpdateInterval(interval))
    .catch((error) => {
      console.error("unable to retrive update interval", error);
    });
  }, []);
  
  useEffect(() => {
    const id = setInterval(() => {
      handleGetServices();
    }, updateInterval);

    return () => clearInterval(id);
  }, [updateInterval, handleGetServices]);

  if (loading) {
    return <div className="p-6">Loading...</div>;
  }

  if (error) {
    return <div className="p-6 text-red-600">{error}</div>;
  }


  return (
    <div className="min-h-screen bg-gray-100 p-8">
      {
        showCreatePopup && 
        <Popup title='Add a new service' onClose={() => {setShowCreatePopup(false)} }>
          <CreateServiceFrom 
            onSubmit={handleCreateService}
          />
        </Popup>
      }
      <div className="mb-8 flex justify-between imx-auto max-w-6xl">
        <h1 className="text-3xl font-bold text-gray-900">
          Service Monitor
        </h1>

        <button
          onClick={() => {setShowCreatePopup(true)}}
          className="rounded bg-blue-500 px-4 py-2 text-white hover:bg-blue-600"
        >
          Add Service
        </button>

      </div>

      <div className="space-y-3">
        {services.map((service: ServiceSummary) => (
          <ServiceSummaryCard
              key={service.id}
              serviceSummary={service}
              onUpdate={handleUpdateService}
              onDelete={handleDeleteService}
          />
        ))}
      </div>
    </div>
  );
}

export default Dashboard;