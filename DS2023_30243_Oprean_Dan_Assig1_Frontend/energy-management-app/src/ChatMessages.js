import React from "react";

function ChatMessages({ messages }) {
  return (
    <div>
      {messages.map((message, index) => (
        <div key={index}>
          <p>
            <strong>{message.sender}:</strong> {message.content}
          </p>
        </div>
      ))}
    </div>
  );
}

export default ChatMessages;
