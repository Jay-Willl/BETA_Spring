import request from '/src/utils/request'

export default {
  //条件分页课程查询的方法
  getCoursesByPage(page,strategy) {
    return request({
      url: `/user/course/${page}/${strategy}`,
      method: 'get',
    })
  },
  //搜索课程
  getsearchCourse(page,text) {
    return request({
        url: `/user/course/search/${page}/${text}`,
        method: 'get',
    })
  },
  //课程详情的方法
  getCourseInfo(course_id) {
    return request({
      url: `/user/course/${course_id}`,
      method: 'get'
    })
  },

  postBuy(course_id,user_id){
    return request({
      url :`/user/buy/${course_id}/${user_id}`,
      method: 'get'
    })
  },

  getBought(course_id,user_id){
    return request({
    url :`/user/check/${course_id}/${user_id}`,
    method: 'get'
  })
  }

}