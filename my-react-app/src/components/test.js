// import React, { useState, useEffect, useRef } from "react";
// import axios from "axios";
// import { Client } from "@stomp/stompjs";
// import SockJS from "sockjs-client";

// const Chat = () => {
//   const [conversations, setConversations] = useState({});
//   const [currentReceiver, setCurrentReceiver] = useState("");
//   const [input, setInput] = useState("");
//   const [receiverTyping, setReceiverTyping] = useState(false);
//   const [connected, setConnected] = useState(false);
//   const [users, setUsers] = useState([]);
//   const [currentUser, setCurrentUser] = useState({ id: "", isAdmin: false });
//   const [stompClient, setStompClient] = useState(null);
//   const messagesEndRef = useRef(null);
//   const typingTimeoutRef = useRef(null);

//   const socketUrl = "http://localhost:8083/ws";
//   const baseUrl = "http://localhost:8083/chat";

//   const ADMIN_ID = "f9ec1a5b-c0d3-4d40-b003-6bb4feee8f2a";

//   // useEffect(() => {
//   //   const userId = localStorage.getItem("id");
//   //   const isAdmin = localStorage.getItem("admin") === "true";
    

//   //   if (!userId) {
//   //     console.error("User ID is missing! Please log in.");
//   //     return;
//   //   }

//   //   setCurrentUser({ id: userId, isAdmin });

//   //   if (isAdmin) {
//   //     axios
//   //       .get("http://localhost:8080/person")
//   //       .then((res) => setUsers(res.data))
//   //       .catch((err) => console.error("Error fetching users:", err));
//   //   } else {
//   //     loadConversation(ADMIN_ID);
//   //   }
//   // }, []);
//   useEffect(() => {
//     const userId = localStorage.getItem("id");
//     const isAdmin = localStorage.getItem("admin") === "true";
//     const token = localStorage.getItem("token"); // Retrieve the token from localStorage
  
//     if (!userId) {
//       console.error("User ID is missing! Please log in.");
//       return;
//     }
  
//     setCurrentUser({ id: userId, isAdmin });
  
//     if (isAdmin) {
//       axios
//         .get("http://localhost:8080/person", {
//           headers: {
//             Authorization: `Bearer ${token}`, // Add the Bearer token here
//           },
//         })
//         .then((res) => setUsers(res.data))
//         .catch((err) => console.error("Error fetching users:", err));
//     } else {
//       loadConversation(ADMIN_ID);
//     }
//   }, []);
  
//   useEffect(() => {
//     const socket = new SockJS(socketUrl);
//     const client = new Client({
//       webSocketFactory: () => socket,
//       reconnectDelay: 5000,
//     });

//     client.onConnect = () => {
//       setConnected(true);
//       console.log("WebSocket connected");

//       client.subscribe("/topic/messages", (message) => {
//         const parsedMessage = JSON.parse(message.body);

//         if (
//           parsedMessage.sender === currentUser.id &&
//           parsedMessage.content === input
//         ) {
//           return;
//         }

//         setConversations((prev) => {
//           const updatedConversations = { ...prev };

//           if (!updatedConversations[parsedMessage.sender]) {
//             updatedConversations[parsedMessage.sender] = [];
//           }
//           updatedConversations[parsedMessage.sender].push(parsedMessage);

//           if (!updatedConversations[parsedMessage.receiver]) {
//             updatedConversations[parsedMessage.receiver] = [];
//           }
//           updatedConversations[parsedMessage.receiver].push(parsedMessage);

//           return updatedConversations;
//         });

//         messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
//       });

//       client.subscribe("/topic/typing", (message) => {
//         const parsedMessage = JSON.parse(message.body);

//         if (
//           parsedMessage.sender === currentReceiver &&
//           parsedMessage.receiver === currentUser.id
//         ) {
//           setReceiverTyping(true);
//           clearTimeout(typingTimeoutRef.current);
//           typingTimeoutRef.current = setTimeout(
//             () => setReceiverTyping(false),
//             1500
//           );
//         }
//       });
//       if (stompClient) {
//         stompClient.subscribe("/topic/message-read", (message) => {
//           const { messageId, sender } = JSON.parse(message.body);
    
