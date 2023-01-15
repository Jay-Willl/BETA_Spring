package edu.sustech.ooad.controller;

import edu.sustech.ooad.entity.OrderInfo;
import edu.sustech.ooad.mapper.OrderInfoMapper;
import edu.sustech.ooad.service.AliPayServiceImpl;
import edu.sustech.ooad.service.OrderInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/ali-pay")
@Controller
public class AlipayController {

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Resource
    private AliPayServiceImpl aliPayServiceImpl;

    @Resource
    private OrderInfoServiceImpl orderInfoServiceImpl;

    @GetMapping("/pay/{payAmount}/{userId}")
    @ResponseBody
    public String tradePagePay(@PathVariable("payAmount") Integer payAmount,
                               @PathVariable("userId") Integer userId){

        String formStr = aliPayServiceImpl.tradeCreate(payAmount,userId);
        return formStr;
    }

    @GetMapping("/refresh")
    @ResponseBody
    public String refreshGold(){
        List<OrderInfo> orderInfoList = orderInfoServiceImpl.getNoPayOrderList();

        for (OrderInfo orderInfo : orderInfoList) {
            String orderNo = orderInfo.getOrder_no();
            aliPayServiceImpl.checkOrderStatus(orderNo);
        }
        return "ok";
    }
}
