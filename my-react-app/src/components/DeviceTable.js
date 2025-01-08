
// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import { useNavigate } from 'react-router-dom';
// import './DeviceTable.css'; 

// const DeviceTable = () => {
//     const [devices, setDevices] = useState([]);
//     const [persons, setPersons] = useState([]);
//     const [description, setDescription] = useState('');
//     const [address, setAddress] = useState('');
//     const [mhec, setMhec] = useState('');
//     const [personId, setPersonId] = useState('');
//     const [editingDeviceId, setEditingDeviceId] = useState(null);
//     const [error, setError] = useState(null);
//     const navigate = useNavigate();
//     const [configContent, setConfigContent] = useState(''); 
//     const setupAxiosAuthorization = () => {
//         const token = localStorage.getItem('token');
//         if (token) {
//             axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
//         }
//     };
    
//     const fetchDevices = async () => {
//         try {
//             const response = await axios.get('http://localhost:8081/device');
//             // const response = await axios.get('http://devices.localhost/device');
//             setDevices(response.data);
//             console.log(response.data);
//                 response.data.forEach(device => {
//                     console.log(`Device ID: ${device.id}, Person ID: ${device.personReference?.personId}`);
//                 });

//             const personIds = response.data.map(device => device.personReference?.personId);
//             const devicesWithPersonId = response.data.map(device => ({
//                 ...device,
//                 personId: device.personReference?.personId || null,
//             }));        
//             setDevices(devicesWithPersonId);
//             console.log(devicesWithPersonId);     
//             console.log(personIds);
//         } catch (error) {
//             setError(error.message);
//         }
//     };

//     const fetchPersons = async () => {
//         try {
//             // const response = await axios.get('http://users.localhost/person');
//             const response = await axios.get('http://localhost:8080/person');
//             setPersons(response.data);
//         } catch (error) {
//             setError(error.message);
//         }
//     };

//     useEffect(() => {
//         setupAxiosAuthorization();
//         fetchDevices();
//         fetchPersons();
//     }, []);
//     const handleCreateOrUpdate = async (event) => {
//         event.preventDefault();
    
//         const newDeviceData = {
//             description,
//             address,
//             mhec,
//             personReference: { personId }, 
//         };
    
//         const updateDeviceData = { 
//             description,
//             address,
//             mhec,
//         };
    
//         try {
//             if (editingDeviceId) {
//                 await axios.put(`http://localhost:8081/device/${editingDeviceId}`, updateDeviceData);
//                 // await axios.put(`http://devices.localhost/device/${editingDeviceId}`, updateDeviceData);
//             } else {
//                 await axios.post('http://localhost:8081/device', newDeviceData);
//                 // await axios.post('http://devices.localhost/device', newDeviceData);
//             }
//             resetForm();
//             fetchDevices();
//         } catch (error) {
//             setError(error.message);
//         }
//     };
    

//     const handleEdit = (device) => {
//         setEditingDeviceId(device.id);
//         setDescription(device.description);
//         setAddress(device.address);
//         setMhec(device.mhec);
//         setPersonId(device.personId);
//     };

//     const handleDelete = async (id) => {
//         if (window.confirm('Are you sure you want to delete this device?')) {
//             try {
//                 // await axios.delete(`http://devices.localhost/device/${id}`);
//                 await axios.delete(`http://localhost:8081/device/${id}`);
//                 fetchDevices();
//             } catch (error) {
//                 setError(error.message);
//             }
//         }
//     };

//     const resetForm = () => {
//         setDescription('');
//         setAddress('');
//         setMhec('');
//         setPersonId('');
//         setEditingDeviceId(null);
//     };
//     // const generateConfigFile = (deviceId) => {
//     //     const fileContent = `${deviceId}`;
//     //     const blob = new Blob([fileContent], { type: 'text/plain' });
//     //     const link = document.createElement('a');
//     //     link.href = URL.createObjectURL(blob);
//     //     link.download = 'configuration.txt';
//     //     link.click();
//     // };

//     // const generateConfigFile = (deviceId) => {
//     //     
//     //     const updatedContent = `${deviceId}`;
//     //     setConfigContent(updatedContent);