//           setConversations((prev) => {
//             const updatedConversations = { ...prev };
    
//             // Update the specific message as "Seen"
//             if (updatedConversations[sender]) {
//               updatedConversations[sender] = updatedConversations[sender].map((msg) =>
//                 msg.id === messageId ? { ...msg, read: true } : msg
//               );
//             }
    
//             return updatedConversations;
//           });
//         });
//       }
//       client.subscribe(`/user/queue/messages`, (message) => {
//         const parsedMessage = JSON.parse(message.body);
      

//         const isRelevantMessage = currentUser.isAdmin 
//           ? true 
//           : parsedMessage.receiver === localStorage.getItem("id"); 
      
//         if (!isRelevantMessage) return; 
//         if (
//           parsedMessage.sender === currentUser.id &&
//           parsedMessage.content === input
//         ) {
//           return;
//         }
      
//         setConversations((prev) => {
//           const updatedConversations = { ...prev };

//           const conversationKey = currentUser.isAdmin ? currentReceiver : ADMIN_ID;
      
//           if (!updatedConversations[conversationKey]) {
//             updatedConversations[conversationKey] = [];
//           }
//           updatedConversations[conversationKey].push(parsedMessage);
      
//           return updatedConversations;
//         });
      
//         messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
//       });
      
      
//     };

//     client.onStompError = (error) => {
//       console.error("WebSocket connection error:", error);
//     };

//     client.activate();
//     setStompClient(client);

//     return () => {
//       if (client) client.deactivate();
//     };
//   }, [currentUser, input]);
//   const markMessagesAsSeen = (messages) => {
//     messages.forEach((message) => {
//       if (!message.read) {
//         axios
//           .patch(`${baseUrl}/messages/${message.id}/read`)
//           .then(() => {
//             if (stompClient && connected) {
//               stompClient.publish({
//                 destination: "/app/message-read",
//                 body: JSON.stringify({
//                   messageId: message.id,
//                   sender: message.sender,
//                   receiver: currentUser.id,
//                 }),
//               });
//             }
//           })
//           .catch((err) => console.error("Failed to mark message as seen:", err));
//       }
//     });
//   };
  
  
//   // const loadConversation = (receiver) => {
//   //   setCurrentReceiver(receiver);
  
//   //   if (!receiver || !currentUser.id) {
//   //     console.error("Missing sender or receiver!");
//   //     return;
//   //   }
  
//   //   const sender = currentUser.isAdmin ? ADMIN_ID : currentUser.id;
  
//   //   axios
//   //     .get(`${baseUrl}/conversation`, {
//   //       params: { sender, receiver },
//   //     })
//   //     .then((res) => {
//   //       const filteredMessages = res.data.filter(
//   //         (msg) => msg.sender === ADMIN_ID || msg.receiver === ADMIN_ID
//   //       );
//   //       markMessagesAsRead(filteredMessages);      
//   //       setConversations((prev) => ({
//   //         ...prev,
//   //         [receiver]: filteredMessages,
//   //       }));
//   //     })
//   //     .catch((err) => console.error("Error loading conversation:", err));
//   // };
//   const loadConversation = (receiver) => {
//     setCurrentReceiver(receiver);
  
//     if (!receiver || !currentUser.id) {
//       console.error("Missing sender or receiver!");
//       return;
//     }
  
//     const sender = currentUser.isAdmin ? ADMIN_ID : currentUser.id;
  
//     axios
//       .get(`${baseUrl}/conversation`, {
//         params: { sender, receiver },
//         headers: {
//           Authorization: `Bearer ${localStorage.getItem("token")}`,
//         },
//       })
//       .then((res) => {
//         const messages = res.data;
  
//         // Mark messages as read locally
//         const updatedMessages = messages.map((msg) =>
//           msg.receiver === currentUser.id ? { ...msg, read: true } : msg
//         );
  
