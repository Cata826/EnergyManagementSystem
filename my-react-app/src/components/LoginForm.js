
// import React, { useState } from 'react';
// import axios from 'axios';
// import { useNavigate } from 'react-router-dom';
// import './Login.css';
// import api from './api';
// const LoginForm = () => {
//     const [email, setEmail] = useState('');
//     const [password, setPassword] = useState('');
//     const [error, setError] = useState('');
//     const navigate = useNavigate();


//     const handleSubmit = async (event) => {
//         event.preventDefault();
//         try {
//              const response = await axios.post('http://localhost:8080/auth/login', { email, password });
//             // const response = await axios.post('http://users.localhost/auth/login', { email, password },{ withCredentials: true });
//             // const response = await axios.post('http://users.localhost/auth/login', { email, password },{ withCredentials: true });

//             // const response = await api.post('/auth/login', { email, password });

//             const { id, admin } = response.data; 
//             localStorage.setItem('id', id);
//             localStorage.setItem('admin', admin);
//             console.log(admin);

//             navigate(admin ? '/admin' : '/persondevice');
//         } catch (error) {
//             if (error.response) {
//                 setError('Invalid email or password');
//             } else if (error.request) {
//                 setError('No response from server. Please check if the server is running.');
//             } else {
//                 setError('Error occurred while trying to log in. Please try again.');
//             }
//             console.error('Error in login or admin check:', error);
//         }
//     };
    
    
//     return (
//         <div className="page-container">
//             <div className="login-container">
//                 <h2>Login</h2>
//                 <form className="login-form" onSubmit={handleSubmit}>
//                     <div>
//                         <label>Email:</label>
//                         <input
//                             type="email"
//                             value={email}
//                             onChange={(e) => setEmail(e.target.value)}
//                             required
//                         />
//                     </div>
//                     <div>
//                         <label>Password:</label>
//                         <input
//                             type="password"
//                             value={password}
//                             onChange={(e) => setPassword(e.target.value)}
//                             required
//                         />
//                     </div>
//                     {error && <p className="error-message">{error}</p>}
//                     <button type="submit">Login</button>
//                 </form>
//             </div>
//         </div>
//     );
// };

// export default LoginForm;
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Login.css';
import api from './api'; // Axios instance for API calls

const LoginForm = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            // Send login request
          //  const response = await axios.post('http://localhost:8080/auth/login', { email, password });
          const response = await axios.post('http://users.localhost/auth/login', { email, password });

            // Extract token, id, and isAdmin from the response
            const { token, id, admin } = response.data;

            // Store token, id, and admin status in localStorage
            localStorage.setItem('token', token);
            localStorage.setItem('id', id);
            localStorage.setItem('admin', admin);

            // Set Authorization header globally for all future Axios requests
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            console.log(token);
            console.log(id);
            console.log("Login successful. Admin:", admin);

            // Navigate based on role
            navigate(admin ? '/admin' : '/persondevice');
        } catch (error) {
            if (error.response) {
                setError('Invalid email or password');
            } else if (error.request) {
                setError('No response from server. Please check if the server is running.');
            } else {
                setError('Error occurred while trying to log in. Please try again.');
            }
            console.error('Error in login:', error);
        }
    };

    return (
        <div className="page-container">
            <div className="login-container">
                <h2>Login</h2>
                <form className="login-form" onSubmit={handleSubmit}>
                    <div>
                        <label>Email:</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label>Password:</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    {error && <p className="error-message">{error}</p>}
                    <button type="submit">Login</button>
                </form>
            </div>
        </div>
    );
};

export default LoginForm;
