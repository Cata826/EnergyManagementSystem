
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import LoginForm from './components/LoginForm';
import AdminPanel from './components/AdminPanel';
import PersonTable from './components/PersonTable';
import DeviceTable from './components/DeviceTable';
import AdminDevice from './components/AdminDevice';
import PersonDevice from './components/PersonDevice';
import ProtectedRoute from './components/ProtectedRoute';
import EnergyConsumptionChart from './components/EnergyConsumptionChart';
import PersonReferenceTable from './components/PersonReferenceTable';
import BrokerTable from './components/BrokerTable';
import ConsumerTable from './components/ConsumerTable';
import UserChat from './components/UserChat';
import Chat from './components/Chat';
import AdminChat from './components/AdminChat';
function App() {
  return (
      <div className="App">
          <Routes>
              <Route path="/" element={<LoginForm />} />
              <Route path="/admin-chat" element={<AdminChat />} />
              <Route path="/user-chat" element={<UserChat />} />
              <Route path="/chat" element={<Chat />} />
                <Route 
                  path="/person-reference" 
                  element={
                      <ProtectedRoute element={<PersonReferenceTable />} requiresAdmin={true} />
                  } 
                />
                <Route 
                  path="/chart" 
                  element={
                      <ProtectedRoute element={<EnergyConsumptionChart />} requiresNonAdmin={true} />
                  } 
              />
                <Route 
                  path="/consumer" 
                  element={
                      <ProtectedRoute element={<ConsumerTable />} requiresAdmin={true} />
                  } 
              />
                <Route 
                  path="/broker" 
                  element={
                      <ProtectedRoute element={<BrokerTable />} requiresAdmin={true} />
                  } 
              />
              
              <Route 
                  path="/admin" 
                  element={
                      <ProtectedRoute element={<AdminPanel />} requiresAdmin={true} />
                  } 
              />
              <Route 
                  path="/persons" 
                  element={
                      <ProtectedRoute element={<PersonTable />} requiresAdmin={true} />
                  } 
              />
              <Route 
                  path="/devices" 
                  element={
                      <ProtectedRoute element={<DeviceTable />} requiresAdmin={true} />
                  } 
              />
              <Route 
                  path="/admindevice" 
                  element={
                      <ProtectedRoute element={<AdminDevice />} requiresAdmin={true} />
                  } 
              />

              <Route 
                  path="/persondevice" 
                  element={
                      <ProtectedRoute element={<PersonDevice />} requiresNonAdmin={true} />
                  } 
              />
          </Routes>
      </div>
  );
}

export default App;

