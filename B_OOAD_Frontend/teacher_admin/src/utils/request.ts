import axios, { AxiosInstance } from "axios";

const URL = "http://localhost:8075/";

const service: AxiosInstance = axios.create({
  baseURL: URL,
  timeout: 5000,
  headers: {}
});

export default service;
