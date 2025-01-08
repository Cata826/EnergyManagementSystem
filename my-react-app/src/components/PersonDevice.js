
// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import SockJS from 'sockjs-client';
// import { Stomp } from '@stomp/stompjs';
// import './PersonDevice.css';
// import { useNavigate } from 'react-router-dom';
// const PersonDevice = () => {
//     const [devices, setDevices] = useState([]);
//     const [loading, setLoading] = useState(true);
//     const [error, setError] = useState('');
//     const [notifications, setNotifications] = useState([]);
//     const [mhecResults, setMhecResults] = useState([]);

//     useEffect(() => {
//         const fetchDevices = async () => {
//             const id = localStorage.getItem('id');
//             if (!id) {
//                 setError('Person ID not found. Please log in again.');
//                 setLoading(false);
//                 return;
//             }

//             try {
//                 const response = await axios.get(`http://localhost:8081/device/person/${id}`);
//                 // const response = await axios.get(`http://devices.localhost/device/person/${id}`);
//                 setDevices(response.data);
//             } catch (error) {
//                 setError('Failed to fetch devices. Please try again later.');
//             } finally {
//                 setLoading(false);
//             }
//         };

//         fetchDevices();
//     }, []);
//     const navigate = useNavigate();

//         // Function to handle navigation to /chart
//         const handleNavigateToChart = () => {
//             navigate('/chart');
//         };
//     useEffect(() => {
//         const id = localStorage.getItem('id');
//         if (!id) {
//             setError('Person ID not found. Please log in again.');
//             return;
//         }

//         const interval = setInterval(async () => {
//             try {
//                 // const response = await axios.get(`http://monitoring.localhost/consumer/checkMhec/person/${id}`);
//                 const response = await axios.get(`http://localhost:8082/consumer/checkMhec/person/${id}`);
//                 console.log('MHEC check response:', response.data);

//                 setMhecResults(prevResults => [...prevResults, response.data]);
//             } catch (error) {
//                 console.error('Error during MHEC check:', error);
//                 setError('Failed to check MHEC. Please try again later.');
//             }
//         }, 1000);  //10 min
//         return () => clearInterval(interval);
//     }, []);

//     // useEffect(() => {
//     //     const socket = new SockJS('http://localhost:8082/ws');
//     //     const stompClient = Stomp.over(socket);
//     //     const id = localStorage.getItem('id');
    
//     //     if (!id) {
//     //         setError('Person ID not found. Please log in again.');
//     //         return;
//     //     }
    
//     //     stompClient.connect({}, () => {
//     //         console.log('Connected to WebSocket');

//     //         stompClient.subscribe(`/topic/notifications/${id}`, (message) => {
//     //             if (message.body) {
//     //                 try {
//     //                     const notification = JSON.parse(message.body);
//     //                     setNotifications(prevNotifications => [...prevNotifications, notification]);
//     //                 } catch (error) {
//     //                     console.error('Failed to parse WebSocket message:', error);
//     //                 }
//     //             }
//     //         });
//     //     });
    
//     //     return () => {
//     //         if (stompClient) stompClient.disconnect();
//     //     };
//     // }, []);
//     useEffect(() => {
//         // const socket = new SockJS('http://monitoring.localhost/ws');
//         const socket = new SockJS('http://localhost:8082/ws');
//         const stompClient = Stomp.over(socket);
//         const id = localStorage.getItem('id');
    
//         if (!id) {
//             setError('Person ID not found. Please log in again.');
//             return;
//         }
    
//         stompClient.connect({}, () => {
//             console.log('Connected to WebSocket');
    
//             stompClient.subscribe(`/topic/notifications/${id}`, (message) => {
//                 if (message.body) {
//                     try {
//                         const notification = JSON.parse(message.body);
//                         setNotifications(prevNotifications => {
//                             // Verificăm dacă notificarea este deja în lista curentă
//                             const isDuplicate = prevNotifications.some(
//                                 (notif) => notif.message === notification.message
//                             );
    
//                             // Adăugăm notificarea doar dacă este unică
//                             if (!isDuplicate) {
//                                 return [...prevNotifications, notification];
//                             }
//                             return prevNotifications;
//                         });
//                     } catch (error) {
//                         console.error('Failed to parse WebSocket message:', error);
//                     }
//                 }
//             });
//         });
    
//         return () => {
//             if (stompClient) stompClient.disconnect();
//         };
//     }, []);
    
//     if (loading) return <p>Loading devices...</p>;
//     if (error) return <p className="error-message">{error}</p>;

//     return (
//         <div className="person-device-container">
//             <h2>Your Devices</h2>
//             {devices.length === 0 ? (
//                 <p className="no-devices-message">No devices found.</p>
//             ) : (
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
//                         {devices.map(device => (
//                             <tr key={device.id}>
//                                 <td>{device.id}</td>
//                                 <td>{device.description}</td>
//                                 <td>{device.address}</td>
//                                 <td>{device.mhec}</td>
//                             </tr>
//                         ))}
//                     </tbody>
//                 </table>
//             )}

