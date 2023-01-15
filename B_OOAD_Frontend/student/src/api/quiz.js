import request from '/src/utils/request'
export default {
    getFinishedQuiz(userId){
        return request({
            url: `/user/quiz/all/${userId}`,
            method: 'get'
        })
    },

    getUnfinishedQuiz(userId){
        return request({
            url: `/user/quiz/undo/${userId}`,
            method: 'get'
        })
    },

    updateQuiz(quizInfo){
        return request({
            url: `/user/quiz/update`,
            method: 'post',
            data: quizInfo
        })
    },
}