import request from '/src/utils/request'
export default {
  getVideo(lesson_id) {
    return request({
      url: `/user/video/${lesson_id}`,
      method: 'get'
    })
  },
  addLessonCom(lesson_id,user_id){
    return request({
        url: `/user/lesson/update/${lesson_id}/${user_id}`,
        method: 'get'
      })
  },
  getLessonProgress(course_id,user_id){
    return request({
      url: `/user/course/progress/${course_id}/${user_id}`,
      method: 'get'
    })
  },
}