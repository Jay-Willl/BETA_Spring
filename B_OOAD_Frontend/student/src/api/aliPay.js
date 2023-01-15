import request from '/src/utils/request'

export default{
    tradePagePay(payAmount,userId) {
        return request({
            url: `/ali-pay/pay/` + payAmount +`/` + userId,
            method: 'get'
        })
    },

    refresh(){
        return request({
            url: '/ali-pay/refresh/',
            method: 'get'
        })
    },
}
