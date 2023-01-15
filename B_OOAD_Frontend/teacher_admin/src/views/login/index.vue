<script setup lang="ts">
import Motion from "./utils/motion";
import { useRouter } from "vue-router";
import { message } from "@/utils/message";
import { loginRules, registerRules } from "./utils/rule";
import { initRouter } from "@/router/utils";
import { useNav } from "@/layout/hooks/useNav";
import type { FormInstance } from "element-plus";
import { useLayout } from "@/layout/hooks/useLayout";
import { useUserStoreHook } from "@/store/modules/user";
import { bg, avatar, illustration } from "./utils/static";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import { ref, reactive, toRaw, onMounted, onBeforeUnmount } from "vue";
import { useDataThemeChange } from "@/layout/hooks/useDataThemeChange";

import dayIcon from "@/assets/svg/day.svg?component";
import darkIcon from "@/assets/svg/dark.svg?component";
import Lock from "@iconify-icons/ri/lock-fill";
import User from "@iconify-icons/ri/user-3-fill";

import { loginAPI, registerAPI } from "@/api/login";
import { setToken } from "@/utils/auth";

defineOptions({
  name: "Login"
});

const router = useRouter();
const loading = ref(false);
const ruleFormRef = ref<FormInstance>();

const { initStorage } = useLayout();
initStorage();

const { dataTheme, dataThemeChange } = useDataThemeChange();
dataThemeChange();
const { title } = useNav();

const ruleForm = reactive({
  username: "admin",
  password: "admin123",
  role: "teacher"
});

const returnForm = reactive({
  data: {
    username: "",
    roles: [],
    accessToken: "",
    refreshToken: "",
    expires: new Date("2023/10/30 00:00:00")
  }
});

const onLogin = async (formEl: FormInstance | undefined) => {
  console.log(formEl);
  console.log(ruleForm);
  loading.value = true;
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log("valid");
      loginAPI(ruleForm).then(res => {
        console.log("login res", res.data);
        if (res.data !== "*wrong password" && res.data !== "*no such user") {
          console.log("login success");
          console.log("username", ruleForm.username);
          console.log("role", ruleForm.role);
          console.log("token", res.data);
          useUserStoreHook().SET_USERNAME(ruleForm.username);
          useUserStoreHook().SET_ROLES([ruleForm.role]);
          useUserStoreHook().SET_TOKEN(res.data);
          localStorage.setItem("username", ruleForm.username);
          localStorage.setItem("roles", ruleForm.role);
          localStorage.setItem("token", res.data);
          returnForm.data.username = ruleForm.username;
          returnForm.data.roles.push(ruleForm.role);
          returnForm.data.accessToken = res.data;
          returnForm.data.refreshToken = res.data;
          setToken(returnForm.data);
          initRouter().then(() => {
            console.log("init route");
            router.push("/");
            message("登录成功", { type: "success" });
          });
        } else {
          message(res.data, { type: "error" });
          loading.value = false;
        }
      });
    } else {
      loading.value = false;
      return fields;
    }
  });
};

/** 使用公共函数，避免`removeEventListener`失效 */
function onkeypress({ code }: KeyboardEvent) {
  if (code === "Enter") {
    onLogin(ruleFormRef.value);
  }
}

onMounted(() => {
  window.document.addEventListener("keypress", onkeypress);
});

onBeforeUnmount(() => {
  window.document.removeEventListener("keypress", onkeypress);
});

const registerVisible = ref(false);

const registerForm = reactive({
  user_name: "",
  user_passwd: "",
  user_email: "",
  user_role: "teacher"
});

const onRegister = async () => {
  console.log("registerForm", registerForm);
  await registerAPI(registerForm).then(res => {
    console.log("register response", res.data);
    if (res.data === "ok") {
      message("注册成功", { type: "success" });
      registerVisible.value = false;
      registerForm.user_name = "";
      registerForm.user_passwd = "";
      registerForm.user_email = "";
    } else {
      message(res.data, { type: "error" });
    }
  });
};
</script>

<template>
  <div>
    <el-dialog v-model="registerVisible" title="注册" width="30%">
      <el-form
        :model="registerForm"
        :rules="registerRules"
        label-position="top"
      >
        <el-form-item label="Username" prop="user_name">
          <el-input v-model="registerForm.user_name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="E-Mail" prop="user_email">
          <el-input v-model="registerForm.user_email" autocomplete="off" />
        </el-form-item>
        <el-form-item label="Password" prop="user_passwd">
          <el-input v-model="registerForm.user_passwd" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="onRegister" size="large" round> 注册 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>

  <div class="select-none">
    <img :src="bg" class="wave" />
    <div class="flex-c absolute right-5 top-3">
      <!-- 主题 -->
      <el-switch
        v-model="dataTheme"
        inline-prompt
        :active-icon="dayIcon"
        :inactive-icon="darkIcon"
        @change="dataThemeChange"
      />
    </div>
    <div class="login-container">
      <div class="img">
        <component :is="toRaw(illustration)" />
      </div>
      <div class="login-box">
        <div class="login-form">
          <avatar class="avatar" />
          <Motion>
            <h2 class="outline-none">{{ title }}</h2>
          </Motion>

          <el-form
            ref="ruleFormRef"
            :model="ruleForm"
            :rules="loginRules"
            size="large"
          >
            <Motion :delay="100">
              <el-form-item
                :rules="[
                  {
                    required: true,
                    message: '请输入账号',
                    trigger: 'blur'
                  }
                ]"
                prop="username"
              >
                <el-input
                  clearable
                  v-model="ruleForm.username"
                  placeholder="账号"
                  :prefix-icon="useRenderIcon(User)"
                />
              </el-form-item>
            </Motion>

            <Motion :delay="150">
              <el-form-item prop="password">
                <el-input
                  clearable
                  show-password
                  v-model="ruleForm.password"
                  placeholder="密码"
                  :prefix-icon="useRenderIcon(Lock)"
                />
              </el-form-item>
            </Motion>

            <Motion :delay="250">
              <div>
                <div>
                  <el-button
                    class="w-full mt-4"
                    size="default"
                    type="primary"
                    :loading="loading"
                    @click="onLogin(ruleFormRef)"
                  >
                    登录
                  </el-button>
                </div>
                <div>
                  <el-button
                    class="w-full mt-4"
                    size="default"
                    :loading="loading"
                    @click="registerVisible = true"
                  >
                    注册
                  </el-button>
                </div>
              </div>
            </Motion>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url("@/style/login.css");
</style>

<style lang="scss" scoped>
:deep(.el-input-group__append, .el-input-group__prepend) {
  padding: 0;
}
</style>
