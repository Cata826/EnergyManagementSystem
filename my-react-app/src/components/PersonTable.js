
// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './PersonTable.css';

// const PersonTable = () => {
//     const [persons, setPersons] = useState([]);
//     const [name, setName] = useState('');
//     const [username, setUsername] = useState('');
//     const [email, setEmail] = useState('');
//     const [password, setPassword] = useState('');
//     const [admin, setAdmin] = useState('false'); 
//     const [editingPersonId, setEditingPersonId] = useState(null);
//     const [error, setError] = useState(null);

//     // const fetchPersons = async () => {
//     //     try {
//     //         const response = await axios.get('http://localhost:8080/person');
//     //         // const response = await axios.get('http://users.localhost/person');
//     //         setPersons(response.data);
//     //     } catch (error) {
//     //         setError(error.message);
//     //     }
//     // };
//     const fetchPersons = async () => {
//         const token = localStorage.getItem('token'); // Get token from localStorage
    
//         try {
//             const response = await axios.get('http://localhost:8080/person', {
//                 headers: {
//                     'Authorization': `Bearer ${token}`,
//                     'Content-Type': 'application/json',
//                 }
                
//             });
//             setPersons(response.data);
//         } catch (error) {
//             setError(error.message);
//         }
//     };
    

//     useEffect(() => {
//         fetchPersons();
//     }, []);

//     const handleCreatePerson = async (event) => {
//         event.preventDefault();
//         const personData = {
//             name,
//             username,
//             email,
//             password,
//             admin: admin === 'true',
//         };

//         try {
//             const response = await axios.post('http://localhost:8080/person', personData);
            
//             // const response = await axios.post('http://users.localhost/person', personData);
//             if (response.status === 201) {
//                 resetForm();
//                 fetchPersons(); 
//             }
//         } catch (error) {
//             setError(error.message);
//         }
//     };

//     const handleUpdatePerson = async (event) => {
//         event.preventDefault();
//         const personData = {
//             name,
//             username,
//             email,
//             password,
//             admin: admin === 'true', 
//         };

//         try {
//             await axios.put(`http://localhost:8080/person/${editingPersonId}`, personData);
//             // await axios.put(`http://users.localhost/person/${editingPersonId}`, personData);
//             resetForm();
//             fetchPersons(); 
//         } catch (error) {
//             setError(error.message);
//         }
//     };

//     const handleEdit = (person) => {
//         setEditingPersonId(person.id);
//         setName(person.name);
//         setUsername(person.username);
//         setEmail(person.email);
//         setPassword(person.password);
//         setAdmin(person.admin ? 'true' : 'false'); 
//     };

//     const handleDelete = async (id) => {
//         if (window.confirm('Are you sure you want to delete this person?')) {
//             try {
//                 // await axios.delete(`http://users.localhost/person/${id}`);
//                 await axios.delete(`http://localhost:8080/person/${id}`);
//                 fetchPersons(); 
//             } catch (error) {
//                 setError(error.message);
//             }
//         }
//     };

//     const resetForm = () => {
//         setName('');
//         setUsername('');
//         setEmail('');
//         setPassword('');
//         setAdmin('false'); 
//         setEditingPersonId(null); 
//     };

//     if (error) return <p>Error: {error}</p>;

//     return (
//         <div className="person-table-container">
//             <h2>Person List</h2>
//             <form className="person-form" onSubmit={editingPersonId ? handleUpdatePerson : handleCreatePerson}>
//                 <input
//                     type="text"
//                     placeholder="Name"
//                     value={name}
//                     onChange={(e) => setName(e.target.value)}
//                     required
//                 />
//                 <input
//                     type="text"
//                     placeholder="Username"
//                     value={username}
//                     onChange={(e) => setUsername(e.target.value)}
//                     required
//                 />
//                 <input
//                     type="email"
//                     placeholder="Email"
//                     value={email}
//                     onChange={(e) => setEmail(e.target.value)}
//                     required
//                 />
//                 <input
//                     type="password"
//                     placeholder="Password"
//                     value={password}
//                     onChange={(e) => setPassword(e.target.value)}
//                     required
//                 />
//                 <label>
//                     Is Admin:
//                     <select value={admin} onChange={(e) => setAdmin(e.target.value)}>
//                         <option value="true">True</option>
//                         <option value="false">False</option>
//                     </select>
//                 </label>
//                 <button type="submit" className="submit-button">
//                     {editingPersonId ? 'Update' : 'Add'} Person
//                 </button>
//                 <button type="button" className="cancel-button" onClick={resetForm}>
//                     Cancel
//                 </button>
//             </form>

