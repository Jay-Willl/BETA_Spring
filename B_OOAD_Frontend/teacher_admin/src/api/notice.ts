import axios from "axios";

export const getNoticeList = () => {
  return axios.get("./noticeList.json");
};

import request from "@/utils/request";

export type noticeGetForm = {
  username: string;
  token: string;
  course: string;
};

export type noticeSubmitForm = {
  username: string;
  token: string;
  course: string;
  useEmail: string;
  data: string;
};

export const noticeGetAPI = (data: noticeGetForm) => {
  console.log("noticeGetAPI");
  return request.post("teacher/notice", data);
};

export const noticeSubmitAPI = (data: noticeSubmitForm) => {
  console.log("noticeSubmitAPI");
  return request.post("teacher/create/notice", data);
};
