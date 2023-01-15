import request from "@/utils/request";

export type chapterGradeGetForm = {
  username: string;
  course: string;
  token: string;
};

export type homeworkGradeGetForm = {
  username: string;
  course: string;
  token: string;
};

export type quizGradeGetForm = {
  username: string;
  course: string;
  token: string;
};

export const chapterGradeGetAPI = (data: chapterGradeGetForm) => {
  console.log("chapterGradeGetAPI");
  return request.post("/teacher/grade/chapter", data);
};

export const homeworkGradeGetAPI = (data: homeworkGradeGetForm) => {
  console.log("homeworkGradeGetAPI");
  return request.post("/teacher/grade/homework", data);
};

export const quizGradeGetAPI = (data: quizGradeGetForm) => {
  console.log("quizGradeGetAPI");
  return request.post("/teacher/grade/quiz", data);
};
