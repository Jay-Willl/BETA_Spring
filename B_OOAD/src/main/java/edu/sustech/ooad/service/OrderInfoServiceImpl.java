package edu.sustech.ooad.service;

import edu.sustech.ooad.entity.OrderInfo;
import edu.sustech.ooad.mapper.OrderInfoMapper;
import edu.sustech.ooad.service.itf.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Override
    public OrderInfo createOrderByProductId(int payAmount, int userId) {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrder_no(getOrderNo());
        orderInfo.setTitle("充值"+payAmount/100+"元");
        orderInfo.setUser_id(userId);
        orderInfo.setTotal_fee(Math.toIntExact(payAmount)); //分
        orderInfo.setOrder_status("未支付"); //未支付
        orderInfoMapper.insertOrderInfo(orderInfo);
        return orderInfo;
    }

    @Override
    public List<OrderInfo> getNoPayOrderList() {

        List<OrderInfo> orderInfoList = orderInfoMapper.selectNoPayOrderInfo();

        return orderInfoList;
    }

    public static String getOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        return "ORDER_" + newDate + result;
    }
}