//     //     const blob = new Blob([updatedContent], { type: 'text/plain' });
//     //     const link = document.createElement('a');
//     //     link.href = URL.createObjectURL(blob);
//     //     link.download = 'configuration.txt';
//     //     link.click();
//     // };
//     const updateConfigFile = async (deviceId) => {
//         try {
//             const [fileHandle] = await window.showOpenFilePicker({
//                 types: [{ description: 'Text Files', accept: { 'text/plain': ['.txt'] } }],
//             });
//             const writable = await fileHandle.createWritable();
//             await writable.write(deviceId);
//             await writable.close();
//             alert('Configuration file updated successfully');
//         } catch (error) {
//             console.error('Failed to update configuration file:', error);
//             alert('Could not update the configuration file.');
//         }
//     };
    
//     if (error) return <p>Error: {error}</p>;

//     return (
//         <div className="device-table-container">
//             <h2>Device List</h2>
//             <form className="device-form" onSubmit={handleCreateOrUpdate}>
//                 <input
//                     type="text"
//                     placeholder="Description"
//                     value={description}
//                     onChange={(e) => setDescription(e.target.value)}
//                     required
//                 />
//                 <input
//                     type="text"
//                     placeholder="Address"
//                     value={address}
//                     onChange={(e) => setAddress(e.target.value)}
//                     required
//                 />
//                 <input
//                     type="text"
//                     placeholder="MHEC"
//                     value={mhec}
//                     onChange={(e) => setMhec(e.target.value)}
//                     required
//                 />
//                 <select
//                     value={personId}
//                     onChange={(e) => setPersonId(e.target.value)}
//                     required
//                 >
//                     <option value="">Select a Person</option>
//                     {persons.map((person) => (
//                         <option key={person.id} value={person.id}>
//                             {person.name}
//                         </option>
//                     ))}
//                 </select>
//                 <button type="submit" className="submit-button">
//                     {editingDeviceId ? 'Update' : 'Add'} Device
//                 </button>
//                 <button type="button" className="cancel-button" onClick={resetForm}>
//                     Cancel
//                 </button>
//             </form>

