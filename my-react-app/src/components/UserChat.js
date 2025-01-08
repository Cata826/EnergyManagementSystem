// import React, { useState, useEffect } from 'react';
// import { Stomp } from '@stomp/stompjs';

// const UserChat = () => {
//     const [messages, setMessages] = useState([]);
//     const [inputMessage, setInputMessage] = useState('');
//     const currentUserId = localStorage.getItem('id'); // Current user's ID
//     const adminId = 'admin-id-uuid'; // Admin's fixed ID
//     const [stompClient, setStompClient] = useState(null);

//     // Connect to WebSocket
//     useEffect(() => {
//         const client = Stomp.client('http://localhost:8083/ws');
//         client.connect({}, () => {
//             console.log('Connected to WebSocket');
//             client.subscribe('/topic/messages', (msg) => {
//                 const message = JSON.parse(msg.body);
//                 if (
//                     (message.sender === currentUserId && message.receiver === adminId) ||
//                     (message.sender === adminId && message.receiver === currentUserId)
//                 ) {
//                     setMessages((prev) => [...prev, message]);
//                 }
//             });
//         });
//         setStompClient(client);
//         return () => client.disconnect();
//     }, [currentUserId, adminId]);

//     // Fetch previous conversation
//     useEffect(() => {
//         fetch(`http://localhost:8083/chat/conversation?sender=${currentUserId}&receiver=${adminId}`)
//             .then((res) => res.json())
//             .then((data) => setMessages(data))
//             .catch((err) => console.error('Error loading conversation:', err));
//     }, [currentUserId, adminId]);

//     // Send a message
//     const sendMessage = () => {
//         if (stompClient && inputMessage.trim()) {
//             const message = {
//                 sender: currentUserId,
//                 receiver: adminId,
//                 content: inputMessage,
//                 timestamp: new Date().toISOString(),
//             };
//             stompClient.send('/app/send', {}, JSON.stringify(message));
//             setInputMessage('');
//         }
//     };

//     return (
//         <div>
//             <h2>Support Chat</h2>
//             <div style={{ height: '300px', overflowY: 'scroll', border: '1px solid black' }}>
//                 {messages.map((msg, index) => (
//                     <div key={index}>
//                         <b>{msg.sender === currentUserId ? 'You' : 'Admin'}:</b> {msg.content}
//                     </div>
//                 ))}
//             </div>
//             <input
//                 type="text"
//                 value={inputMessage}
//                 onChange={(e) => setInputMessage(e.target.value)}
//                 placeholder="Type a message..."
//             />
//             <button onClick={sendMessage}>Send</button>
//         </div>
//     );
// };

// export default UserChat;
