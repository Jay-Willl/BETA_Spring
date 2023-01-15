<script setup lang="ts">
import { computed, PropType } from "vue";
import shopIcon from "@/assets/svg/shop.svg";
import laptopIcon from "@/assets/svg/laptop.svg";
import serviceIcon from "@/assets/svg/service.svg";
import calendarIcon from "@/assets/svg/calendar.svg";
import userAvatarIcon from "@/assets/svg/user_avatar.svg";
import More2Fill from "@iconify-icons/ri/more-2-fill";
import { useUserStoreHook } from "@/store/modules/user";
import { useRouter } from "vue-router";

defineOptions({
  name: "CourseCard"
});

interface CardCourseType {
  course_title: string;
  course_cover: string;
  course_teacher: string;
  course_price: number;
  course_description: string;
  course_status: string;
}

const props = defineProps({
  courseInfo: {
    type: Object as PropType<CardCourseType>
  }
});

const router = useRouter();

const emit = defineEmits(["manage-course", "delete-course"]);

const handleClickManage = (courseInfo: CardCourseType) => {
  emit("manage-course", courseInfo);
};

const handleClickDelete = (courseInfo: CardCourseType) => {
  emit("delete-course", courseInfo);
  console.log("delete courseInfo", courseInfo);
};

const goChapter = () => {
  console.log("进入");
  console.log(props.courseInfo.course_title);
  useUserStoreHook().SET_COURSE(props.courseInfo.course_title);
  localStorage.setItem("course", props.courseInfo.course_title);
  router.push({ name: "Chapter" });
};

const goNotice = () => {
  console.log("通知");
  useUserStoreHook().SET_COURSE(props.courseInfo.course_title);
  localStorage.setItem("course", props.courseInfo.course_title);
  router.push({ name: "Notice" });
};

const goHomework = () => {
  console.log("作业");
  useUserStoreHook().SET_COURSE(props.courseInfo.course_title);
  localStorage.setItem("course", props.courseInfo.course_title);
  router.push({ name: "Homework" });
};

const goGrade = () => {
  console.log("成绩");
  useUserStoreHook().SET_COURSE(props.courseInfo.course_title);
  localStorage.setItem("course", props.courseInfo.course_title);
  router.push({ name: "Grade" });
};

const cardClass = computed(() => [
  "list-card-item",
  { "list-card-item__disabled": props.courseInfo.course_status !== "success" }
]);

const cardLogoClass = computed(() => [
  "list-card-item_detail--logo",
  {
    "list-card-item_detail--logo__disabled":
      props.courseInfo.course_status !== "success"
  }
]);
</script>

<template>
  <div :class="cardClass">
    <div class="list-card-item_detail bg-bg_color">
      <el-row justify="space-between">
        <div :class="cardLogoClass">
          <serviceIcon />
        </div>
        <div class="list-card-item_detail--operation">
          <el-tag
            :color="courseInfo.course_status === 'success' ? '#00a870' : '#eee'"
            effect="dark"
            class="mx-1 list-card-item_detail--operation--tag"
          >
            {{ courseInfo.course_status === "success" ? "审核通过" : (courseInfo.course_status === "checking" ? "审核中" : (courseInfo.course_status === "delete" ? "已删除" : "失败")) }}
          </el-tag>
          <el-dropdown
            trigger="click"
            :disabled="courseInfo.course_status !== 'success'"
          >
            <IconifyIconOffline :icon="More2Fill" class="text-[24px]" />
            <template #dropdown>
              <el-dropdown-item
                :disabled="courseInfo.course_status !== 'success'"
                @click="handleClickDelete(courseInfo)"
              >
                删除
              </el-dropdown-item>
            </template>
          </el-dropdown>
        </div>
      </el-row>
      <p class="list-card-item_detail--name text-text_color_primary">
        {{ courseInfo.course_title }}
      </p>
      <p class="list-card-item_detail--desc text-text_color_regular">
        {{ courseInfo.course_description }}
      </p>
      <el-row :gutter="20">
        <el-col :span="6"><div class="grid-content ep-bg-purple" />
          <el-button
            color="#626aef"
            :disabled="courseInfo.course_status !== 'success'"
            @click="goChapter"
          >
            进入
          </el-button>
        </el-col>
        <el-col :span="6"><div class="grid-content ep-bg-purple" />
          <el-button :disabled="courseInfo.course_status !== 'success'" @click="goNotice">
            通知
          </el-button>
        </el-col>
        <el-col :span="6"><div class="grid-content ep-bg-purple" />
          <el-button
            :disabled="courseInfo.course_status !== 'success'"
            @click="goHomework"
          >
            作业
          </el-button>
        </el-col>
        <el-col :span="6"><div class="grid-content ep-bg-purple" />
          <el-button :disabled="courseInfo.course_status !== 'success'" @click="goGrade">
            成绩
          </el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<style scoped lang="scss">
.list-card-item {
  display: flex;
  flex-direction: column;
  margin-bottom: 12px;
  border-radius: 3px;
  overflow: hidden;
  cursor: pointer;

  &_detail {
    flex: 1;
    padding: 24px 32px;
    min-height: 140px;

    &--logo {
      width: 56px;
      height: 56px;
      border-radius: 50%;
      display: flex;
      justify-content: center;
      align-items: center;
      background: #e0ebff;
      font-size: 32px;
      color: #0052d9;

      &__disabled {
        color: #a1c4ff;
      }
    }

    &--operation {
      display: flex;
      height: 100%;

      &--tag {
        border: 0;
      }
    }

    &--name {
      margin: 24px 0 8px 0;
      font-size: 16px;
      font-weight: 400;
    }

    &--desc {
      font-size: 12px;
      line-height: 20px;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      margin-bottom: 24px;
      height: 40px;
    }
  }

  &__disabled {
    .list-card-item_detail--name,
    .list-card-item_detail--desc {
      color: var(--el-text-color-disabled);
    }

    .list-card-item_detail--operation--tag {
      color: #bababa;
    }
  }
}
</style>
