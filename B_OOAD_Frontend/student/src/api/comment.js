import request from '/src/utils/request'

export default {
  getComment(courseId) {
    return request({
      url: `/user/comment/${courseId}`,
      method: 'get',
    })
  },
  addComment(comment) {
    return request({
      url: `/user/comment/add`,
      method: 'post',
      data: comment
    })
  }
}