//         setConversations((prev) => ({
//           ...prev,
//           [receiver]: updatedMessages,
//         }));
  
//         // Notify backend about the messages being read
//         markMessagesAsSeen(messages.filter((msg) => !msg.read && msg.receiver === currentUser.id));
//       })
//       .catch((err) => {
//         console.error("Error loading conversation:", err);
//       });
//   };
  
//   // const loadConversation = (receiver) => {
//   //   setCurrentReceiver(receiver);
  
//   //   if (!receiver || !currentUser.id) {
//   //     console.error("Missing sender or receiver!");
//   //     return;
//   //   }
  
//   //   const sender = currentUser.isAdmin ? ADMIN_ID : currentUser.id;
  
//   //   axios
//   //     .get(`${baseUrl}/conversation`, {
//   //       params: { sender, receiver },
//   //     })
//   //     .then((res) => {
//   //       const messages = res.data;
  
//   //       // Mark messages as read locally
//   //       const updatedMessages = messages.map((msg) =>
//   //         msg.receiver === currentUser.id ? { ...msg, read: true } : msg
//   //       );
  
//   //       setConversations((prev) => ({
//   //         ...prev,
//   //         [receiver]: updatedMessages,
//   //       }));
  
//   //       // Notify backend about the messages being read
//   //       markMessagesAsSeen(messages.filter((msg) => !msg.read && msg.receiver === currentUser.id));
//   //     })
//   //     .catch((err) => console.error("Error loading conversation:", err));
//   // };
  
//   const sendMessage = () => {
//     if (!input.trim()) return;
  
//     const message = {
//       sender: currentUser.isAdmin ? ADMIN_ID : currentUser.id,
//       receiver: currentUser.isAdmin ? currentReceiver : ADMIN_ID,
//       content: input,
//       timestamp: new Date().toISOString(),
//     };
  
//     setInput(""); // Clear input field
  
//     if (stompClient && connected) {
//       stompClient.publish({
//         destination: "/app/send",
//         body: JSON.stringify(message),
//       });
  
//       stompClient.publish({
//         destination: "/app/typing",
//         body: JSON.stringify({
//           sender: currentUser.id,
//           receiver: currentUser.isAdmin ? currentReceiver : ADMIN_ID,
//           typing: false,
//         }),
//       });
//     } else {
//       console.warn("STOMP client is not connected. Cannot send message.");
//     }
  
//     setConversations((prev) => {
//       const updatedConversations = { ...prev };
  
//       if (!updatedConversations[message.sender]) {
//         updatedConversations[message.sender] = [];
//       }
//       updatedConversations[message.sender].push(message);
  
//       if (!updatedConversations[message.receiver]) {
//         updatedConversations[message.receiver] = [];
//       }
//       updatedConversations[message.receiver].push(message);
  
//       return updatedConversations;
//     });
//   };
  
//   const handleTyping = (e) => {
//     const newInput = e.target.value;
//     setInput(newInput);
  
//     const receiverId = currentUser.isAdmin ? currentReceiver : ADMIN_ID;
  
//     if (stompClient && connected && stompClient.active) {
//       stompClient.publish({
//         destination: "/app/typing",
//         body: JSON.stringify({
//           sender: currentUser.id,
//           receiver: receiverId,
//           typing: newInput.trim() !== "",
//         }),
//       });
//     } else {
//       console.warn("STOMP client is not connected. Cannot publish typing event.");
//     }
//   };
  
//   useEffect(() => {
//     if (currentReceiver) {
//       loadConversation(currentReceiver);
//     }
//   }, [currentReceiver]);

//   useEffect(() => {
//     messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
//   }, [conversations, currentReceiver]);

//   return (
//     <div style={{ display: "flex", height: "100vh" }}>
//       {currentUser.isAdmin && (
//         <div style={{ width: "20%", borderRight: "1px solid #ccc", padding: "10px" }}>
//           <h3>Users</h3>
//           {users.map((user) => (
//             <div
//               key={user.id}
//               onClick={() => setCurrentReceiver(user.id)}
//               style={{
//                 padding: "10px",
//                 cursor: "pointer",
//                 background: currentReceiver === user.id ? "#e0e0e0" : "transparent",
//               }}
//             >
//               {user.name}
//             </div>
//           ))}
//         </div>
//       )}

