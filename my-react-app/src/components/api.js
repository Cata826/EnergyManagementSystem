// import axios from 'axios';

// const api = axios.create({
//     // baseURL: 'http://users.localhost', // Backend URLh
//     baseURL: 'http://localhost:8080',
//     withCredentials: true, // For cookies/sessions
// });

// export default api;
const API_BASE_URL = "http://localhost:8083/chat"; 

export const fetchMessages = async (sender, receiver) => {
  const response = await fetch(`${API_BASE_URL}?sender=${sender}&receiver=${receiver}`);
  const data = await response.json();
  return data;
};

export const saveMessage = async (message) => {
  const response = await fetch(API_BASE_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(message),
  });
  return response.json();
};