//             <table className="person-table">
//                 <thead>
//                     <tr>
//                         <th>Name</th>
//                         <th>Username</th>
//                         <th>Email</th>
//                         <th>Admin</th>
//                         <th>Actions</th>
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {persons.map((person) => (
//                         <tr key={person.id}>
//                             <td>{person.name}</td>
//                             <td>{person.username}</td>
//                             <td>{person.email}</td>
//                             <td>{person.admin ? 'ADMIN' : 'PERSON'}</td>
//                             <td>
//                                 <button className="edit-button" onClick={() => handleEdit(person)}>Edit</button>
//                                 <button className="delete-button" onClick={() => handleDelete(person.id)}>Delete</button>
//                             </td>
//                         </tr>
//                     ))}
//                 </tbody>
//             </table>
//         </div>
//     );
// };

// export default PersonTable;
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './PersonTable.css';

const PersonTable = () => {
    const [persons, setPersons] = useState([]);
    const [name, setName] = useState('');
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [admin, setAdmin] = useState('false'); 
    const [editingPersonId, setEditingPersonId] = useState(null);
    const [error, setError] = useState(null);

    const getHeaders = () => {
        const token = localStorage.getItem('token'); // Get token from localStorage
        return {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        };
    };

    const fetchPersons = async () => {
        try {
            // const response = await axios.get('http://localhost:8080/person', {
            //     headers: getHeaders(),
            // });
            const response = await axios.get('http://users.localhost/person', {
                headers: getHeaders(),
            });
            setPersons(response.data);
        } catch (error) {
            setError(error.message);
        }
    };

    useEffect(() => {
        fetchPersons();
    }, []);

    const handleCreatePerson = async (event) => {
        event.preventDefault();
        const personData = {
            name,
            username,
            email,
            password,
            admin: admin === 'true',
        };

        try {
            // const response = await axios.post('http://localhost:8080/person', personData, {
            //     headers: getHeaders(),
            // });
            const response = await axios.post('http://users.localhost/person', personData, {
                headers: getHeaders(),
            });
            if (response.status === 201) {
                resetForm();
                fetchPersons(); 
            }
        } catch (error) {
            setError(error.message);
        }
    };

    const handleUpdatePerson = async (event) => {
        event.preventDefault();
        const personData = {
            name,
            username,
            email,
            password,
            admin: admin === 'true', 
        };

        try {
            // await axios.put(`http://localhost:8080/person/${editingPersonId}`, personData, {
            //     headers: getHeaders(),
            // });
            await axios.put(`http://users.localhost/person/${editingPersonId}`, personData, {
                headers: getHeaders(),
            });
            resetForm();
            fetchPersons(); 
        } catch (error) {
            setError(error.message);
        }
    };

    const handleEdit = (person) => {
        setEditingPersonId(person.id);
        setName(person.name);
        setUsername(person.username);
        setEmail(person.email);
        setPassword(person.password);
        setAdmin(person.admin ? 'true' : 'false'); 
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this person?')) {
            try {
                // await axios.delete(`http://localhost:8080/person/${id}`, {
                //     headers: getHeaders(),
                // });
                await axios.delete(`http://users.localhost/person/${id}`, {
                    headers: getHeaders(),
                });
                fetchPersons(); 
            } catch (error) {
                setError(error.message);
            }
        }
    };

    const resetForm = () => {
        setName('');
        setUsername('');
        setEmail('');
        setPassword('');
        setAdmin('false'); 
        setEditingPersonId(null); 
    };

    if (error) return <p>Error: {error}</p>;

    return (
        <div className="person-table-container">
            <h2>Person List</h2>
            <form className="person-form" onSubmit={editingPersonId ? handleUpdatePerson : handleCreatePerson}>
                <input
                    type="text"
                    placeholder="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <label>
                    Is Admin:
                    <select value={admin} onChange={(e) => setAdmin(e.target.value)}>
                        <option value="true">True</option>
                        <option value="false">False</option>
                    </select>
                </label>
                <button type="submit" className="submit-button">
                    {editingPersonId ? 'Update' : 'Add'} Person
                </button>
                <button type="button" className="cancel-button" onClick={resetForm}>
                    Cancel
                </button>
            </form>

            <table className="person-table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Admin</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {persons.map((person) => (
                        <tr key={person.id}>
                            <td>{person.name}</td>
                            <td>{person.username}</td>
                            <td>{person.email}</td>
                            <td>{person.admin ? 'ADMIN' : 'PERSON'}</td>
                            <td>
                                <button className="edit-button" onClick={() => handleEdit(person)}>Edit</button>
                                <button className="delete-button" onClick={() => handleDelete(person.id)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default PersonTable;
