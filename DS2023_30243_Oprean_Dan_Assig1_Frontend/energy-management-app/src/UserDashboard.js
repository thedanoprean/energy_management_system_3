import React from "react";
import "./styles/UserDashboard.css";
import { Link, Route, Routes } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { useAuth } from "./AuthContext";
import DeviceList from "./DeviceList";
import ChatRoom from "./ChatRoom";

function UserDashboard() {
  const { user } = useAuth();
  const navigate = useNavigate();

  console.log("UserDashboard user:", user);

  const redirectToChat = () => {
    if (user && user.id) {
      navigate(`/user/${user.id}/chat`);
    } else {
      console.error("User or userId is undefined");
    }
  };

  const redirectToDevices = () => {
    if (user && user.id) {
      navigate(`/user/${user.id}/devices`);
    } else {
      console.error("User or userId is undefined");
    }
  };

  return (
    <div className="user-dashboard">
      <h2>Your Dashboard</h2>
      <div className="dashboard-buttons">
        <Link to={`/user/${user?.id}/devices`} className="dashboard-button">
          See Your Devices
        </Link>
        <Link to={`/user/${user?.id}/chat`} className="dashboard-button">
          Chat
        </Link>
      </div>

      <Routes>
        <Route path={`/user/${user?.id}/devices`} element={<DeviceList />} />
        <Route path={`/user/${user?.id}/chat`} element={<ChatRoom />} />
      </Routes>
    </div>
  );
}

export default UserDashboard;
