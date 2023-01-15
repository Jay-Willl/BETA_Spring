import request from '/src/utils/request'
export default {
    getFinishedHomework(userId){
        return request({
            url: `/user/homework/all/${userId}`,
            method: 'get'
        })
    },

    getUnfinishedHomework(userId){
        return request({
            url: `/user/homework/undo/${userId}`,
            method: 'get'
        })
    },

    getSubmittedHomework(userId){
        return request({
            url: `/user/homework/ungraded/${userId}`,
            method: 'get'
        })
    },

    obtainFileUrl(pic_id){
        return request({
            url: `/obtain/${pic_id}`,
            method: 'get'
        })
    },

    submitHomeworkInfo(homeworkInfo){
        return request({
            url: `/user/homework/update`,
            method: 'post',
            data:homeworkInfo,
        })
    }

}