//             <table className="device-table">
//                 <thead>
//                     <tr>
//                         <th>ID</th>
//                         <th>Description</th>
//                         <th>Address</th>
//                         <th>MHEC</th>
//                         <th>Person Reference</th>
//                         <th>Actions</th>
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {devices.map((device) => (
//                         <tr key={device.id}>
//                             <td>{device.id}</td>
//                             <td>{device.description}</td>
//                             <td>{device.address}</td>
//                             <td>{device.mhec}</td>
//                             <td>{device.personId}</td>
//                             <td>
//                                 <button 
//                                     className="config-button"
//                                     onClick={() => updateConfigFile(device.id)}>
//                                     Update Config
//                                 </button>

//                                 <button
//                                     className="edit-button"
//                                     onClick={() => handleEdit(device)}
//                                 >
//                                     Edit
//                                 </button>
//                                 <button
//                                     className="delete-button"
//                                     onClick={() => handleDelete(device.id)}
//                                 >
//                                     Delete
//                                 </button>

//                             </td>
//                         </tr>
//                     ))}
//                 </tbody>
//             </table>
//         </div>
//     );
// };

// export default DeviceTable;
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './DeviceTable.css';

const DeviceTable = () => {
    const [devices, setDevices] = useState([]);
    const [persons, setPersons] = useState([]);
    const [description, setDescription] = useState('');
    const [address, setAddress] = useState('');
    const [mhec, setMhec] = useState('');
    const [personId, setPersonId] = useState('');
    const [editingDeviceId, setEditingDeviceId] = useState(null);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const getAuthHeaders = () => {
        const token = localStorage.getItem('token');
        return {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        };
    };

    const fetchDevices = async () => {
        try {
            // const response = await axios.get('http://localhost:8081/device', {
            //     headers: getAuthHeaders(),
            // });
            const response = await axios.get('http://devices.localhost/device', {
                headers: getAuthHeaders(),
            });
            setDevices(
                response.data.map(device => ({
                    ...device,
                    personId: device.personReference?.personId || null,
                }))
            );
        } catch (error) {
            setError(error.message);
        }
    };

    const fetchPersons = async () => {
        try {
            // const response = await axios.get('http://localhost:8080/person', {
            //     headers: getAuthHeaders(),
            // });
            const response = await axios.get('http://users.localhost/person', {
                headers: getAuthHeaders(),
            });
            setPersons(response.data);
        } catch (error) {
            setError(error.message);
        }
    };

    useEffect(() => {
        fetchDevices();
        fetchPersons();
    }, []);

    const handleCreateOrUpdate = async (event) => {
        event.preventDefault();

        const deviceData = {
            description,
            address,
            mhec,
            personReference: { personId },
        };

        try {
            if (editingDeviceId) {
                // await axios.put(
                //     `http://localhost:8081/device/${editingDeviceId}`,
                //     deviceData,
                //     { headers: getAuthHeaders() }
                // );
                await axios.put(
                    `http://devices.localhost/device/${editingDeviceId}`,
                    deviceData,
                    { headers: getAuthHeaders() }
                );
            } else {
                // await axios.post('http://localhost:8081/device', deviceData, {
                //     headers: getAuthHeaders(),
                // });
                await axios.post('http://devices.localhost/device', deviceData, {
                    headers: getAuthHeaders(),
                });
            }
            resetForm();
            fetchDevices();
        } catch (error) {
            setError(error.message);
        }
    };

    const handleEdit = (device) => {
        setEditingDeviceId(device.id);
        setDescription(device.description);
        setAddress(device.address);
        setMhec(device.mhec);
        setPersonId(device.personId);
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this device?')) {
            try {
                // await axios.delete(`http://localhost:8081/device/${id}`, {
                //     headers: getAuthHeaders(),
                // });
                await axios.delete(`http://devices.localhost/device/${id}`, {
                    headers: getAuthHeaders(),
                });
                fetchDevices();
            } catch (error) {
                setError(error.message);
            }
        }
    };

    const resetForm = () => {
        setDescription('');
        setAddress('');
        setMhec('');
        setPersonId('');
        setEditingDeviceId(null);
    };

    const updateConfigFile = async (deviceId) => {
        try {
            const [fileHandle] = await window.showOpenFilePicker({
                types: [{ description: 'Text Files', accept: { 'text/plain': ['.txt'] } }],
            });
            const writable = await fileHandle.createWritable();
            await writable.write(deviceId);
            await writable.close();
            alert('Configuration file updated successfully');
        } catch (error) {
            console.error('Failed to update configuration file:', error);
            alert('Could not update the configuration file.');
        }
    };

    if (error) return <p>Error: {error}</p>;

    return (
        <div className="device-table-container">
            <h2>Device List</h2>
            <form className="device-form" onSubmit={handleCreateOrUpdate}>
                <input
                    type="text"
                    placeholder="Description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="Address"
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="MHEC"
                    value={mhec}
                    onChange={(e) => setMhec(e.target.value)}
                    required
                />
                <select
                    value={personId}
                    onChange={(e) => setPersonId(e.target.value)}
                    required
                >
                    <option value="">Select a Person</option>
                    {persons.map((person) => (
                        <option key={person.id} value={person.id}>
                            {person.name}
                        </option>
                    ))}
                </select>
                <button type="submit" className="submit-button">
                    {editingDeviceId ? 'Update' : 'Add'} Device
                </button>
                <button type="button" className="cancel-button" onClick={resetForm}>
                    Cancel
                </button>
            </form>

            <table className="device-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Description</th>
                        <th>Address</th>
                        <th>MHEC</th>
                        <th>Person Reference</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {devices.map((device) => (
                        <tr key={device.id}>
                            <td>{device.id}</td>
                            <td>{device.description}</td>
                            <td>{device.address}</td>
                            <td>{device.mhec}</td>
                            <td>{device.personId}</td>
                            <td>
                                <button
                                    className="config-button"
                                    onClick={() => updateConfigFile(device.id)}
                                >
                                    Update Config
                                </button>
                                <button
                                    className="edit-button"
                                    onClick={() => handleEdit(device)}
                                >
                                    Edit
                                </button>
                                <button
                                    className="delete-button"
                                    onClick={() => handleDelete(device.id)}
                                >
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default DeviceTable;
