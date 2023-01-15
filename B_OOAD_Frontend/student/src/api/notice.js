import request from '/src/utils/request'
export default {
    getNotice(userId){
        return request({
            url: `/user/notice/${userId}`,
            method: 'get'
        })
    },

}