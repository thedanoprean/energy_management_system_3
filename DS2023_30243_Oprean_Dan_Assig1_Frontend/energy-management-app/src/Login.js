// Login.js
import React, { useState } from "react";
import api from "../src/api.js";
import { useNavigate } from "react-router-dom";
import "./styles/Login.css";
import { useAuth } from "./AuthContext.js";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await api.post("/auth/login", {
        username,
        password,
      });

      const token = response.data.token;
      const user = response.data; // sau preiați doar datele necesare ale utilizatorului
      login(token, user);

      const userId = response.data.id;

      if (response.data.role === "ADMIN") {
        navigate("/admin");
      } else {
        navigate(`/user/${userId}`);
      }
    } catch (error) {
      console.error("Authentication error:", error);
      setError("Username sau parolă introduse greșit");
    }
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
      {error && <div className="error-message">{error}</div>}
      <form className="login-form" onSubmit={handleLogin}>
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          placeholder="Username"
          required
        />
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Password"
          required
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
}

export default Login;