//             <div className="notifications">
//                 <h3>Notifications</h3>
//                 {notifications.length === 0 ? (
//                     <p>No notifications at the moment.</p>
//                 ) : (
//                     <ul>
//                         {notifications.map((notification, index) => (
//                             <li key={index}>{notification.message}</li>
//                         ))}
//                     </ul>
//                 )}
//             </div>
//                  {/* Button to navigate to /chart */}
//             <button onClick={handleNavigateToChart}>Go to Chart</button>
//         </div>
//     );
// };

// export default PersonDevice;
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import './PersonDevice.css';
import { useNavigate } from 'react-router-dom';

const PersonDevice = () => {
    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [notifications, setNotifications] = useState([]);
    const [mhecResults, setMhecResults] = useState([]);

    const navigate = useNavigate();

    // Fetch devices with authorization token
    useEffect(() => {
        const fetchDevices = async () => {
            const id = localStorage.getItem('id');
            const token = localStorage.getItem('token'); // Get token from localStorage

            if (!id || !token) {
                setError('Person ID or Token not found. Please log in again.');
                setLoading(false);
                return;
            }

            try {
                // const response = await axios.get(`http://localhost:8081/device/person/${id}`, {
                //     headers: {
                //         'Authorization': `Bearer ${token}`, // Add Authorization header
                //         'Content-Type': 'application/json',
                //     },
                // });
                const response = await axios.get(`http://devices.localhost/device/person/${id}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`, // Add Authorization header
                        'Content-Type': 'application/json',
                    },
                });
                setDevices(response.data);
            } catch (error) {
                setError('Failed to fetch devices. Please try again later.');
            } finally {
                setLoading(false);
            }
        };

        fetchDevices();
    }, []);

    // Handle navigation to /chart
    const handleNavigateToChart = () => {
        navigate('/chart');
    };
    const handleNavigateToChat = () => {
        navigate('/chat');
    };

    // Fetch MHEC results at a regular interval with authorization token
    useEffect(() => {
        const id = localStorage.getItem('id');
        const token = localStorage.getItem('token'); // Get token from localStorage

        if (!id || !token) {
            setError('Person ID or Token not found. Please log in again.');
            return;
        }

        const interval = setInterval(async () => {
            try {
                // const response = await axios.get(`http://localhost:8082/consumer/checkMhec/person/${id}`, {
                //     headers: {
                //         'Authorization': `Bearer ${token}`, // Add Authorization header
                //         'Content-Type': 'application/json',
                //     },
                // });
                const response = await axios.get(`http://monitoring.localhost/consumer/checkMhec/person/${id}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`, // Add Authorization header
                        'Content-Type': 'application/json',
                    },
                });
                console.log('MHEC check response:', response.data);
                setMhecResults(prevResults => [...prevResults, response.data]);
            } catch (error) {
                console.error('Error during MHEC check:', error);
                setError('Failed to check MHEC. Please try again later.');
            }
        }, 100000);  // Check every 1 second (for demonstration, adjust as necessary)

        return () => clearInterval(interval);
    }, []);

    // WebSocket connection for notifications
    useEffect(() => {
        // const socket = new SockJS('http://monitoring.localhost/ws');
        const socket = new SockJS('http://localhost:8082/ws');
        const stompClient = Stomp.over(socket);
        const id = localStorage.getItem('id');
        const token = localStorage.getItem('token'); // Get token from localStorage

        if (!id || !token) {
            setError('Person ID or Token not found. Please log in again.');
            return;
        }

        stompClient.connect({}, () => {
            console.log('Connected to WebSocket');
            stompClient.subscribe(`/topic/notifications/${id}`, (message) => {
                if (message.body) {
                    try {
                        const notification = JSON.parse(message.body);
                        setNotifications(prevNotifications => {
                            // Check if the notification is already in the list to prevent duplicates
                            const isDuplicate = prevNotifications.some(
                                (notif) => notif.message === notification.message
                            );

                            // Add the notification only if it's unique
                            if (!isDuplicate) {
                                return [...prevNotifications, notification];
                            }
                            return prevNotifications;
                        });
                    } catch (error) {
                        console.error('Failed to parse WebSocket message:', error);
                    }
                }
            });
        });

        return () => {
            if (stompClient) stompClient.disconnect();
        };
    }, []);

    if (loading) return <p>Loading devices...</p>;
    if (error) return <p className="error-message">{error}</p>;

    return (
        <div className="person-device-container">
            <h2>Your Devices</h2>
            {devices.length === 0 ? (
                <p className="no-devices-message">No devices found.</p>
            ) : (
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
                        {devices.map(device => (
                            <tr key={device.id}>
                                <td>{device.id}</td>
                                <td>{device.description}</td>
                                <td>{device.address}</td>
                                <td>{device.mhec}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}

            <div className="notifications">
                <h3>Notifications</h3>
                {notifications.length === 0 ? (
                    <p>No notifications at the moment.</p>
                ) : (
                    <ul>
                        {notifications.map((notification, index) => (
                            <li key={index}>{notification.message}</li>
                        ))}
                    </ul>
                )}
            </div>

            {/* Button to navigate to /chart */}
            <button onClick={handleNavigateToChart}>Go to Chart</button>
            <button onClick={handleNavigateToChat}>Go to Chat with ADMIN</button>
        </div>
    );
};

export default PersonDevice;
