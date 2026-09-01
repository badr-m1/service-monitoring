import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import ServiceDetails from './pages/ServiceDetails';

function App() {

  return (
    <>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/services/:id" element={<ServiceDetails />} />
      </Routes>
    </BrowserRouter>
    </>
  )
}

export default App
