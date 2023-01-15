import request from "@/utils/request";

export type courseManageGetForm = {
  username: string;
  token: string;
};

export type courseManageSubmitForm = {
  username: string;
  token: string;
  course: string;
  status: string;
};

export type courseSubmitForm = {
  username: string;
  token: string;
  data: {
    title: string;
    cover: string;
    teacher: string;
    price: number;
    description: string;
    status: string;
    field: string; //新加
    tag: string; //新加
  };
};

export type courseGetForm = {
  username: string;
  token: string;
};

export type courseDeleteForm = {
  username: string;
  course: string;
  token: string;
};

export type chapterCreateForm = {
  username: string;
  token: string;
  course: string;
  data: {
    index: number;
    title: string;
  };
};

export type chapterGetForm = {
  username: string;
  token: string;
  course: string;
};

export type chapterDeleteForm = {
  username: string;
  token: string;
  course: string;
  chapter: number;
};

export type lessonGetForm = {
  username: string;
  token: string;
  course: string;
};

export type lessonCreateFrm = {
  username: string;
  token: string;
  course: string;
  chapter: number;
  data: {
    index: number;
    title: string;
    description: string;
    id: string;
    score: number;
  };
};

export const courseGetAPI = (data: courseGetForm) => {
  console.log("courseGetAPI");
  return request.post("teacher/course/all", data);
};

export const courseCreateAPI = (data: courseSubmitForm) => {
  console.log("courseCreateAPI");
  return request.post("teacher/create/course", data);
};

export const courseDeleteAPI = (data: courseDeleteForm) => {
  console.log("courseDeleteAPI");
  return request.post("teacher/delete/course", data);
};

export const chapterGetAPI = (data: chapterGetForm) => {
  console.log("chapterGetAPI");
  return request.post("/teacher/chapter", data);
};

export const chapterCreateAPI = (data: chapterCreateForm) => {
  console.log("chapterCreateAPI");
  return request.post("teacher/create/chapter", data);
};

export const chapterDeleteAPI = (data: chapterDeleteForm) => {
  console.log("chapterDeleteAPI");
  return request.post("teacher/delete/chapter", data);
};

export const lessonGetAPI = (data: lessonGetForm) => {
  console.log("lessonGetAPI");
  return request.post("teacher/lesson", data);
};

export const lessonCreateAPI = (data: lessonCreateFrm) => {
  console.log("lessonCreateAPI");
  return request.post("teacher/create/lesson", data);
};

export const courseManageGetAPI = (data: courseManageGetForm) => {
  console.log("courseManageGetAPI");
  return request.post("admin/course", data);
};

export const courseManageSubmitAPI = (data: courseManageSubmitForm) => {
  console.log("courseManageSubmitAPI");
  return request.post("admin/manage/course", data);
};

export const videoViewAPI = (data: string) => {
  console.log("videoViewAPI");
  return request.get(`user/video/${data}`);
};
