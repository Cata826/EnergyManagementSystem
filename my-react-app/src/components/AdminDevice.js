
// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './AdminDevice.css';

// const AdminDevice = () => {
//     const [persons, setPersons] = useState([]);
//     const [devices, setDevices] = useState([]);
//     const [selectedPersonId, setSelectedPersonId] = useState('');

//     const fetchPersons = async () => {
//         try {
//             // const response = await axios.get('http://users.localhost/person');
//             const response = await axios.get('http://localhost:8080/person');
//             setPersons(response.data);
//         } catch (error) {
//             console.error('Error fetching persons:', error);
//         }
//     };

//     const fetchDevicesByPersonId = async (personId) => {
//         try {
//             const response = await axios.get(`http://localhost:8081/device/person/${personId}`);
//             // const response = await axios.get(`http://devices.localhost/device/person/${personId}`);
//             setDevices(response.data);
//         } catch (error) {
//             console.error('Error fetching devices:', error);
//         }
//     };

//     useEffect(() => {
//         fetchPersons();
//     }, []);

//     const handlePersonChange = (event) => {
//         const personId = event.target.value;
//         setSelectedPersonId(personId);
//         if (personId) {
//             fetchDevicesByPersonId(personId);
//         } else {
//             setDevices([]);
//         }
//     };

//     return (
//         <div className="admin-device-container">
//             <h2>Devices for a Person</h2>
//             <label htmlFor="person-select">Select a Person:</label>
//             <select id="person-select" value={selectedPersonId} onChange={handlePersonChange}>
//                 <option value="">--Choose a person--</option>
//                 {persons.map((person) => (
//                     <option key={person.id} value={person.id}>
//                         {person.name} ({person.username})
//                     </option>
//                 ))}
//             </select>

//             {devices.length > 0 ? (
//                 <table>
//                     <thead>
//                         <tr>
//                             <th>ID</th>
//                             <th>Description</th>
//                             <th>Address</th>
//                             <th>MHEC</th>
//                         </tr>
//                     </thead>
//                     <tbody>
//                         {devices.map((device) => (
//                             <tr key={device.id}>
//                                 <td>{device.id}</td>
//                                 <td>{device.description}</td>
//                                 <td>{device.address}</td>
//                                 <td>{device.mhec}</td>
//                             </tr>
//                         ))}
//                     </tbody>
//                 </table>
//             ) : (
//                 <p className="no-devices-message">No devices found for this person.</p>
//             )}
//         </div>
//     );
// };

// export default AdminDevice;
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './AdminDevice.css';

const AdminDevice = () => {
    const [persons, setPersons] = useState([]);
    const [devices, setDevices] = useState([]);
    const [selectedPersonId, setSelectedPersonId] = useState('');

    // Function to set Authorization headers
    const getAuthHeaders = () => {
        const token = localStorage.getItem('token');
        return {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        };
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
            console.error('Error fetching persons:', error);
        }
    };

    const fetchDevicesByPersonId = async (personId) => {
        try {
            // const response = await axios.get(`http://localhost:8081/device/person/${personId}`, {
            //     headers: getAuthHeaders(),
            // });
            const response = await axios.get(`http://devices.localhost/device/person/${personId}`, {
                headers: getAuthHeaders(),
            });
            setDevices(response.data);
        } catch (error) {
            console.error('Error fetching devices:', error);
        }
    };

    useEffect(() => {
        fetchPersons();
    }, []);

    const handlePersonChange = (event) => {
        const personId = event.target.value;
        setSelectedPersonId(personId);
        if (personId) {
            fetchDevicesByPersonId(personId);
        } else {
            setDevices([]);
        }
    };

    return (
        <div className="admin-device-container">
            <h2>Devices for a Person</h2>
            <label htmlFor="person-select">Select a Person:</label>
            <select id="person-select" value={selectedPersonId} onChange={handlePersonChange}>
                <option value="">--Choose a person--</option>
                {persons.map((person) => (
                    <option key={person.id} value={person.id}>
                        {person.name} ({person.username})
                    </option>
                ))}
            </select>

            {devices.length > 0 ? (
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Description</th>
                            <th>Address</th>
                            <th>MHEC</th>
                        </tr>
                    </thead>
                    <tbody>
                        {devices.map((device) => (
                            <tr key={device.id}>
                                <td>{device.id}</td>
                                <td>{device.description}</td>
                                <td>{device.address}</td>
                                <td>{device.mhec}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p className="no-devices-message">No devices found for this person.</p>
            )}
        </div>
    );
};

export default AdminDevice;
