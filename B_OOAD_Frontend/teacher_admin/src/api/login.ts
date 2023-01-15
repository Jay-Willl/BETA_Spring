import request from "@/utils/request";

export type loginForm = {
  username: string;
  password: string;
  role: string;
};

export type registerForm = {
  user_name: string;
  user_passwd: string;
  user_email: string;
  user_role: string;
};

export const loginAPI = (data: loginForm) => {
  console.log("loginAPI");
  return request.post("/login", data);
};

export const registerAPI = (data: registerForm) => {
  console.log("registerAPI");
  return request.post("/register", data);
};
