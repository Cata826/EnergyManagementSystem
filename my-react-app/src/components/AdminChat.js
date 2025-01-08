import React, { useState, useEffect } from 'react';
import { Stomp } from '@stomp/stompjs';

const AdminChat = () => {
    const [users, setUsers] = useState([]); // List of users
    const [selectedUser, setSelectedUser] = useState(null); // Currently selected user
    const [messages, setMessages] = useState([]);
    const [inputMessage, setInputMessage] = useState('');
    const adminId = 'admin-id-uuid'; // Admin's fixed ID
    const [stompClient, setStompClient] = useState(null);

    // Connect to WebSocket
    useEffect(() => {
        const client = Stomp.client('http://localhost:8083/ws');
        client.connect({}, () => {
            console.log('Connected to WebSocket');
            client.subscribe('/topic/messages', (msg) => {
                const message = JSON.parse(msg.body);
                if (
                    (message.sender === adminId && message.receiver === selectedUser) ||
                    (message.sender === selectedUser && message.receiver === adminId)
                ) {
                    setMessages((prev) => [...prev, message]);
                }
            });
        });
        setStompClient(client);
        return () => client.disconnect();
    }, [adminId, selectedUser]);

    // Fetch users (mock for now)
    useEffect(() => {
        // Replace with API call to fetch users
        setUsers(['user1-id', 'user2-id', 'user3-id']);
    }, []);

    // Fetch conversation
    useEffect(() => {
        if (selectedUser) {
            fetch(`http://localhost:8083/chat/conversation?sender=${adminId}&receiver=${selectedUser}`)
                .then((res) => res.json())
                .then((data) => setMessages(data))
                .catch((err) => console.error('Error loading conversation:', err));
        }
    }, [adminId, selectedUser]);

    // Send a message
    const sendMessage = () => {
        if (stompClient && inputMessage.trim() && selectedUser) {
            const message = {
                sender: adminId,
                receiver: selectedUser,
                content: inputMessage,
                timestamp: new Date().toISOString(),
            };
            stompClient.send('/app/send', {}, JSON.stringify(message));
            setInputMessage('');
        }
    };

    return (
        <div>
            <h2>Admin Support Panel</h2>
            <div>
                <h3>Users:</h3>
                {users.map((user) => (
                    <div key={user} onClick={() => setSelectedUser(user)} style={{ cursor: 'pointer' }}>
                        {user}
                    </div>
                ))}
            </div>
            {selectedUser && (
                <>
                    <h3>Chat with {selectedUser}</h3>
                    <div style={{ height: '300px', overflowY: 'scroll', border: '1px solid black' }}>
                        {messages.map((msg, index) => (
                            <div key={index}>
                                <b>{msg.sender === adminId ? 'You' : 'User'}:</b> {msg.content}
                            </div>
                        ))}
                    </div>
                    <input
                        type="text"
                        value={inputMessage}
                        onChange={(e) => setInputMessage(e.target.value)}
                        placeholder="Type a message..."
                    />
                    <button onClick={sendMessage}>Send</button>
                </>
            )}
        </div>
    );
};

export default AdminChat;
