import request from '/src/utils/request'

export default {
    getUserInfo(userId){
        return request({
            url: `/user/info/${userId}`,
            method: 'get'
        })
    }

}