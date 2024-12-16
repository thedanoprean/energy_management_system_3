import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./Login";
import AdminDashboard from "./AdminDashboard";
import UserDashboard from "./UserDashboard";
import CRUDUsers from "./CRUDUsers";
import CRUDDevices from "./CRUDDevices";
import SeeUsers from "./UserCRUD/SeeUsers";
import CreateUser from "./UserCRUD/CreateUser";
import DeleteUser from "./UserCRUD/DeleteUser";
import UserUpdate from "./UserCRUD/UserUpdate";
import SeeDevices from "./DeviceCRUD/SeeDevices";
import CreateDevice from "./DeviceCRUD/CreateDevice";
import DeleteDevice from "./DeviceCRUD/DeleteDevice";
import UpdateDevice from "./DeviceCRUD/UpdateDevice";
import DeviceList from "./DeviceList";
import ChatRoom from "./ChatRoom";
import { AuthProvider } from "./AuthContext";

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/admin" element={<AdminDashboard />} />
          <Route path="/admin/chat" element={<ChatRoom />} />
          <Route path="/user/:user_id" element={<UserDashboard />} />
          <Route path="/user/:user_id/chat" element={<ChatRoom />} />
          <Route path="/user/:user_id/devices" element={<DeviceList />} />
          <Route path="/" element={<Login />} />
          <Route path="/admin/crud-users" element={<CRUDUsers />} />
          <Route path="/admin/crud-devices" element={<CRUDDevices />} />
          <Route path="/admin/crud-users/see-users" element={<SeeUsers />} />
          <Route
            path="/admin/crud-users/create-user"
            element={<CreateUser />}
          />
          <Route
            path="/admin/crud-users/delete-user"
            element={<DeleteUser />}
          />
          <Route
            path="/admin/crud-users/update-user"
            element={<UserUpdate />}
          />
          <Route
            path="/admin/crud-devices/see-devices"
            element={<SeeDevices />}
          />
          <Route
            path="/admin/crud-devices/create-device"
            element={<CreateDevice />}
          />
          <Route
            path="/admin/crud-devices/delete-device"
            element={<DeleteDevice />}
          />
          <Route
            path="/admin/crud-devices/update-device"
            element={<UpdateDevice />}
          />
        </Routes>
      </Router>
    </AuthProvider>
  );
}
export default App;