//       <div style={{ flex: 1, padding: "10px", display: "flex", flexDirection: "column" }}>
//         <h3>
//           {currentUser.isAdmin
//             ? `Chat with ${currentReceiver || "Select a User"}`
//             : "Chat with Admin"}
//         </h3>
//           <div style={{ flex: 1, overflowY: "auto", border: "1px solid #ccc", padding: "10px" }}>
//           {conversations[currentUser.isAdmin ? currentReceiver : ADMIN_ID]?.map((msg) => {
//   // Filter Admin content to only show if receiver matches currentUser.id
//   if (
//     msg.sender === ADMIN_ID &&
//     msg.receiver !== currentUser.id &&
//     !currentUser.isAdmin
//   ) {
//     return null; // Skip messages not meant for the current user
//   }

//   const isSender = msg.sender === currentUser.id;
//   return (
//     <div
//       key={msg.timestamp}
//       style={{
//         display: "flex",
//         justifyContent: isSender ? "flex-end" : "flex-start",
//         margin: "5px 0",
//       }}
//     >
//       <div
//         style={{
//           display: "inline-block",
//           padding: "10px",
//           borderRadius: "10px",
//           backgroundColor: isSender ? "#d4f8d4" : "#f0f0f0",
//         }}
//       >
//         <strong>
//           {isSender ? "You" : msg.sender === ADMIN_ID ? "Admin" : "User"}:
//         </strong>{" "}
//         {msg.content}
//         <div style={{ fontSize: "0.8rem", color: msg.read ? "green" : "red" }}>
//           {msg.read ? "Seen" : "Delivered"}
//         </div>
//       </div>
//     </div>
//   );
// })}

//           {/* {conversations[currentUser.isAdmin ? currentReceiver : ADMIN_ID]?.map((msg) => {
//   const isSender = msg.sender === currentUser.id;
//   return (
//     <div
//       key={msg.timestamp}
//       style={{
//         display: "flex",
//         justifyContent: isSender ? "flex-end" : "flex-start",
//         margin: "5px 0",
//       }}
//     >
//       <div
//         style={{
//           display: "inline-block",
//           padding: "10px",
//           borderRadius: "10px",
//           backgroundColor: isSender ? "#d4f8d4" : "#f0f0f0",
//         }}
//       >
//         <strong>
//           {isSender ? "You" : msg.sender === ADMIN_ID ? "Admin" : "User"}:
//         </strong>{" "}
//         {msg.content}
//         <div style={{ fontSize: "0.8rem", color: msg.read ? "green" : "red" }}>
//           {msg.read ? "Seen" : "Delivered"}
//         </div>
//       </div>
//     </div>
//   );
// })} */}

//           {receiverTyping && (
//             <div>
//               <em>{currentUser.isAdmin ? "User is typing..." : "Admin is typing..."}</em>
//             </div>
//           )}
//           <div ref={messagesEndRef} />
//         </div>
//               <div style={{ display: "flex" }}>
//               <input
//         type="text"
//         value={input}
//         onChange={handleTyping}
//         disabled={currentUser.isAdmin && !currentReceiver} 
//         style={{
//           flex: 1,
//           padding: "10px",
//           border: "1px solid #ccc",
//           marginRight: "10px",
//           backgroundColor: currentUser.isAdmin && !currentReceiver ? "#f5f5f5" : "white",
//         }}
//       />
//       <button
//         onClick={sendMessage}
//         disabled={currentUser.isAdmin && !currentReceiver} // Disable if admin and no user selected
//         style={{
//           padding: "10px",
//           cursor: currentUser.isAdmin && !currentReceiver ? "not-allowed" : "pointer",
//         }}
//       >
//         Send
//       </button>

//               </div>
//             </div>
//           </div>
//         );
//       };

// export default Chat;
