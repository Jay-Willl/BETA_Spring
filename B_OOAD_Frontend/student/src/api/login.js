import request from '/src/utils/request'

export default {
    //登录的方法
  submitLoginUser(userInfo) {
    return request({
      url: '/user/login',
      method: 'post',
      data: userInfo
    })
  },
  registerMember(formItem) {
    return request({
      url: `/register`,
      method: 'post',
      data: formItem
    })
  },
  submitLogOut(userInfo){
    return request({
      url: `/login/${userInfo}`,
      method: 'get',
      
    })
  }
  //根据token获取用户信息
//   getLoginUserInfo() {
//     return request({
//       url: `/educenter/member/getMemberInfo`,
//       method: 'get'
//     })
//   }

}