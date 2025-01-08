import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

const SOCKET_URL = "http://localhost:8083/ws";

export const connectWebSocket = (onMessageReceived) => {
  const client = new Client({
    webSocketFactory: () => new SockJS(SOCKET_URL), // WebSocket or SockJS fallback
    connectHeaders: {},
    debug: (str) => {
      console.log("WebSocket Debug: ", str);
    },
    reconnectDelay: 5000, // Auto reconnect after 5 seconds
    onConnect: () => {
      console.log("Connected to WebSocket");

      // Subscribe to the topic to receive messages
      client.subscribe("/topic/messages", (message) => {
        const receivedMessage = JSON.parse(message.body);
        onMessageReceived(receivedMessage); // Handle received message
      });
    },
    onDisconnect: () => {
      console.log("Disconnected from WebSocket");
    },
  });

  client.activate();
  return client;
};
