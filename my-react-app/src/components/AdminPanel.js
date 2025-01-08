
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './AdminPanel.css';

const AdminPanel = () => {
    const navigate = useNavigate();

    const viewTablePersons = () => {
        navigate('/persons');
    };
    const viewTableDevices = () => {
        navigate('/devices');
    };
    const viewAssociations = () => {
        navigate('/admindevice');
    };
    const viewPersonReference = () => {
        navigate('/person-reference');
    };
    const viewBroker = () => {
        navigate('/broker');
    };
    const viewConsumer = () => {
        navigate('/consumer');
    };

    const chatBox = () => {
        navigate('/chat');
    };

    return (
        <div className="admin-panel">
            <h2>Admin Panel</h2>
            <button onClick={viewTablePersons}>View Users</button>
            <button onClick={viewTableDevices}>View Devices</button>
            <button onClick={viewAssociations}>View Devices per user </button>
            <button onClick={viewPersonReference}>View Person Reference </button>
            <button onClick={viewBroker}>View Broker </button>
            <button onClick={viewConsumer}>View Consumer </button>
            <button onClick={chatBox}>CHAT </button>
         
        </div>
    );
};

export default AdminPanel;
