// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './BrokerTable.css';

// const BrokerTable = () => {
//     const [brokers, setBrokers] = useState([]);
//     const [error, setError] = useState(null);

//     const fetchBrokers = async () => {
//         try {
//             // const response = await axios.get('http://monitoring.localhost/broker');
//             const response = await axios.get('http://localhost:8082/broker');
//             // setBrokers(response.data);
//         } catch (error) {
//             console.error("Failed to fetch brokers:", error);
//             setError("Could not fetch brokers.");
//         }
//     };


//     useEffect(() => {
//         fetchBrokers();
//     }, []);

//     if (error) return <p>Error: {error}</p>;

//     return (
//         <div className="broker-table-container">
//             <h2>Broker Data</h2>

//             <table className="broker-table">
//                 <thead>
//                     <tr>
//                         <th>ID</th>
//                         <th>Device ID</th>
//                         <th>Timestamp</th>
//                         <th>Measurement Value</th>
  
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {brokers.map((broker) => (
//                         <tr key={broker.id}>
//                             <td>{broker.id}</td>
//                             <td>{broker.deviceId}</td>
//                             <td>{new Date(broker.timestamp).toLocaleString()}</td>
//                             <td>{broker.measurementValue}</td>
//                         </tr>
//                     ))}
//                 </tbody>
//             </table>
//         </div>
//     );
// };

// export default BrokerTable;
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './BrokerTable.css';

const BrokerTable = () => {
    const [brokers, setBrokers] = useState([]);
    const [error, setError] = useState(null);

    const getAuthHeaders = () => {
        const token = localStorage.getItem('token');
        return {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        };
    };

    const fetchBrokers = async () => {
        try {
            // const response = await axios.get('http://localhost:8082/broker', {
            //     headers: getAuthHeaders(),
            // });
            const response = await axios.get('http://monitoring.localhost/broker', {
                headers: getAuthHeaders(),
            });
            setBrokers(response.data);
        } catch (error) {
            console.error("Failed to fetch brokers:", error);
            setError("Could not fetch brokers.");
        }
    };

    useEffect(() => {
        fetchBrokers();
    }, []);

    if (error) return <p>Error: {error}</p>;

    return (
        <div className="broker-table-container">
            <h2>Broker Data</h2>

            <table className="broker-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Device ID</th>
                        <th>Timestamp</th>
                        <th>Measurement Value</th>
                    </tr>
                </thead>
                <tbody>
                    {brokers.map((broker) => (
                        <tr key={broker.id}>
                            <td>{broker.id}</td>
                            <td>{broker.deviceId}</td>
                            <td>{new Date(broker.timestamp).toLocaleString()}</td>
                            <td>{broker.measurementValue}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default BrokerTable;
