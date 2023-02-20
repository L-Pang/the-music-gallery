import axios from "axios";

// create an axios instance
const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API,
    timeout: 50000,
    headers: {
        "Content-Type": "application/json;charset=utf-8",
    },
});

service.interceptors.request.use(
    (config) => {
        return config
    },
    (error) => {
        return Promise.reject(error);
    }
);

service.interceptors.response.use(
    (response) => {
        const res = response.data;
        return res;
    },
    (error) => {
        return Promise.reject(error);

    }
);

export default service;