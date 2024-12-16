import React, { useEffect, useState } from "react";
import ChatInput from "./ChatInput";
import { useNavigate } from "react-router-dom";
import { useAuth } from "./AuthContext";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

function ChatRoom() {
  const [messages, setMessages] = useState([]);
  const [isTyping, setIsTyping] = useState("");
  const [isConnected, setIsConnected] = useState(false);
  const navigate = useNavigate();
  const { user, logout } = useAuth();
  const [messageInput, setMessageInput] = useState("");

  let socket;
  let stompClient;

  const handleMessage = (message) => {
    if (isConnected) {
      console.log("Sending message:", message);
      const chatMessage = {
        sender: user.username,
        content: message,
        type: "CHAT",
      };
      // Asigurați-vă că stompClient este definit înainte de utilizare
      stompClient &&
        stompClient.send(
          "/app/chatSendMessage",
          {},
          JSON.stringify(chatMessage)
        );
    } else {
      console.error("WebSocket is not connected.");
    }
  };

  useEffect(() => {
    try {
      socket = new SockJS("http://localhost:8090/wss");
      stompClient = Stomp.over(socket);

      stompClient.connect({}, () => {
        setIsConnected(true);

        const joinMessage = {
          sender: user.username,
          type: "JOIN",
        };
        stompClient.send("/app/chatAddUser", {}, JSON.stringify(joinMessage));

        const joinPublicMessage = {
          sender: user.username,
          type: "JOIN",
        };
        stompClient.send(
          "/app/chatSendMessage",
          {},
          JSON.stringify(joinPublicMessage)
        );

        // Abonare la coada personală pentru notificări
        stompClient.subscribe("/user/queue/notification", (message) => {
          const receivedNotification = JSON.parse(message.body);
          console.log("Received notification:", receivedNotification);
          // Procesați notificarea aici
        });

        stompClient.subscribe("/topic/public", (message) => {
          const receivedMessage = JSON.parse(message.body);
          console.log("Received message:", receivedMessage);

          if (receivedMessage.type === "JOIN") {
            setMessages((prevMessages) => [
              ...prevMessages,
              {
                content: `${receivedMessage.sender} joined the chat`,
                type: "JOIN",
              },
            ]);
          } else {
            setMessages((prevMessages) => [
              ...prevMessages,
              {
                content: `${receivedMessage.content}`,
                type: "CHAT",
              },
            ]);
          }
        });
      });

      return () => {
        const chatMessage = {
          sender: user.username,
          type: "LEAVE",
        };
        stompClient &&
          stompClient.send(
            "/app/chatSendMessage",
            {},
            JSON.stringify(chatMessage)
          );
        stompClient && stompClient.disconnect();
      };
    } catch (error) {
      console.error("WebSocket connection error:", error);
    }
  }, [user.username, logout]);

  return (
    <div>
      <h2>Chat Room</h2>
      <button onClick={() => navigate(`/user/${user.id}`)}>Back</button>
      <div className="message-container">
        {messages.map((message, index) => (
          <div key={index} className="message">
            <p>
              <strong>{message.sender}:</strong> {message.content}
            </p>
          </div>
        ))}
      </div>

      <ChatInput onMessage={handleMessage} />
      <div>{isTyping}</div>
    </div>
  );
}

export default ChatRoom;
