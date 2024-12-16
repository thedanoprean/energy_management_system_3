// ProtectedRoute.js
import { Route, Navigate } from "react-router-dom";
import { useAuth } from "./AuthContext";

const ProtectedRoute = ({ component: Component, ...rest }) => {
  const { user } = useAuth();

  return (
    <Route
      {...rest}
      element={
        user ? <Component {...rest} /> : <Navigate to="/login" replace />
      }
    />
  );
};

export default ProtectedRoute;
