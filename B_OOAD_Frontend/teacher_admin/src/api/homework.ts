import request from "@/utils/request";

export type homeworkGetForm = {
  username: string;
  token: string;
  course: string;
};

export type homeworkSubmitForm = {
  username: string;
  token: string;
  course: string;
  index: number;
  data: string;
};

export type quizGetForm = {
  username: string;
  token: string;
  course: string;
};

export type quizSubmitForm = {
  username: string;
  token: string;
  course: string;
};

export type homeworkCheckForm = {
  username: string;
  token: string;
  course: string;
  index: number;
};

export type homeworkReviewForm = {
  username: string;
  token: string;
  course: string;
  index: number;
  name: string;
  score: number;
  commit: string;
};

export const homeworkCheckAPI = (data: homeworkCheckForm) => {
  console.log("homeworkCheckAPI");
  return request.post("/teacher/check/homework", data);
};

export const homeworkReviewAPI = (data: homeworkReviewForm) => {
  console.log("homeworkReviewAPI");
  return request.post("/teacher/review/homework", data);
};

export const homeworkGetAPI = (data: homeworkGetForm) => {
  console.log("homeworkGetAPI");
  return request.post("/teacher/homework", data);
};

export const homeworkSubmitAPI = (data: homeworkSubmitForm) => {
  console.log("homeworkSubmitAPI");
  return request.post("teacher/create/homework", data);
};

export const quizGetAPI = (data: quizGetForm) => {
  console.log("quizGetAPI");
  return request.post("/teacher/quiz", data);
};

export const quizSubmitAPI = (data: quizSubmitForm) => {
  console.log("quizSubmitAPI");
  return request.post("teacher/create/quiz", data);
};
