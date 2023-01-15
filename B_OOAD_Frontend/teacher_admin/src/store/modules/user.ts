import { defineStore } from "pinia";
import { store } from "@/store";
import { routerArrays } from "@/layout/types";
import { router, resetRouter } from "@/router";
import { getLogin, refreshTokenApi } from "@/api/user";
import { UserResult, RefreshTokenResult } from "@/api/user";
import { useMultiTagsStoreHook } from "@/store/modules/multiTags";
import { setToken, removeToken } from "@/utils/auth";

export const useUserStore = defineStore({
  id: "pure-user",
  state: (): {
    chapter: string;
    roles: any[];
    course: string;
    username: string;
    token: string;
  } => ({
    // 用户名
    username: "",
    // 页面级别权限
    roles: [],
    // course, chapter
    course: "",
    chapter: "",
    token: ""
  }),
  actions: {
    /** 存储用户名 */
    SET_USERNAME(username: string) {
      console.log("set username", username);
      this.username = username;
    },
    /** 存储角色 */
    SET_ROLES(roles: Array<string>) {
      console.log("set role", roles);
      this.roles = roles;
    },
    SET_COURSE(course: string) {
      console.log("set course", course);
      this.course = course;
    },
    SET_CHAPTER(chapter: string) {
      this.chapter = chapter;
    },
    SET_TOKEN(token: string) {
      console.log("set token", token);
      this.token = token;
    },
    /** 登入 */
    async loginByUsername(data) {
      return new Promise<UserResult>((resolve, reject) => {
        getLogin(data)
          .then(data => {
            if (data) {
              setToken(data.data);
              resolve(data);
            }
          })
          .catch(error => {
            reject(error);
          });
      });
    },
    /** 前端登出（不调用接口） */
    logOut() {
      this.username = "";
      this.roles = [];
      removeToken();
      useMultiTagsStoreHook().handleTags("equal", [...routerArrays]);
      resetRouter();
      router.push("/login");
    },
    /** 刷新`token` */
    async handRefreshToken(data) {
      return new Promise<RefreshTokenResult>((resolve, reject) => {
        refreshTokenApi(data)
          .then(data => {
            if (data) {
              setToken(data.data);
              resolve(data);
            }
          })
          .catch(error => {
            reject(error);
          });
      });
    }
  }
});

export function useUserStoreHook() {
  return useUserStore(store);
}
