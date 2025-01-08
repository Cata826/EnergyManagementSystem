// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './PersonReferenceTable.css';

// const PersonReferenceTable = () => {
//     const [personReferences, setPersonReferences] = useState([]);
//     const [error, setError] = useState(null);

//     const fetchPersonReferences = async () => {
//         try {
//             const response = await axios.get('http://localhost:8081/person-reference');
//             // const response = await axios.get('http://devices.localhost/person-reference');
//             setPersonReferences(response.data);
//             console.log("Fetched person references:", response.data);
//         } catch (error) {
//             console.error("Failed to fetch person references:", error);
//             setError("Could not fetch person references.");
//         }
//     };

//     const handleDelete = async (id) => {
//         if (window.confirm('Are you sure you want to delete this person reference?')) {
//             try {
//                 await axios.delete(`http://localhost:8081/person-reference/${id}`);
//                 // const response = await axios.get('http://devices.localhost/person-reference');
//                 fetchPersonReferences(); 
//             } catch (error) {
//                 console.error("Failed to delete person reference:", error);
//                 setError("Could not delete person reference.");
//             }
//         }
//     };

//     useEffect(() => {
//         fetchPersonReferences();
//     }, []);

//     if (error) return <p>Error: {error}</p>;

//     return (
//         <div className="person-reference-table-container">
//             <h2>Person References</h2>

//             <table className="person-reference-table">
//                 <thead>
//                     <tr>
//                         <th>ID</th>
//                         <th>Person ID</th>
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {personReferences.map((reference) => (
//                         <tr key={reference.id}>
//                             <td>{reference.id}</td>
//                             <td>{reference.personId}</td>
//                         </tr>
//                     ))}
//                 </tbody>
//             </table>
//         </div>
//     );
// };

// export default PersonReferenceTable;
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './PersonReferenceTable.css';

const PersonReferenceTable = () => {
    const [personReferences, setPersonReferences] = useState([]);
    const [error, setError] = useState(null);

    const fetchPersonReferences = async () => {
        const token = localStorage.getItem('token'); // Get the token from localStorage
        if (!token) {
            setError('No authorization token found.');
            return;
        }

        try {
            // const response = await axios.get('http://localhost:8081/person-reference', {
            //     headers: {
            //         'Authorization': `Bearer ${token}`, // Add the token to the headers
            //         'Content-Type': 'application/json',  // Set the content type to JSON
            //     }
            // });
            const response = await axios.get('http://devices.localhost/person-reference', {
                headers: {
                    'Authorization': `Bearer ${token}`, // Add the token to the headers
                    'Content-Type': 'application/json',  // Set the content type to JSON
                }
            });
            setPersonReferences(response.data);
            console.log("Fetched person references:", response.data);
        } catch (error) {
            console.error("Failed to fetch person references:", error);
            setError("Could not fetch person references.");
        }
    };

    const handleDelete = async (id) => {
        const token = localStorage.getItem('token'); // Get the token from localStorage
        if (!token) {
            setError('No authorization token found.');
            return;
        }

        if (window.confirm('Are you sure you want to delete this person reference?')) {
            try {
                // await axios.delete(`http://localhost:8081/person-reference/${id}`, {
                //     headers: {
                //         'Authorization': `Bearer ${token}`, // Add the token to the headers
                //         'Content-Type': 'application/json',  // Set the content type to JSON
                //     }
                // });
                await axios.delete(`http://devices.localhost/person-reference/${id}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`, // Add the token to the headers
                        'Content-Type': 'application/json',  // Set the content type to JSON
                    }
                });
                fetchPersonReferences(); // Re-fetch the list after deletion
            } catch (error) {
                console.error("Failed to delete person reference:", error);
                setError("Could not delete person reference.");
            }
        }
    };

    useEffect(() => {
        fetchPersonReferences();
    }, []);

    if (error) return <p>Error: {error}</p>;

    return (
        <div className="person-reference-table-container">
            <h2>Person References</h2>

            <table className="person-reference-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Person ID</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {personReferences.map((reference) => (
                        <tr key={reference.id}>
                            <td>{reference.id}</td>
                            <td>{reference.personId}</td>
                            <td>
                                <button onClick={() => handleDelete(reference.id)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default PersonReferenceTable;
