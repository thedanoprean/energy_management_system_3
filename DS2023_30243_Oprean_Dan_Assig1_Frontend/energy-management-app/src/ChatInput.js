import React, { useState, useEffect } from "react";
import { useAuth } from "./AuthContext";

function ChatInput({ onMessage }) {
  const [message, setMessage] = useState("");
  const [isTyping, setIsTyping] = useState(false);
  const { user } = useAuth();

  let typingTimeout;

  const handleTyping = () => {
    if (!isTyping) {
      setIsTyping(true);
      onMessage({
        sender: user.username,
        type: "TYPING",
      });
    }

    clearTimeout(typingTimeout);

    typingTimeout = setTimeout(() => {
      setIsTyping(false);
      onMessage({
        sender: user.username,
        type: "STOP_TYPING",
      });
    }, 5000);
  };

  useEffect(() => {
    return () => {
      clearTimeout(typingTimeout);
      setIsTyping(false);
    };
  }, [isTyping, onMessage, user.username, typingTimeout]);

  const onClick = () => {
    // Modificați astfel încât să trimiteți mesajul doar dacă nu este gol
    if (message.trim() !== "") {
      onMessage(message);
      setMessage("");
    }
  };

  return (
    <div>
      <input
        type="text"
        placeholder="Type your message..."
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        onKeyPress={handleTyping}
      />
      <button onClick={onClick}>Send</button>
    </div>
  );
}

export default ChatInput;
