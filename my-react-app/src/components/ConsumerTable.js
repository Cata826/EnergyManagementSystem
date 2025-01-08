// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './ConsumerTable.css';

// const ConsumerTable = () => {
//     const [consumers, setConsumers] = useState([]);
//     const [error, setError] = useState(null);

//     const fetchConsumers = async () => {
//         try {
//             // const response = await axios.get('http://monitoring.localhost/consumer');
//             const response = await axios.get('http://localhost:8082/consumer');
//             setConsumers(response.data);
//         } catch (error) {
//             console.error("Failed to fetch consumers:", error);
//             setError("Could not fetch consumers.");
//         }
//     };


//     useEffect(() => {
//         fetchConsumers();
//     }, []);

//     if (error) return <p>Error: {error}</p>;

//     return (
//         <div className="consumer-table-container">
//             <h2>Consumer Data</h2>

//             <table className="consumer-table">
//                 <thead>
//                     <tr>
//                         <th>ID</th>
//                         <th>Device ID</th>
//                         <th>Measurement Value</th>
             
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {consumers.map((consumer) => (
//                         <tr key={consumer.id}>
//                             <td>{consumer.id}</td>
//                             <td>{consumer.deviceId}</td>
//                             <td>{consumer.measurementValue}</td>

//                         </tr>
//                     ))}
//                 </tbody>
//             </table>
//         </div>
//     );
// };

// export default ConsumerTable;
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ConsumerTable.css';

const ConsumerTable = () => {
    const [consumers, setConsumers] = useState([]);
    const [error, setError] = useState(null);

    const getAuthHeaders = () => {
        const token = localStorage.getItem('token');
        return {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        };
    };

    const fetchConsumers = async () => {
        try {
            // const response = await axios.get('http://localhost:8082/consumer', {
            //     headers: getAuthHeaders(),
            // });
            const response = await axios.get('http://monitoring.localhost/consumer', {
                headers: getAuthHeaders(),
            });
            setConsumers(response.data);
        } catch (error) {
            console.error("Failed to fetch consumers:", error);
            setError("Could not fetch consumers.");
        }
    };

    useEffect(() => {
        fetchConsumers();
    }, []);

    if (error) return <p>Error: {error}</p>;

    return (
        <div className="consumer-table-container">
            <h2>Consumer Data</h2>

            <table className="consumer-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Device ID</th>
                        <th>Measurement Value</th>
                    </tr>
                </thead>
                <tbody>
                    {consumers.map((consumer) => (
                        <tr key={consumer.id}>
                            <td>{consumer.id}</td>
                            <td>{consumer.deviceId}</td>
                            <td>{consumer.measurementValue}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ConsumerTable;
