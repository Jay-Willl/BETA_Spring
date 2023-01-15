import request from '/src/utils/request'
export default {
  getListBanner() {
    return request({
      url: '/user/banner',
      method: 'get'
    })
  },
  getgoodCourse() {
    return request({
      url: '/user/welcomePage',
      method: 'get'
    })
  }
}