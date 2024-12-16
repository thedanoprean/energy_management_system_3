// DeviceList.js
import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import "./styles/device/DeviceList.css"; // Importăm fișierul CSS

function DeviceList() {
  const { user_id } = useParams();
  const [devices, setDevices] = useState([]);

  useEffect(() => {
    const fetchDevices = async () => {
      try {
        const response = await axios.get(
          `${process.env.REACT_APP_DEVICEHOST}/device/users/${user_id}`
        );
        setDevices(response.data);
      } catch (error) {
        console.error("Error fetching device list:", error);
      }
    };

    fetchDevices();
  }, [user_id]);

  return (
    <div className="device-list">
      <h2>Your Devices</h2>
      <ul>
        {devices.map((device) => (
          <li key={device.id}>
            <strong>Description:</strong> {device.description}
            <br />
            <strong>Address:</strong> {device.address}
            <br />
            <strong>Consumption:</strong> {device.consumption}
            <hr />
          </li>
        ))}
      </ul>
    </div>
  );
}

export default DeviceList;
