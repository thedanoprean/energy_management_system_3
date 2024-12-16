import axios from "axios";

const API_BASE_URL = "http://localhost:8088";

const api = axios.create({
  baseURL: API_BASE_URL,
});

// const apiDevices = axios.create({
//   baseURL: API_BASE_URL_DEVICES,
// });

export default api;
